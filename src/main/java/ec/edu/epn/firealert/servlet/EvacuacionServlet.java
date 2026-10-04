package ec.edu.epn.firealert.servlet;

import ec.edu.epn.firealert.model.Incendio;
import ec.edu.epn.firealert.model.RutaEvacuacion;
import ec.edu.epn.firealert.model.Ubicacion;
import ec.edu.epn.firealert.model.ZonaSegura;
import ec.edu.epn.firealert.service.GestorEvacuacion;
import ec.edu.epn.firealert.service.ServicioGPS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR WEB (SERVLET) PARA CASO DE USO 4 (CU4: Solicitar Ruta de Evacuación)
 * Mapeado a /evacuacion. Conecta con GestorEvacuacion y despacha a PantallaRutas / PantallaSalida.
 * ============================================================================
 */
@WebServlet(name = "EvacuacionServlet", urlPatterns = {"/evacuacion"})
public class EvacuacionServlet extends HttpServlet {

    private GestorEvacuacion gestorEvacuacion;
    private ServicioGPS servicioGPS;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gestorEvacuacion = new GestorEvacuacion();
        this.servicioGPS = new ServicioGPS();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String latStr = req.getParameter("lat");
        String lngStr = req.getParameter("lng");
        String dir = req.getParameter("direccion");

        Ubicacion ubicacionUsuario;
        if (latStr != null && !latStr.isEmpty() && lngStr != null && !lngStr.isEmpty()) {
            double lat = Double.parseDouble(latStr);
            double lng = Double.parseDouble(lngStr);
            ubicacionUsuario = new Ubicacion(lat, lng, dir != null && !dir.isEmpty() ? dir : "Mi Ubicación Actual");
        } else {
            // Posición por defecto en Quito
            ubicacionUsuario = servicioGPS.obtenerUbicacion();
        }

        // Flujo CU4: solicitarRuta(ubicación) -> evaluarRiesgo -> mostrarAlternativas(rutas)
        List<RutaEvacuacion> alternativas = gestorEvacuacion.solicitarRuta(ubicacionUsuario);
        List<Incendio> incendiosActivos = gestorEvacuacion.obtenerIncendiosActivos();
        List<ZonaSegura> zonasSeguras = gestorEvacuacion.obtenerZonasSeguras();

        req.setAttribute("ubicacionUsuario", ubicacionUsuario);
        req.setAttribute("rutasAlternativas", alternativas);
        req.setAttribute("incendiosActivos", incendiosActivos);
        req.setAttribute("zonasSeguras", zonasSeguras);

        String idRutaParam = req.getParameter("idRuta");
        if (idRutaParam != null && !idRutaParam.isEmpty()) {
            try {
                Long idRuta = Long.parseLong(idRutaParam);
                alternativas.stream()
                        .filter(r -> r.getId().equals(idRuta))
                        .findFirst()
                        .ifPresent(r -> req.setAttribute("rutaSeleccionada", r));
            } catch (NumberFormatException ignored) {}
        } else if (!alternativas.isEmpty()) {
            // Seleccionar por defecto la ruta con menor riesgo
            req.setAttribute("rutaSeleccionada", alternativas.get(0));
        }

        req.getRequestDispatcher("/rutas-evacuacion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
