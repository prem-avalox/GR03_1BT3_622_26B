package ec.edu.epn.firealert.dao;

import ec.edu.epn.firealert.model.Bombero;
import ec.edu.epn.firealert.model.Ciudadano;
import ec.edu.epn.firealert.model.Usuario;
import ec.edu.epn.firealert.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDAO {

    public Usuario guardar(Usuario usuario) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(usuario);
            tx.commit();
            return usuario;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return null;
        }
    }

    public Usuario buscarPorEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = session.createQuery("FROM Usuario WHERE email = :email", Usuario.class);
            q.setParameter("email", email);
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Ciudadano obtenerCiudadanoDefault() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Ciudadano> q = session.createQuery("FROM Ciudadano ORDER BY id ASC", Ciudadano.class);
            q.setMaxResults(1);
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bombero obtenerBomberoDefault() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bombero> q = session.createQuery("FROM Bombero ORDER BY id ASC", Bombero.class);
            q.setMaxResults(1);
            return q.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bombero buscarBomberoPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Bombero.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
