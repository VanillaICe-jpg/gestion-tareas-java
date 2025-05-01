package com.uady.gestiontareas.model;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;

    public Task(String title, String description, LocalDate dueDate, Priority priority, Status status) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
    }

    // Getters and setters (you can generate them in your IDE if needed)

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s", id, title, dueDate, priority, status);
    }
}