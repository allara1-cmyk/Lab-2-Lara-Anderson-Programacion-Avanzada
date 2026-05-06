package com.laboratorio.streams.service;

import com.laboratorio.streams.dto.TareaDTO;
import com.laboratorio.streams.exception.ResourceNotFoundException;
import com.laboratorio.streams.model.EstadoTarea;
import com.laboratorio.streams.model.Tarea;
import com.laboratorio.streams.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    // Listar con filtro opcional y paginación
    public Page<Tarea> listarTareas(EstadoTarea estado, Pageable pageable) {
        if (estado != null) {
            return tareaRepository.findByEstado(estado, pageable);
        }
        return tareaRepository.findAll(pageable);
    }

    public Tarea obtenerPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con el ID: " + id));
    }

    public Tarea crearTarea(TareaDTO tareaDTO) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstado(tareaDTO.getEstado());
        tarea.setPrioridad(tareaDTO.getPrioridad());
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(Long id, TareaDTO tareaDTO) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la tarea con el ID: " + id));
        
        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());
        tarea.setEstado(tareaDTO.getEstado());
        tarea.setPrioridad(tareaDTO.getPrioridad());
        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró la tarea con el ID: " + id);
        }
        tareaRepository.deleteById(id);
    }

    public Map<String, Long> obtenerResumenPorEstado() {
        List<Object[]> resultados = tareaRepository.contarTareasPorEstado();
        Map<String, Long> resumen = new HashMap<>();
        for (Object[] fila : resultados) {
            EstadoTarea estado = (EstadoTarea) fila[0];
            Long cantidad = (Long) fila[1];
            resumen.put(estado != null ? estado.name() : "DESCONOCIDO", cantidad);
        }
        return resumen;
    }
}
