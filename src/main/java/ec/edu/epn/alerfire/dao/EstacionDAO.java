package ec.edu.epn.alerfire.dao;

import ec.edu.epn.alerfire.model.EstacionBomberos;
import ec.edu.epn.alerfire.service.IEstacionDAO;
import ec.edu.epn.alerfire.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class EstacionDAO implements IEstacionDAO {

    @Override
    public boolean guardar(EstacionBomberos estacion) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(estacion);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(EstacionBomberos estacion) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(estacion);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public EstacionBomberos buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(EstacionBomberos.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<EstacionBomberos> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<EstacionBomberos> query = session.createQuery("FROM EstacionBomberos ORDER BY distanciaKmReferencia ASC", EstacionBomberos.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
