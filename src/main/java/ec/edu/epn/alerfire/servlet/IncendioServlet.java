package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.service.GestorIncendios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
 * ============================================================================
 * CONTROLADOR WEB (SERVLET) PARA CASO DE USO 2 (CU2: Actualizar Estado de Incendio)
 * Mapeado a /incendios. Conecta con GestorIncendios y despacha a PantallaActualizarEstado.
 * ============================================================================
 */
@WebServlet(name = "IncendioServlet", urlPatterns = {"/incendios", "/actualizar-estado"})
public class IncendioServlet extends HttpServlet {

    private GestorIncendios gestorIncendios;
    private IncendioDAO incendioDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.gestorIncendios = new GestorIncendios();
        this.incendioDAO = new IncendioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // abrirPantalla() y mostrarReportesPendientes(lista)
        List<Incendio> pendientes = gestorIncendios.obtenerReportesPendientes();
        req.setAttribute("reportesPendientes", pendientes);

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
        String idStr = req.getParameter("idIncendio");
        String estadoStr = req.getParameter("nuevoEstado");
        String observacion = req.getParameter("observacion");

        try {
            Long id = Long.parseLong(idStr);
            EstadoIncendio nuevoEstado = EstadoIncendio.valueOf(estadoStr);

            boolean exito = gestorIncendios.actualizarEstado(id, nuevoEstado, null, observacion);

            if (exito) {
                req.getSession().setAttribute("mensajeExito",
                        "El estado del incendio #" + id + " fue actualizado a [" + nuevoEstado.getDescripcion() + "] exitosamente.");
            } else {
                req.getSession().setAttribute("mensajeError", "No se pudo actualizar el estado del incendio #" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("mensajeError", "Error al procesar la actualización: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/incendios");
    }
}
