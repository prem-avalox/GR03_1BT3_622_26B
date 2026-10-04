package ec.edu.epn.alerfire.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[AppContextListener] Inicializando aplicación AlerFire...");
        try {
            // Inicializar SessionFactory y poblar datos semilla de Quito
            HibernateUtil.getSessionFactory();
            DataInitializer.inicializarDatos();
            System.out.println("[AppContextListener] Base de datos y datos semilla listos.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.shutdown();
    }
}
