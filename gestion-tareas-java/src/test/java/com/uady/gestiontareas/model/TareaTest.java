package com.uady.gestiontareas.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TareaTest {

    private Task crearTareaBasica() {
        return new Task(
                "Tarea de prueba",
                "Descripción de prueba",
                LocalDate.of(2025, 5, 10),
                Priority.MEDIUM,
                Status.PENDING);
    }

    @Test
    public void testCreacionTarea() {
        Task tarea = crearTareaBasica();
        assertNotNull(tarea.getId());
        assertEquals("Tarea de prueba", tarea.getTitle());
        assertEquals("Descripción de prueba", tarea.getDescription());
        assertEquals(LocalDate.of(2025, 5, 10), tarea.getDueDate());
        assertEquals(Priority.MEDIUM, tarea.getPriority());
        assertEquals(Status.PENDING, tarea.getStatus());
    }

    @Test
    void testToString() {
        Task task = new Task("Titulo", "Descripción", LocalDate.now(), Priority.HIGH, Status.PENDING);
        String result = task.toString();
        assertTrue(result.contains("Titulo"));
        assertTrue(result.contains("Descripción"));
    }

    @Test
    void testCambiarEstado() {
        Task tarea = crearTareaBasica();
        tarea.setStatus(Status.COMPLETED);
        assertEquals(Status.COMPLETED, tarea.getStatus());
    }

    @Test
    void testCambiarPrioridad() {
        Task tarea = crearTareaBasica();
        tarea.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, tarea.getPriority());
    }

}
