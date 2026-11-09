package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.UsuarioDAO;
import ec.edu.epn.alerfire.model.Ciudadano;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.NivelConfiabilidad;
import ec.edu.epn.alerfire.service.GestorReputacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controlador Web para el Caso de Uso CU6 / HU-I1-03:
 * Consulta ciudadana de perfil de confiabilidad e historial de conatos propios.
 * Mapeado a /mi-historial.
 */
@WebServlet(name = "HistorialCiudadanoServlet", urlPatterns = {"/mi-historial"})
public class HistorialCiudadanoServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private IncendioDAO incendioDAO;
    private GestorReputacion gestorReputacion;

    @Override
    public void init() throws ServletException {
        super.init();
        this.usuarioDAO = new UsuarioDAO();
        this.incendioDAO = new IncendioDAO();
        this.gestorReputacion = new GestorReputacion(this.usuarioDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // RF-I1-03.1: Obtención del perfil de reputación y rango del ciudadano autenticado
        Ciudadano ciudadano = usuarioDAO.obtenerCiudadanoDefault();

        if (ciudadano == null) {
            req.setAttribute("mensajeError", "No se encontró un perfil de ciudadano activo.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        NivelConfiabilidad nivel = gestorReputacion.clasificarNivelConfiabilidad(ciudadano.getPuntosReputacion());
        req.setAttribute("ciudadano", ciudadano);
        req.setAttribute("nivelConfiabilidad", nivel);

        // RF-I1-03.2: Listado cronológico descendente de reportes emitidos por el ciudadano
        List<Incendio> misReportes = incendioDAO.listarPorCiudadano(ciudadano.getId());
        req.setAttribute("misReportes", misReportes);

        req.getRequestDispatcher("/mi-historial.jsp").forward(req, resp);
    }
}
