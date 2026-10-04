package ec.edu.epn.firealert.dao;

import ec.edu.epn.firealert.model.RutaEvacuacion;
import ec.edu.epn.firealert.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class RutaEvacuacionDAO {

    public RutaEvacuacion guardar(RutaEvacuacion ruta) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(ruta);
            tx.commit();
            return ruta;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public List<RutaEvacuacion> listarTodas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM RutaEvacuacion ORDER BY nivelRiesgo ASC, distanciaKm ASC", RutaEvacuacion.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public RutaEvacuacion buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(RutaEvacuacion.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
