package ec.edu.epn.alerfire.util;

import ec.edu.epn.alerfire.dao.EstacionDAO;
import ec.edu.epn.alerfire.dao.IncendioDAO;
import ec.edu.epn.alerfire.dao.RutaEvacuacionDAO;
import ec.edu.epn.alerfire.dao.UsuarioDAO;
import ec.edu.epn.alerfire.dao.ZonaSeguraDAO;
import ec.edu.epn.alerfire.model.*;

import java.time.LocalDateTime;

public class DataInitializer {

    private static boolean inicializado = false;

    public static synchronized void inicializarDatos() {
        if (inicializado) return;

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            IncendioDAO incendioDAO = new IncendioDAO();
            ZonaSeguraDAO zonaSeguraDAO = new ZonaSeguraDAO();
            RutaEvacuacionDAO rutaDAO = new RutaEvacuacionDAO();
            EstacionDAO estacionDAO = new EstacionDAO();

            // Si ya hay usuarios, asumimos que la base ya contiene datos
            if (usuarioDAO.obtenerCiudadanoDefault() != null) {
                if (estacionDAO.listarTodas().isEmpty()) {
                    sembrarEstaciones(estacionDAO);
                }
                inicializado = true;
                return;
            }

            System.out.println("[DataInitializer] Inicializando datos semilla de AlerFire en H2 Database...");

            // 1. Crear Usuarios de prueba
            Ciudadano ciudadano1 = new Ciudadano("Juan Pérez", "juan.perez@epn.edu.ec", "123456", "0991234567");
            usuarioDAO.guardar(ciudadano1);

            Bombero bombero1 = new Bombero("Sgt. Carlos Mendoza", "carlos.mendoza@bomberosquito.gob.ec", "bombero2026",
                    "Jefe de Cuadrilla", "Estación B1 - Sector Norte");
            usuarioDAO.guardar(bombero1);

            // 2. Crear Estaciones de Bomberos para HU-01, HU-02 y HU-03
            EstacionBomberos estProximidad = new EstacionBomberos("B1", "Estación Operativa A (Proximidad Inmediata)", true, 0.5,
                    new Ubicacion(-0.180000, -78.467000, "Puesto Avanzado Guardaparques"));
            estacionDAO.guardar(estProximidad);

            EstacionBomberos estUrbana = new EstacionBomberos("B2", "Estación Operativa B (Sector Urbano)", true, 5.0,
                    new Ubicacion(-0.185000, -78.482000, "Av. Amazonas y República"));
            estacionDAO.guardar(estUrbana);

            EstacionBomberos estOcupada = new EstacionBomberos("B3", "Estación Operativa C (En Atención Activa)", false, 4.0,
                    new Ubicacion(-0.174200, -78.468000, "Sector Bellavista"));
            estacionDAO.guardar(estOcupada);

            EstacionBomberos estLejana = new EstacionBomberos("B4", "Estación Operativa D (Periferia Rural)", true, 10.0,
                    new Ubicacion(-0.245000, -78.520000, " Reserva Suroriental"));
            estacionDAO.guardar(estLejana);

            // 3. Crear Zonas Seguras en Quito
            ZonaSegura z1 = new ZonaSegura("Parque La Carolina - Zona de Seguridad Cruz Roja",
                    new Ubicacion(-0.180653, -78.484838, "Av. De los Shyris y Av. Naciones Unidas, Quito"),
                    5000, "Parque Abierto");
            zonaSeguraDAO.guardar(z1);

            ZonaSegura z2 = new ZonaSegura("Parque El Ejido - Explanada Central",
                    new Ubicacion(-0.210453, -78.498838, "Av. 10 de Agosto y Av. Patria, Quito"),
                    3500, "Plaza Pública");
            zonaSeguraDAO.guardar(z2);

            ZonaSegura z3 = new ZonaSegura("Coliseo General Rumiñahui - Refugio de Emergencia",
                    new Ubicacion(-0.212500, -78.489000, "Av. Velasco Ibarra y Ladrón de Guevara, Quito"),
                    8000, "Instalación Techada");
            zonaSeguraDAO.guardar(z3);

            // 4. Crear Incendios en Quito (incluyendo conato REPORTADO para HU-01 y conato con estación asignada para HU-02)
            Incendio inc0 = new Incendio("Conato de incendio forestal recién reportado en Bosque Protector Pichincha. Requiere despacho inmediato de estación.",
                    new Ubicacion(-0.180700, -78.467800, "Bosque Protector Pichincha, Sector Teleférico"),
                    ciudadano1);
            inc0.setFechaHora(LocalDateTime.now().minusMinutes(25));
            inc0.setEstado(EstadoIncendio.REPORTADO);
            inc0.agregarEvidencia(new Evidencia("images/incendio-guapulo.jpg", TipoEvidencia.FOTO));
            incendioDAO.guardar(inc0);

            Incendio inc1 = new Incendio("Conato de incendio forestal de rápida propagación en laderas de Guápulo con abundante humo.",
                    new Ubicacion(-0.198500, -78.472000, "Laderas de Guápulo, Vía De Los Conquistadores"),
                    ciudadano1);
            inc1.setFechaHora(LocalDateTime.now().minusHours(2));
            inc1.setEstado(EstadoIncendio.REPORTADO);
            inc1.agregarEvidencia(new Evidencia("images/incendio-guapulo.jpg", TipoEvidencia.FOTO));
            incendioDAO.guardar(inc1);

            Incendio inc2 = new Incendio("Foco de fuego en pastizales secos cerca de la Quebrada Bellavista. Cuadrilla B3 operando en el punto.",
                    new Ubicacion(-0.174200, -78.468000, "Sector Bellavista Alto, Quebrada"),
                    ciudadano1);
            inc2.setFechaHora(LocalDateTime.now().minusHours(4));
            inc2.setEstado(EstadoIncendio.EN_PROCESO_DE_ATENCION);
            inc2.setEstacionAsignada(estOcupada);
            inc2.agregarEvidencia(new Evidencia("images/incendio-bellavista.jpg", TipoEvidencia.FOTO));
            inc2.getHistorial().add(new HistorialEstado(EstadoIncendio.REPORTADO, EstadoIncendio.EN_PROCESO_DE_ATENCION, bombero1, inc2,
                    "Estación Operativa C (B3) desplegando líneas de ataque con espuma."));
            incendioDAO.guardar(inc2);

            Incendio inc3 = new Incendio("Quema de matorrales en borde de quebrada cerca al Parque Bicentenario. Fuegos aislados.",
                    new Ubicacion(-0.155000, -78.488000, "Av. Real Audiencia, cerca Bicentenario"),
                    ciudadano1);
            inc3.setFechaHora(LocalDateTime.now().minusHours(8));
            inc3.setEstado(EstadoIncendio.BAJO_CONTROL);
            inc3.getHistorial().add(new HistorialEstado(EstadoIncendio.EN_PROCESO_DE_ATENCION, EstadoIncendio.BAJO_CONTROL, bombero1, inc3,
                    "Línea de contención establecida. Labores de enfriamiento en curso."));
            incendioDAO.guardar(inc3);

            // 5. Crear Rutas de Evacuación precargadas
            Ubicacion origenGonzales = new Ubicacion(-0.195000, -78.478000, "Sector González Suárez");
            RutaEvacuacion r1 = new RutaEvacuacion("Ruta 1: Por Av. 12 de Octubre hacia La Carolina",
                    "Ruta rápida por avenidas principales con calzadas amplias y buena visibilidad.",
                    2.8, 1.8, origenGonzales, z1,
                    "[[-0.195000, -78.478000], [-0.190000, -78.481000], [-0.185000, -78.483000], [-0.180653, -78.484838]]");
            rutaDAO.guardar(r1);

            RutaEvacuacion r2 = new RutaEvacuacion("Ruta 2: Por Av. Ladrón de Guevara hacia Coliseo Rumiñahui",
                    "Alternativa hacia el sur por sector La Floresta hacia zona de refugio coliseo.",
                    3.4, 3.2, origenGonzales, z3,
                    "[[-0.195000, -78.478000], [-0.202000, -78.482000], [-0.208000, -78.486000], [-0.212500, -78.489000]]");
            rutaDAO.guardar(r2);

            RutaEvacuacion r3 = new RutaEvacuacion("Ruta 3: Por Av. 6 de Diciembre hacia Parque El Ejido",
                    "Trayecto directo por carril exclusivo Ecovía despejado para contingencias.",
                    4.1, 2.1, origenGonzales, z2,
                    "[[-0.195000, -78.478000], [-0.201000, -78.489000], [-0.207000, -78.494000], [-0.210453, -78.498838]]");
            rutaDAO.guardar(r3);

            inicializado = true;
            System.out.println("[DataInitializer] ¡Datos semilla de AlerFire cargados con éxito!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sembrarEstaciones(EstacionDAO estacionDAO) {
        estacionDAO.guardar(new EstacionBomberos("B1", "Estación Operativa A (Proximidad Inmediata)", true, 0.5,
                new Ubicacion(-0.180000, -78.467000, "Puesto Avanzado Guardaparques")));
        estacionDAO.guardar(new EstacionBomberos("B2", "Estación Operativa B (Sector Urbano)", true, 5.0,
                new Ubicacion(-0.185000, -78.482000, "Av. Amazonas y República")));
        estacionDAO.guardar(new EstacionBomberos("B3", "Estación Operativa C (En Atención Activa)", false, 4.0,
                new Ubicacion(-0.174200, -78.468000, "Sector Bellavista")));
        estacionDAO.guardar(new EstacionBomberos("B4", "Estación Operativa D (Periferia Rural)", true, 10.0,
                new Ubicacion(-0.245000, -78.520000, "Reserva Suroriental")));
    }
}
