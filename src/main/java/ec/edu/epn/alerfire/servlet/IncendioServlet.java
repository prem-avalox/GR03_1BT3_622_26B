package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.dao.EstacionDAO;
import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.service.GestorDespacho;
import ec.edu.epn.alerfire.service.GestorIncendios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IncendioServlet", urlPatterns = {"/incendios", "/actualizar-estado", "/incendios/actualizar"})
public class IncendioServlet extends HttpServlet {

    private GestorIncendios gestorIncendios;
    private GestorDespacho gestorDespacho;
    private IncendioDAO incendioDAO;
    private EstacionDAO estacionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.incendioDAO = new IncendioDAO();
        this.estacionDAO = new EstacionDAO();
        this.gestorIncendios = new GestorIncendios();
        this.gestorDespacho = new GestorDespacho(this.estacionDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Incendio> pendientes = incendioDAO.listarTodos();
        req.setAttribute("reportesPendientes", pendientes);

        List<EstacionBomberos> estaciones = estacionDAO.listarTodas();
        req.setAttribute("estacionesBomberos", estaciones);

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Incendio seleccionado = incendioDAO.buscarPorId(id);
                req.setAttribute("incendioSeleccionado", seleccionado);
            } catch (NumberFormatException ignored) {}
        }

        req.setAttribute("estadosDisponibles", EstadoIncendio.values());
        req.getRequestDispatcher("/actualizar-estado.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String accion = req.getParameter("accion");

        try {
            if ("despachar".equals(accion)) {
                // HU-01: Despacho de Estación de Bomberos Disponible a un Conato Activo
                Long idIncendio = Long.parseLong(req.getParameter("idIncendio"));
                Long idEstacion = Long.parseLong(req.getParameter("idEstacion"));

                Incendio conato = incendioDAO.buscarPorId(idIncendio);
                EstacionBomberos estacion = estacionDAO.buscarPorId(idEstacion);

                boolean despachado = gestorDespacho.asignarEstacion(conato, estacion);
                if (despachado) {
                    incendioDAO.actualizar(conato);
                    int eta = gestorDespacho.calcularTiempoArriboMinutos(estacion.getDistanciaKmReferencia());
                    boolean extendida = gestorDespacho.esRespuestaExtendida(estacion.getDistanciaKmReferencia());
                    String avisoEta = extendida ? " [ALERTA: Respuesta Extendida > 15 min]" : " [Respuesta Operativa Normal]";
                    req.getSession().setAttribute("mensajeExito",
                            "[HU-01 / HU-03] Despacho exitoso: " + estacion.getNombre() + " (" + estacion.getCodigo()
                                    + ") asignada al Conato #" + conato.getId()
                                    + ". Estado cambiado a EN PROCESO DE ATENCIÓN. ETA estimado: " + eta + " min" + avisoEta + ".");
                }
            } else if ("liquidar".equals(accion)) {
                // HU-02: Liquidación de Emergencia y Liberación Automática de Estación
                Long idIncendio = Long.parseLong(req.getParameter("idIncendio"));
                Incendio conato = incendioDAO.buscarPorId(idIncendio);
                String nombreEstacion = (conato != null && conato.getEstacionAsignada() != null)
                        ? conato.getEstacionAsignada().getNombre() + " (" + conato.getEstacionAsignada().getCodigo() + ")"
                        : "N/A";

                boolean finalizado = gestorDespacho.finalizarAtencion(conato);
                if (finalizado) {
                    incendioDAO.actualizar(conato);
                    req.getSession().setAttribute("mensajeExito",
                            "[HU-02] Liquidación exitosa: El Conato #" + conato.getId()
                                    + " pasó a estado EXTINTO y la estación " + nombreEstacion
                                    + " fue liberada automáticamente (DISPONIBLE = true).");
                }
            } else {
                // Flujo tradicional CU2
                Long id = Long.parseLong(req.getParameter("idIncendio"));
                EstadoIncendio nuevoEstado = EstadoIncendio.valueOf(req.getParameter("nuevoEstado"));
                String observacion = req.getParameter("observacion");

                boolean exito = gestorIncendios.actualizarEstado(id, nuevoEstado, null, observacion);
                if (exito) {
                    req.getSession().setAttribute("mensajeExito",
                            "El estado del incendio #" + id + " fue actualizado a [" + nuevoEstado.getDescripcion() + "] exitosamente.");
                } else {
                    req.getSession().setAttribute("mensajeError", "No se pudo actualizar el estado del incendio #" + id);
                }
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            req.getSession().setAttribute("mensajeError", "Operación rechazada por regla de negocio: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("mensajeError", "Error al procesar la solicitud: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/incendios");
    }
}
