package com.uady.gestiontareas.controller;

import com.uady.gestiontareas.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskController {
    private final List<Task> tasks;

    public TaskController() {
        this.tasks = new ArrayList<>();
    }

    public boolean addTask(String title, String description, LocalDate dueDate, Priority priority, Status status) {
        if (title == null || title.trim().isEmpty())
            return false;
        if (dueDate.isBefore(LocalDate.now()))
            return false;

        Task task = new Task(title, description, dueDate, priority, status);
        tasks.add(task);
        return true;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public List<Task> getTasksSortedByDate() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .toList();
    }

    public List<Task> getTasksSortedByPriority() {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getPriority))
                .toList();
    }
}
