package com.uady.gestiontareas.controller;

import com.uady.gestiontareas.model.Task;
import com.uady.gestiontareas.model.TaskFileManager;
import com.uady.gestiontareas.model.Priority;
import com.uady.gestiontareas.model.Status;
import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class TaskController {
    private List<Task> tasks;

    public TaskController() {
        // Cargar tareas desde el archivo JSON
        this.tasks = new java.util.ArrayList<>(TaskFileManager.loadTasks());
        this.tasks = new ArrayList<>(TaskFileManager.loadTasks());

    }

    public void loadTasks() {
        List<Task> loadedTasks = TaskFileManager.loadTasks();
        if (loadedTasks != null) {
            tasks = new ArrayList<>(loadedTasks); // reemplaza la lista anterior con una mutable
        }
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public boolean addTask(String title, String description, LocalDate dueDate, Priority priority, Status status) {
        if (title == null || title.trim().isEmpty())
            return false;
        if (dueDate.isBefore(LocalDate.now()))
            return false;

        // Comprobamos que el título no esté repetido
        boolean exists = tasks.stream().anyMatch(task -> task.getTitle().equalsIgnoreCase(title));
        if (exists) {
            return false; // El título ya existe
        }

        Task task = new Task(title, description, dueDate, priority, status);
        tasks.add(task);

        // Guardamos las tareas después de agregar
        TaskFileManager.saveTasks(tasks);
        return true;
    }

    public boolean updateTask(String id, String title, String description, LocalDate dueDate, Priority priority,
            Status status) {
        Task task = getTaskById(id);
        if (task == null)
            return false;

        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);

        // Guardamos las tareas después de editar
        TaskFileManager.saveTasks(tasks);
        return true;
    }

    public Task getTaskById(String id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteTask(String id) {
        boolean removed = tasks.removeIf(t -> t.getId().equals(id));

        if (removed) {
            TaskFileManager.saveTasks(tasks); // Guardamos después de eliminar
        }

        return removed;
    }

    // Método para obtener tareas ordenadas por fecha de vencimiento
    public List<Task> getTasksSortedByDate() {
        tasks.sort(Comparator.comparing(Task::getDueDate)); // Ordena las tareas por fecha de vencimiento
        return tasks;
    }

    // Método para obtener tareas ordenadas por prioridad
    public List<Task> getTasksSortedByPriority() {
        tasks.sort(Comparator.comparing(Task::getPriority));
        return tasks;
    }

    public List<Task> searchTasksByTitle(String query) {
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public void saveTasks() {
        TaskFileManager.saveTasks(tasks);
    }

    public List<Task> filterByStatus(Status status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public List<Task> filterByPriority(Priority priority) {
        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .toList();
    }

    public List<Task> filterByDateRange(LocalDate start, LocalDate end) {
        return tasks.stream()
                .filter(task -> !task.getDueDate().isBefore(start) && !task.getDueDate().isAfter(end))
                .toList();
    }

}
