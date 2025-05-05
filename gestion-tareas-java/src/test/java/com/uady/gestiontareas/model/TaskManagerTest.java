package com.uady.gestiontareas.model;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    private static final String TEMP_FILE = "temp_tasks.json";

    @Test
    void testGuardarYCargarTareas() throws IOException {
        // Crear tareas de prueba
        Task task1 = new Task("Tarea 1", "Descripción 1", LocalDate.now(), Priority.HIGH, Status.PENDING);
        Task task2 = new Task("Tarea 2", "Descripción 2", LocalDate.now().plusDays(1), Priority.LOW, Status.COMPLETED);
        List<Task> tareasOriginales = List.of(task1, task2);

        // Guardar tareas a archivo temporal
        try (FileWriter writer = new FileWriter(TEMP_FILE)) {
            TaskFileManager.saveTasks(tareasOriginales);
        }

        // Simular lectura desde archivo original
        List<Task> tareasLeidas = TaskFileManager.loadTasks();

        assertEquals(tareasOriginales.size(), tareasLeidas.size());
        assertEquals(tareasOriginales.get(0).getTitle(), tareasLeidas.get(0).getTitle());

        // Limpieza
        Files.deleteIfExists(Paths.get(TEMP_FILE));
    }

    @Test
    void testCargarDesdeArchivoInexistente() throws IOException {
        // Crear archivo temporal y borrarlo para simular que no existe
        File tempFile = File.createTempFile("no_existe", ".json");
        String ruta = tempFile.getAbsolutePath();
        tempFile.delete(); // Asegura que no existe

        List<Task> tareas = TaskFileManager.loadTasks(ruta);

        assertNotNull(tareas);
        assertTrue(tareas.isEmpty());
    }

}
