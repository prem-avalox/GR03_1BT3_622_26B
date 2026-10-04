package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.dao.NotificacionDAO;
import ec.edu.epn.alerfire.model.Incendio;
import ec.edu.epn.alerfire.model.Notificacion;

import java.time.LocalDateTime;

public class ServicioNotificaciones {

    private final NotificacionDAO notificacionDAO;

    public ServicioNotificaciones() {
        this.notificacionDAO = new NotificacionDAO();
    }

    /**
     * Notifica a ciudadanos en zonas cercanas al registrarse un nuevo incendio (CU1).
     */
    public boolean notificarCercanos(Incendio incendio) {
        if (incendio == null) return false;
        String dir = (incendio.getUbicacion() != null) ? incendio.getUbicacion().getDireccion() : "Sector reportado";
        String mensaje = String.format("¡ALERTA DE INCENDIO! Se ha reportado un conato de incendio en: %s. Mantenga la precaución y despeje las vías.", dir);

        Notificacion notif = new Notificacion(mensaje, "NUEVO_INCENDIO", incendio, null);
        notif.setFechaHora(LocalDateTime.now());
        boolean enviada = notif.enviar();
        notificacionDAO.guardar(notif);
        return enviada;
    }

    /**
     * Notifica a las entidades y a la comunidad sobre el cambio de estado de un incendio (CU2).
     */
    public boolean notificarCambioEstado(Incendio incendio) {
        if (incendio == null) return false;
        String mensaje = String.format("ACTUALIZACIÓN: El incendio #%d ha cambiado de estado a [%s].",
                incendio.getId(),
                incendio.getEstado().getDescripcion());

        Notificacion notif = new Notificacion(mensaje, "CAMBIO_ESTADO", incendio, null);
        notif.setFechaHora(LocalDateTime.now());
        boolean enviada = notif.enviar();
        notificacionDAO.guardar(notif);
        return enviada;
    }
}
