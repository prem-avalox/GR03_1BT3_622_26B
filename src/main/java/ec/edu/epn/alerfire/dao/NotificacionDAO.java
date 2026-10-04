package ec.edu.epn.alerfire.dao;

import ec.edu.epn.alerfire.model.Notificacion;
import ec.edu.epn.alerfire.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class NotificacionDAO {

    public Notificacion guardar(Notificacion notificacion) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(notificacion);
            tx.commit();
            return notificacion;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<Notificacion> listarRecientes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Notificacion ORDER BY fechaHora DESC", Notificacion.class)
                    .setMaxResults(15)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
