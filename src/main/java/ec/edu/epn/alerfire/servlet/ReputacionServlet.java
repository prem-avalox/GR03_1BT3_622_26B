package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.dao.EstacionDAO;
import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.UsuarioDAO;
import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.NivelConfiabilidad;
import ec.edu.epn.alerfire.service.GestorReputacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controlador Web para el Módulo de Reputación Cívica y Gobernanza (HU-I1-01 y HU-I1-02).
 * Mapeado a /reputacion.
 */
@WebServlet(name = "ReputacionServlet", urlPatterns = {"/reputacion"})
public class ReputacionServlet extends HttpServlet {

    private IncendioDAO incendioDAO;
    private UsuarioDAO usuarioDAO;
    private EstacionDAO estacionDAO;
    private GestorReputacion gestorReputacion;

    @Override
    public void init() throws ServletException {
        super.init();
        this.incendioDAO = new IncendioDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.estacionDAO = new EstacionDAO();
        this.gestorReputacion = new GestorReputacion(this.usuarioDAO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");
        String idIncendioParam = req.getParameter("idIncendio");

        if (idIncendioParam == null || idIncendioParam.trim().isEmpty()) {
            req.getSession().setAttribute("mensajeError", "Identificador de conato no especificado.");
            resp.sendRedirect(req.getContextPath() + "/incendios");
            return;
        }

        try {
            Long idIncendio = Long.parseLong(idIncendioParam);
            Incendio conato = incendioDAO.buscarPorId(idIncendio);

            if (conato == null) {
                req.getSession().setAttribute("mensajeError", "El conato #" + idIncendio + " no existe.");
                resp.sendRedirect(req.getContextPath() + "/incendios");
                return;
            }

            Ciudadano ciudadano = conato.getCiudadano();
            if (ciudadano == null) {
                req.getSession().setAttribute("mensajeError", "El conato #" + idIncendio + " no tiene ciudadano asociado.");
                resp.sendRedirect(req.getContextPath() + "/incendios");
                return;
            }

            if ("calificar_veraz".equals(accion)) {
                // CA-01 / RF-I1-01.1: Solo conatos en atención o extintos
                boolean estadoValido = conato.getEstado() == EstadoIncendio.EN_PROCESO_DE_ATENCION
                        || conato.getEstado() == EstadoIncendio.BAJO_CONTROL
                        || conato.getEstado() == EstadoIncendio.EXTINTO;

                if (!estadoValido) {
                    req.getSession().setAttribute("mensajeError",
                            "Operación denegada: solo se puede recompensar un conato verificado en terreno con atención operativa iniciada (no en estado inicial Reportado).");
                    resp.sendRedirect(req.getContextPath() + "/incendios");
                    return;
                }

                if (conato.isCalificadoVeraz()) {
                    req.getSession().setAttribute("mensajeError",
                            "Operación denegada: el conato #" + idIncendio + " ya fue calificado como veraz.");
                    resp.sendRedirect(req.getContextPath() + "/incendios");
                    return;
                }

                if (ciudadano.isSuspendido()) {
                    req.getSession().setAttribute("mensajeError",
                            "Operación denegada: no se puede bonificar el reporte de un ciudadano con cuenta suspendida.");
                    resp.sendRedirect(req.getContextPath() + "/incendios");
                    return;
                }

                // Ejecución de bonificación (+50 pts)
                int nuevoSaldo = gestorReputacion.evaluarVeracidad(ciudadano, true);
                conato.setCalificadoVeraz(true);
                incendioDAO.actualizar(conato);

                NivelConfiabilidad nivel = gestorReputacion.clasificarNivelConfiabilidad(nuevoSaldo);
                req.getSession().setAttribute("mensajeExito",
                        "[HU-I1-01] Reporte #" + conato.getId() + " calificado como veraz: se otorgaron +50 puntos al ciudadano (Nuevo saldo: "
                                + nuevoSaldo + " pts - Nivel: " + nivel.name() + ").");

            } else if ("sancionar_falsa_alarma".equals(accion)) {
                // CA-01 / RF-I1-02.1: Sanción -100 pts con piso en cero y liberación de estación
                int nuevoSaldo = gestorReputacion.evaluarVeracidad(ciudadano, false);

                conato.setEstado(EstadoIncendio.FALSA_ALARMA);
                if (conato.getEstacionAsignada() != null) {
                    conato.getEstacionAsignada().setDisponible(true);
                    estacionDAO.actualizar(conato.getEstacionAsignada());
                }
                incendioDAO.actualizar(conato);

                String avisoSuspension = ciudadano.isSuspendido()
                        ? " [ALERTA: Infractor reincidente bloqueado: acumuló 3 falsas alarmas y su cuenta ha sido suspendida automáticamente]."
                        : ".";

                req.getSession().setAttribute("mensajeExito",
                        "[HU-I1-02] Conato #" + conato.getId() + " sancionado como FALSA ALARMA: se descontaron 100 puntos (Saldo actual: "
                                + nuevoSaldo + " pts - Infracciones: " + ciudadano.getReportesFalsos() + " de 3)"
                                + avisoSuspension);
            }

        } catch (IllegalStateException e) {
            req.getSession().setAttribute("mensajeError", "Regla de negocio: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("mensajeError", "Error al procesar la evaluación: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/incendios");
    }
}
