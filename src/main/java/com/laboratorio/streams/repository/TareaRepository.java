package com.laboratorio.streams.repository;

import com.laboratorio.streams.model.EstadoTarea;
import com.laboratorio.streams.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    // Consulta por convención (lista completa)
    List<Tarea> findByEstado(EstadoTarea estado);
    
    // Consulta por convención con Paginación
    Page<Tarea> findByEstado(EstadoTarea estado, Pageable pageable);

    @Query("SELECT t FROM Tarea t WHERE t.prioridad >= :prioridad")
    List<Tarea> buscarPorPrioridadMinima(@Param("prioridad") Integer prioridad);

    // Consulta para resumen agrupado por estado
    @Query("SELECT t.estado, COUNT(t) FROM Tarea t GROUP BY t.estado")
    List<Object[]> contarTareasPorEstado();

}
