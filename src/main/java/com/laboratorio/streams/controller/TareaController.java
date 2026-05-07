package com.laboratorio.streams.controller;

import com.laboratorio.streams.dto.TareaDTO;
import com.laboratorio.streams.model.EstadoTarea;
import com.laboratorio.streams.model.Tarea;
import com.laboratorio.streams.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // GET /api/tareas?estado=...&page=0&size=5 (Listar todas con filtros y paginación)
    @GetMapping
    public ResponseEntity<Page<Tarea>> listarTareas(
            @RequestParam(required = false) EstadoTarea estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            
        Page<Tarea> tareas = tareaService.listarTareas(estado, PageRequest.of(page, size));
        return new ResponseEntity<>(tareas, HttpStatus.OK); // 200
    }
    // GET /api/tareas/resumen (Conteo total de tareas agrupadas por estado)
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Long>> obtenerResumen() {
        Map<String, Long> resumen = tareaService.obtenerResumenPorEstado();
        return new ResponseEntity<>(resumen, HttpStatus.OK); // 200
    }
    // GET /api/tareas/{id} (Obtener por ID)
    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        Tarea tarea = tareaService.obtenerPorId(id);
        return new ResponseEntity<>(tarea, HttpStatus.OK); // 200
    }
    // POST /api/tareas (Crear)
    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@Valid @RequestBody TareaDTO tareaDTO) {
        Tarea nuevaTarea = tareaService.crearTarea(tareaDTO);
        return new ResponseEntity<>(nuevaTarea, HttpStatus.CREATED); // 201
    }

    // PUT /api/tareas/{id} (Actualizar)
    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @Valid @RequestBody TareaDTO tareaDTO) {
        Tarea tareaActualizada = tareaService.actualizarTarea(id, tareaDTO);
        return new ResponseEntity<>(tareaActualizada, HttpStatus.OK); // 200
    }

    // DELETE /api/tareas/{id} (Eliminar)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }
}
