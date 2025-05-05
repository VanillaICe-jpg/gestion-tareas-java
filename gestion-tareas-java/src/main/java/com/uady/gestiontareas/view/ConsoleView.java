/*
 * package com.uady.gestiontareas.view;
 * 
 * import com.uady.gestiontareas.controller.TaskController;
 * import com.uady.gestiontareas.model.*;
 * 
 * import java.time.LocalDate;
 * import java.util.List;
 * import java.util.Scanner;
 * 
 * public class ConsoleView {
 * private final TaskController controller;
 * private final Scanner scanner;
 * 
 * public ConsoleView() {
 * this.controller = new TaskController();
 * this.scanner = new Scanner(System.in);
 * }
 * 
 * public void start() {
 * 
 * controller.loadTasks();
 * 
 * boolean running = true;
 * while (running) {
 * System.out.println("1. Create Task");
 * System.out.println("2. List Tasks");
 * System.out.println("3. List Tasks (by Date)");
 * System.out.println("4. List Tasks (by Priority)");
 * System.out.println("5. Update Task");
 * System.out.println("6. Delete Task");
 * System.out.println("7. Search Task by Title");
 * System.out.println("8. Filter by status");
 * System.out.println("9. Filter by priority");
 * System.out.println("10. Filter by date range");
 * System.out.println("0. Exit");
 * 
 * System.out.print("Choose an option: ");
 * int choice = Integer.parseInt(scanner.nextLine());
 * 
 * switch (choice) {
 * case 1 -> {
 * createTask();
 * controller.saveTasks();
 * }
 * case 2 -> listTasks(controller.getAllTasks());
 * case 3 -> listTasks(controller.getTasksSortedByDate());
 * case 4 -> listTasks(controller.getTasksSortedByPriority());
 * case 5 -> {
 * updateTask();
 * controller.saveTasks();
 * }
 * case 6 -> deleteTask();
 * case 7 -> searchTaskByTitle();
 * case 8 -> {
 * System.out.print("Enter status (PENDING, IN_PROGRESS, COMPLETED): ");
 * Status status = Status.valueOf(scanner.nextLine().toUpperCase());
 * controller.filterByStatus(status).forEach(System.out::println);
 * }
 * case 9 -> {
 * System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
 * Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
 * controller.filterByPriority(priority).forEach(System.out::println);
 * }
 * case 10 -> {
 * System.out.print("Enter start date (YYYY-MM-DD): ");
 * LocalDate start = LocalDate.parse(scanner.nextLine());
 * System.out.print("Enter end date (YYYY-MM-DD): ");
 * LocalDate end = LocalDate.parse(scanner.nextLine());
 * controller.filterByDateRange(start, end).forEach(System.out::println);
 * }
 * 
 * case 0 -> {
 * controller.saveTasks();
 * System.out.println(" Exiting and saving tasks...");
 * running = false;
 * }
 * 
 * default -> System.out.println("Invalid option");
 * }
 * }
 * }
 * 
 * private void createTask() {
 * System.out.print("Title: ");
 * String title = scanner.nextLine();
 * 
 * System.out.print("Description: ");
 * String description = scanner.nextLine();
 * 
 * System.out.print("Due date (YYYY-MM-DD): ");
 * LocalDate dueDate = LocalDate.parse(scanner.nextLine());
 * 
 * System.out.print("Priority (HIGH, MEDIUM, LOW): ");
 * Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
 * 
 * System.out.print("Status (PENDING, IN_PROGRESS, COMPLETED): ");
 * Status status = Status.valueOf(scanner.nextLine().toUpperCase());
 * 
 * boolean success = controller.addTask(title, description, dueDate, priority,
 * status);
 * if (success) {
 * System.out.println("✅ Task created successfully.");
 * } else {
 * System.out.println("❌ Error: invalid title or past due date.");
 * }
 * }
 * 
 * private void updateTask() {
 * System.out.print("Enter task ID to update: ");
 * String id = scanner.nextLine();
 * Task task = controller.getTaskById(id);
 * if (task == null) {
 * System.out.println("❌ Task not found.");
 * return;
 * }
 * 
 * System.out.print("New Title [" + task.getTitle() + "]: ");
 * String title = scanner.nextLine();
 * if (title.isEmpty())
 * title = task.getTitle();
 * 
 * System.out.print("New Description [" + task.getDescription() + "]: ");
 * String description = scanner.nextLine();
 * if (description.isEmpty())
 * description = task.getDescription();
 * 
 * System.out.print("New Due Date (YYYY-MM-DD) [" + task.getDueDate() + "]: ");
 * String dateInput = scanner.nextLine();
 * LocalDate dueDate = dateInput.isEmpty() ? task.getDueDate() :
 * LocalDate.parse(dateInput);
 * 
 * System.out.print("New Priority (HIGH, MEDIUM, LOW) [" + task.getPriority() +
 * "]: ");
 * String prioInput = scanner.nextLine();
 * Priority priority = prioInput.isEmpty() ? task.getPriority() :
 * Priority.valueOf(prioInput.toUpperCase());
 * 
 * System.out.print("New Status (PENDING, IN_PROGRESS, COMPLETED) [" +
 * task.getStatus() + "]: ");
 * String statInput = scanner.nextLine();
 * Status status = statInput.isEmpty() ? task.getStatus() :
 * Status.valueOf(statInput.toUpperCase());
 * 
 * boolean success = controller.updateTask(id, title, description, dueDate,
 * priority, status);
 * System.out.println(success ? "✅ Task updated." : "❌ Failed to update.");
 * }
 * 
 * private void deleteTask() {
 * System.out.print("Enter task ID to delete: ");
 * String id = scanner.nextLine();
 * boolean deleted = controller.deleteTask(id);
 * System.out.println(deleted ? "🗑️ Task deleted." : "❌ Task not found.");
 * }
 * 
 * private void searchTaskByTitle() {
 * System.out.print("Enter keyword to search: ");
 * String query = scanner.nextLine();
 * List<Task> results = controller.searchTasksByTitle(query);
 * listTasks(results);
 * }
 * 
 * private void listTasks(List<Task> tasks) {
 * if (tasks.isEmpty()) {
 * System.out.println("No tasks found.");
 * return;
 * }
 * 
 * System.out.println("\nID | Title | Due Date | Priority | Status");
 * System.out.println("--------------------------------------------------");
 * for (Task task : tasks) {
 * System.out.println(task);
 * }
 * }
 * }
 */