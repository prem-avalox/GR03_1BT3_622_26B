package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.dao.UsuarioDAO;
import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.Evidencia;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.TipoEvidencia;
import ec.edu.epn.alerfire.model.Ubicacion;
import ec.edu.epn.alerfire.service.GestorReportes;
import ec.edu.epn.alerfire.service.GestorReputacion;
import ec.edu.epn.alerfire.service.ServicioGPS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR WEB (SERVLET) PARA CASO DE USO 1 (CU1: Reportar Incendio)
 * Mapeado a /reportar. Conecta con GestorReportes y GestorReputacion (RF-I1-02.3).
 * ============================================================================
 */
@WebServlet(name = "ReporteServlet", urlPatterns = {"/reportar"})
public class ReporteServlet extends HttpServlet {

    private GestorReportes gestorReportes;
    private ServicioGPS servicioGPS;
    private UsuarioDAO usuarioDAO;
    private GestorReputacion gestorReputacion;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gestorReportes = new GestorReportes();
        this.servicioGPS = new ServicioGPS();
        this.usuarioDAO = new UsuarioDAO();
        this.gestorReputacion = new GestorReputacion(this.usuarioDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Ciudadano ciudadano = usuarioDAO.obtenerCiudadanoDefault();
        boolean accesoValido = gestorReputacion.validarAcceso(ciudadano);

        req.setAttribute("cuentaSuspendida", !accesoValido);
        req.setAttribute("ciudadanoActual", ciudadano);

        // abrirPantalla(): Inicializa la vista con la ubicación actual sugerida por GPS
        Ubicacion gps = servicioGPS.obtenerUbicacion();
        req.setAttribute("ubicacionGPS", gps);
        req.getRequestDispatcher("/reportar-incendio.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // RF-I1-02.3: Bloqueo en servidor si el ciudadano se encuentra suspendido
        Ciudadano ciudadano = usuarioDAO.obtenerCiudadanoDefault();
        if (!gestorReputacion.validarAcceso(ciudadano)) {
            req.getSession().setAttribute("mensajeError",
                    "Acceso bloqueado: su cuenta ha sido suspendida permanentemente por acumular 3 reportes de falsa alarma.");
            resp.sendRedirect(req.getContextPath() + "/reportar");
            return;
        }

        String descripcion = req.getParameter("descripcion");
        String latStr = req.getParameter("latitud");
        String lngStr = req.getParameter("longitud");
        String direccion = req.getParameter("direccion");
        String tipoEvidenciaStr = req.getParameter("tipoEvidencia");
        String archivoUrl = req.getParameter("archivoUrl");

        try {
            double lat = (latStr != null && !latStr.isEmpty()) ? Double.parseDouble(latStr) : -0.180653;
            double lng = (lngStr != null && !lngStr.isEmpty()) ? Double.parseDouble(lngStr) : -78.467838;
            Ubicacion ubicacion = new Ubicacion(lat, lng, direccion != null ? direccion : "Ubicación reportada");

            List<Evidencia> evidencias = new ArrayList<>();
            if (archivoUrl != null && !archivoUrl.trim().isEmpty()) {
                TipoEvidencia tipo = "VIDEO".equalsIgnoreCase(tipoEvidenciaStr) ? TipoEvidencia.VIDEO : TipoEvidencia.FOTO;
                evidencias.add(new Evidencia(archivoUrl.trim(), tipo));
            }

            // Invocación al Gestor del caso de uso vinculando al ciudadano
            Incendio reporteCreado = gestorReportes.crearReporte(ciudadano, evidencias, descripcion, ubicacion);

            if (reporteCreado != null) {
                req.getSession().setAttribute("mensajeExito",
                        "Reporte #" + reporteCreado.getId() + " registrado con éxito. Las unidades de socorro y la comunidad han sido alertadas.");
                req.getSession().setAttribute("reporteConfirmado", reporteCreado);
                resp.sendRedirect(req.getContextPath() + "/mapa?idSeleccionado=" + reporteCreado.getId());
            } else {
                req.setAttribute("mensajeError", "No se pudo registrar el reporte. Por favor verifique los datos.");
                doGet(req, resp);
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("mensajeError", e.getMessage());
            doGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("mensajeError", "Error inesperado al procesar el reporte: " + e.getMessage());
            doGet(req, resp);
        }
    }
}
