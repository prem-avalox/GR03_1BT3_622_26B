package ec.edu.epn.alerfire.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/*
 * ============================================================================
 * PROMPT DE CREACIÓN UTILIZADO EN GITHUB COPILOT:
 * "Crea una clase singleton utilitaria de Hibernate en Java llamada HibernateUtil
 * que construya y administre el SessionFactory a partir del archivo hibernate.cfg.xml,
 * con manejo de excepciones y método seguro para cerrar la sesión."
 * ============================================================================
 */
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
                throw new ExceptionInInitializerError("Error al inicializar Hibernate SessionFactory: " + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
