package ec.edu.epn.alerfire.dao;

import ec.edu.epn.alerfire.model.ZonaSegura;
import ec.edu.epn.alerfire.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class ZonaSeguraDAO {

    public ZonaSegura guardar(ZonaSegura zona) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(zona);
            tx.commit();
            return zona;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<ZonaSegura> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM ZonaSegura ORDER BY nombre ASC", ZonaSegura.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public ZonaSegura buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(ZonaSegura.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
