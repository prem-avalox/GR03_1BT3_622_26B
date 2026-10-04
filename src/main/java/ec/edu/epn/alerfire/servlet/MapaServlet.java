package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.ZonaSegura;
import ec.edu.epn.alerfire.service.GestorMapa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR WEB (SERVLET) PARA CASO DE USO 3 (CU3: Visualizar Mapa de Incendios)
 * Mapeado a /mapa. Conecta con GestorMapa y despacha a PantallaMapa.
 * ============================================================================
 */
@WebServlet(name = "MapaServlet", urlPatterns = {"/mapa"})
public class MapaServlet extends HttpServlet {

    private GestorMapa gestorMapa;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gestorMapa = new GestorMapa();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // abrirPantalla() y listarIncendiosActivos()
        List<Incendio> activos = gestorMapa.listarIncendiosActivos();
        List<ZonaSegura> zonasSeguras = gestorMapa.obtenerZonasSeguras();

        req.setAttribute("incendiosActivos", activos);
        req.setAttribute("zonasSeguras", zonasSeguras);

        String idParam = req.getParameter("idSeleccionado");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Incendio detalle = gestorMapa.obtenerDetalle(id);
                req.setAttribute("incendioDetalle", detalle);
            } catch (NumberFormatException ignored) {}
        }

        req.getRequestDispatcher("/mapa.jsp").forward(req, resp);
    }
}
