package ec.edu.epn.firealert.dao;

import ec.edu.epn.firealert.model.EstadoIncendio;
import ec.edu.epn.firealert.model.Incendio;
import ec.edu.epn.firealert.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class IncendioDAO {

    public Incendio guardar(Incendio incendio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(incendio);
            tx.commit();
            return incendio;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizar(Incendio incendio) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(incendio);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Incendio buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Incendio inc = session.get(Incendio.class, id);
            if (inc != null) {
                // Forzar inicialización de colecciones lazy
                inc.getEvidencias().size();
                inc.getHistorial().size();
            }
            return inc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Incendio> listarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Incendio> query = session.createQuery("FROM Incendio ORDER BY fechaHora DESC", Incendio.class);
            List<Incendio> list = query.list();
            for (Incendio inc : list) {
                inc.getEvidencias().size();
                inc.getHistorial().size();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Incendio> listarActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Incendio> query = session.createQuery(
                "FROM Incendio WHERE estado != :extinto ORDER BY fechaHora DESC", Incendio.class);
            query.setParameter("extinto", EstadoIncendio.EXTINTO);
            List<Incendio> list = query.list();
            for (Incendio inc : list) {
                inc.getEvidencias().size();
                inc.getHistorial().size();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Incendio> listarPendientes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Incendio> query = session.createQuery(
                "FROM Incendio WHERE estado IN (:s1, :s2, :s3) ORDER BY fechaHora DESC", Incendio.class);
            query.setParameter("s1", EstadoIncendio.REPORTADO);
            query.setParameter("s2", EstadoIncendio.EN_PROCESO_DE_ATENCION);
            query.setParameter("s3", EstadoIncendio.NO_CONTROLADO);
            List<Incendio> list = query.list();
            for (Incendio inc : list) {
                inc.getEvidencias().size();
                inc.getHistorial().size();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
