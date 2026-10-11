package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.Evidencia;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.TipoEvidencia;
import ec.edu.epn.alerfire.model.Ubicacion;
import ec.edu.epn.alerfire.service.GestorReportes;
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
 * Mapeado a /reportar. Conecta con GestorReportes y despacha a PantallaReporte.
 * ============================================================================
 */
@WebServlet(name = "ReporteServlet", urlPatterns = {"/reportar"})
public class ReporteServlet extends HttpServlet {

    private GestorReportes gestorReportes;
    private ServicioGPS servicioGPS;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gestorReportes = new GestorReportes();
        this.servicioGPS = new ServicioGPS();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // abrirPantalla(): Inicializa la vista con la ubicación actual sugerida por GPS
        Ubicacion gps = servicioGPS.obtenerUbicacion();
        req.setAttribute("ubicacionGPS", gps);
        req.getRequestDispatcher("/reportar-incendio.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String descripcion = req.getParameter("descripcion");

        try {
            // Llamadas limpias a los métodos extraídos
            Ubicacion ubicacion = extraerUbicacion(req);
            List<Evidencia> evidencias = extraerEvidencias(req);

            Incendio reporteCreado = gestorReportes.crearReporte(null, evidencias, descripcion, ubicacion);

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

    /**
     * extrae y construye la Ubicacion a partir de los parámetros de la petición HTTP.
     */
    private Ubicacion extraerUbicacion(HttpServletRequest req) {
        String latStr = req.getParameter("latitud");
        String lngStr = req.getParameter("longitud");
        String direccion = req.getParameter("direccion");

        double lat = (latStr != null && !latStr.isEmpty()) ? Double.parseDouble(latStr) : -0.180653;
        double lng = (lngStr != null && !lngStr.isEmpty()) ? Double.parseDouble(lngStr) : -78.467838;
        String dir = (direccion != null && !direccion.trim().isEmpty()) ? direccion : "Ubicación reportada";

        return new Ubicacion(lat, lng, dir);
    }

    /**
     * extrae y genera la lista de evidencias adjuntas en la petición HTTP.
     */
    private List<Evidencia> extraerEvidencias(HttpServletRequest req) {
        String tipoEvidenciaStr = req.getParameter("tipoEvidencia");
        String archivoUrl = req.getParameter("archivoUrl");
        List<Evidencia> evidencias = new ArrayList<>();

        if (archivoUrl != null && !archivoUrl.trim().isEmpty()) {
            TipoEvidencia tipo = "VIDEO".equalsIgnoreCase(tipoEvidenciaStr) ? TipoEvidencia.VIDEO : TipoEvidencia.FOTO;
            evidencias.add(new Evidencia(archivoUrl.trim(), tipo));
        }
        return evidencias;
    }


}
