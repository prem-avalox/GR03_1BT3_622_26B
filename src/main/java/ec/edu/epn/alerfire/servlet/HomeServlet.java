package ec.edu.epn.alerfire.servlet;

import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.NotificacionDAO;
import ec.edu.epn.alerfire.dao.ZonaSeguraDAO;
import ec.edu.epn.alerfire.model.EstadoIncendio;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.util.DataInitializer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"", "/home", "/index"})
public class HomeServlet extends HttpServlet {

    private IncendioDAO incendioDAO;
    private ZonaSeguraDAO zonaSeguraDAO;
    private NotificacionDAO notificacionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.incendioDAO = new IncendioDAO();
        this.zonaSeguraDAO = new ZonaSeguraDAO();
        this.notificacionDAO = new NotificacionDAO();
        // Cargar datos semilla si la BD está vacía
        DataInitializer.inicializarDatos();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Incendio> todos = incendioDAO.listarTodos();
        List<Incendio> activos = incendioDAO.listarActivos();
        List<Incendio> pendientes = incendioDAO.listarPendientes();

        long extintos = todos.stream().filter(i -> i.getEstado() == EstadoIncendio.EXTINTO).count();
        long noControlados = todos.stream().filter(i -> i.getEstado() == EstadoIncendio.NO_CONTROLADO).count();

        req.setAttribute("totalIncendios", todos.size());
        req.setAttribute("activosCount", activos.size());
        req.setAttribute("pendientesCount", pendientes.size());
        req.setAttribute("extintosCount", extintos);
        req.setAttribute("noControladosCount", noControlados);

        req.setAttribute("incendiosRecientes", activos.size() > 5 ? activos.subList(0, 5) : activos);
        req.setAttribute("zonasSeguras", zonaSeguraDAO.listarTodas());
        req.setAttribute("notificaciones", notificacionDAO.listarRecientes());

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
