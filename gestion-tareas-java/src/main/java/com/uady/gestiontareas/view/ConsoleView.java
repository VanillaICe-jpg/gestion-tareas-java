package com.uady.gestiontareas.view;

import com.uady.gestiontareas.controller.TaskController;
import com.uady.gestiontareas.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final TaskController controller;
    private final Scanner scanner;

    public ConsoleView() {
        this.controller = new TaskController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Task Manager ===");
            System.out.println("1. Create Task");
            System.out.println("2. List Tasks");
            System.out.println("3. List Tasks (by Date)");
            System.out.println("4. List Tasks (by Priority)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createTask();
                case 2 -> listTasks(controller.getAllTasks());
                case 3 -> listTasks(controller.getTasksSortedByDate());
                case 4 -> listTasks(controller.getTasksSortedByPriority());
                case 0 -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void createTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Due date (YYYY-MM-DD): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Priority (HIGH, MEDIUM, LOW): ");
        Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Status (PENDING, IN_PROGRESS, COMPLETED): ");
        Status status = Status.valueOf(scanner.nextLine().toUpperCase());

        boolean success = controller.addTask(title, description, dueDate, priority, status);
        if (success) {
            System.out.println("✅ Task created successfully.");
        } else {
            System.out.println("❌ Error: invalid title or past due date.");
        }
    }

    private void listTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("\nID | Title | Due Date | Priority | Status");
        System.out.println("--------------------------------------------------");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}
