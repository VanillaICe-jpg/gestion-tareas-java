package com.uady.gestiontareas.controller;

import com.uady.gestiontareas.model.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TareaControllerTest {

    private TaskController controller;

    @BeforeEach
    public void setUp() {
        controller = new TaskController();
        controller.getAllTasks().clear(); // elimina todas las tareas
        controller.saveTasks(); // guarda la lista vacía en el archivo

        // Limpia la lista de tareas para pruebas controladas
        List<Task> tareasActuales = controller.getAllTasks();
        for (int i = 0; i < tareasActuales.size(); i++) {
            controller.deleteTask(tareasActuales.get(i).getId());
        }
    }

    @Test
    public void testAgregarTareaValida() {
        boolean resultado = controller.addTask(
                "Tarea de prueba",
                "Descripción",
                LocalDate.now().plusDays(1),
                Priority.HIGH,
                Status.PENDING);

        assertTrue(resultado);
        assertEquals(1, controller.getAllTasks().size());
    }

    @Test
    public void testAgregarTareaSinTitulo() {
        boolean resultado = controller.addTask(
                "",
                "Sin título",
                LocalDate.now().plusDays(1),
                Priority.MEDIUM,
                Status.PENDING);

        assertFalse(resultado);
        assertTrue(controller.getAllTasks().isEmpty());
    }

    @Test
    public void testAgregarTareaConFechaPasada() {
        boolean resultado = controller.addTask(
                "Tarea vieja",
                "Descripción",
                LocalDate.now().minusDays(1),
                Priority.LOW,
                Status.COMPLETED);

        assertFalse(resultado);
    }

    @Test
    public void testActualizarTarea() {
        controller.addTask(
                "Original",
                "Desc",
                LocalDate.now().plusDays(2),
                Priority.LOW,
                Status.PENDING);

        Task t = controller.getAllTasks().get(0);

        boolean actualizado = controller.updateTask(
                t.getId(),
                "Actualizada",
                "Nueva desc",
                LocalDate.now().plusDays(3),
                Priority.HIGH,
                Status.IN_PROGRESS);

        assertTrue(actualizado);
        Task actualizada = controller.getTaskById(t.getId());
        assertEquals("Actualizada", actualizada.getTitle());
    }

    @Test
    public void testEliminarTarea() {
        controller.addTask("Eliminar", "desc", LocalDate.now().plusDays(1), Priority.MEDIUM, Status.PENDING);
        Task t = controller.getAllTasks().get(0);

        boolean eliminado = controller.deleteTask(t.getId());

        assertTrue(eliminado);
        assertTrue(controller.getAllTasks().isEmpty());
    }

    @Test
    public void testBuscarPorTitulo() {
        controller.addTask("Estudiar Java", "", LocalDate.now().plusDays(2), Priority.MEDIUM, Status.PENDING);
        controller.addTask("Leer libro", "", LocalDate.now().plusDays(3), Priority.LOW, Status.COMPLETED);

        List<Task> resultados = controller.searchTasksByTitle("java");

        assertEquals(1, resultados.size());
        assertEquals("Estudiar Java", resultados.get(0).getTitle());
    }

    @Test
    public void testFiltrarPorPrioridad() {
        controller.addTask("Alta", "", LocalDate.now().plusDays(1), Priority.HIGH, Status.PENDING);
        controller.addTask("Baja", "", LocalDate.now().plusDays(1), Priority.LOW, Status.PENDING);

        List<Task> altas = controller.filterByPriority(Priority.HIGH);

        assertEquals(1, altas.size());
        assertEquals("Alta", altas.get(0).getTitle());
    }

    @Test
    public void testOrdenarPorFecha() {
        controller.addTask("Primera", "", LocalDate.now().plusDays(1), Priority.LOW, Status.PENDING);
        controller.addTask("Segunda", "", LocalDate.now().plusDays(3), Priority.HIGH, Status.PENDING);

        List<Task> ordenadas = controller.getTasksSortedByDate();
        assertEquals("Primera", ordenadas.get(0).getTitle());
    }

    @Test
    public void testFiltrarPorRangoDeFechas() {
        controller.addTask("Día 1", "", LocalDate.of(2025, 5, 10), Priority.MEDIUM, Status.PENDING);
        controller.addTask("Día 2", "", LocalDate.of(2025, 5, 15), Priority.MEDIUM, Status.PENDING);

        List<Task> filtradas = controller.filterByDateRange(
                LocalDate.of(2025, 5, 9),
                LocalDate.of(2025, 5, 12));

        assertEquals(1, filtradas.size());
        assertEquals("Día 1", filtradas.get(0).getTitle());
    }
}
