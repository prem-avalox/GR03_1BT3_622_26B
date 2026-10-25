package ec.edu.epn.alerfire.service;

import ec.edu.epn.alerfire.model.EstacionBomberos;
import java.util.List;

public interface IEstacionDAO {
    boolean guardar(EstacionBomberos estacion);
    boolean actualizar(EstacionBomberos estacion);
    EstacionBomberos buscarPorId(Long id);
    List<EstacionBomberos> listarTodas();
}