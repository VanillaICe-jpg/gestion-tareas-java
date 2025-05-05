package com.uady.gestiontareas.view;

import com.uady.gestiontareas.controller.TaskController;
import com.uady.gestiontareas.model.Task;
import com.uady.gestiontareas.model.Priority;
import com.uady.gestiontareas.model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.util.List;

public class TaskViewFX {
    private final TaskController controller;
    private final BorderPane mainLayout = new BorderPane();
    private final ObservableList<Task> taskObservableList = FXCollections.observableArrayList();
    private final ListView<Task> taskListView = new ListView<>();
    private final TextField searchField = new TextField();

    public TaskViewFX(TaskController controller) {
        this.controller = controller;

        controller.loadTasks(); // Carga desde JSON
        taskObservableList.addAll(controller.getAllTasks());
        taskListView.setItems(taskObservableList);

        // Botones
        Button addButton = new Button("Agregar");
        Button deleteButton = new Button("Eliminar");
        Button editButton = new Button("Editar");

        addButton.setOnAction(e -> agregarTarea());
        deleteButton.setOnAction(e -> eliminarTarea());
        editButton.setOnAction(e -> editarTarea());

        HBox buttonBox = new HBox(10, addButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));

        VBox listBox = new VBox(10, new Label("Lista de Tareas:"), taskListView);
        listBox.setPadding(new Insets(10));

        // Filtros
        ComboBox<Status> statusFilter = new ComboBox<>();
        statusFilter.getItems().setAll(Status.values());
        statusFilter.setPromptText("Filtrar por estado");

        ComboBox<Priority> priorityFilter = new ComboBox<>();
        priorityFilter.getItems().setAll(Priority.values());
        priorityFilter.setPromptText("Filtrar por prioridad");

        DatePicker dateFilter = new DatePicker();
        dateFilter.setPromptText("Filtrar por fecha");

        Button clearFiltersButton = new Button("Limpiar filtros");
        clearFiltersButton.setOnAction(e -> {
            statusFilter.setValue(null);
            priorityFilter.setValue(null);
            dateFilter.setValue(null);
            searchField.clear();
            aplicarFiltros(statusFilter, priorityFilter, dateFilter, searchField.getText());
        });

        // Búsqueda por título
        searchField.setPromptText("Buscar por título...");
        searchField
                .setOnKeyReleased(e -> aplicarFiltros(statusFilter, priorityFilter, dateFilter, searchField.getText()));

        // Escuchar cambios en los filtros
        statusFilter.setOnAction(e -> aplicarFiltros(statusFilter, priorityFilter, dateFilter, searchField.getText()));
        priorityFilter
                .setOnAction(e -> aplicarFiltros(statusFilter, priorityFilter, dateFilter, searchField.getText()));
        dateFilter.setOnAction(e -> aplicarFiltros(statusFilter, priorityFilter, dateFilter, searchField.getText()));

        // Layout de filtros y búsqueda
        HBox filtrosBox = new HBox(10, searchField, statusFilter, priorityFilter, dateFilter, clearFiltersButton);
        filtrosBox.setPadding(new Insets(10));

        VBox layoutPrincipal = new VBox(10, filtrosBox, listBox);
        mainLayout.setCenter(layoutPrincipal);
        mainLayout.setBottom(buttonBox);
    }

    private void agregarTarea() {
        // Pedir Título
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Nueva Tarea");
        titleDialog.setHeaderText("Ingresa el título de la tarea:");
        titleDialog.setContentText("Título:");
        String title = titleDialog.showAndWait().orElse(null);
        boolean exists = controller.getAllTasks().stream()
                .anyMatch(task -> task.getTitle().equalsIgnoreCase(title));

        if (exists) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ya existe una tarea con este título.");
            alert.showAndWait();
            return;
        }
        if (title == null || title.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("El título no puede estar vacío.");
            alert.showAndWait();
            return;
        }

        // Pedir Descripción
        TextInputDialog descDialog = new TextInputDialog();
        descDialog.setTitle("Nueva Tarea");
        descDialog.setHeaderText("Ingresa la descripción de la tarea:");
        descDialog.setContentText("Descripción:");
        String descripcion = descDialog.showAndWait().orElse(null);
        if (descripcion == null || descripcion.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("La descripción no puede estar vacía.");
            alert.showAndWait();
            return;
        }
        // Seleccionar Prioridad y Estado
        ComboBox<Priority> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(Priority.values());
        priorityBox.setValue(Priority.MEDIUM);

        ComboBox<Status> statusBox = new ComboBox<>();
        statusBox.getItems().addAll(Status.values());
        statusBox.setValue(Status.PENDING);

        Dialog<ButtonType> opcionesDialog = new Dialog<>();
        opcionesDialog.setTitle("Nueva Tarea");
        VBox opcionesContent = new VBox(10, new Label("Prioridad:"), priorityBox, new Label("Estado:"), statusBox);
        opcionesContent.setPadding(new Insets(10));
        opcionesDialog.getDialogPane().setContent(opcionesContent);
        opcionesDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        if (opcionesDialog.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
            return;

        // Pedir Fecha
        DatePicker datePicker = new DatePicker();
        Dialog<LocalDate> dateDialog = new Dialog<>();
        dateDialog.setTitle("Nueva Tarea");
        dateDialog.setHeaderText("Selecciona la fecha de vencimiento:");
        dateDialog.getDialogPane().setContent(datePicker);
        dateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dateDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return datePicker.getValue();
            }
            return null;
        });

        LocalDate fecha = dateDialog.showAndWait().orElse(null);
        if (fecha == null)
            return;

        if (fecha.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("La fecha de vencimiento no puede ser anterior a la fecha actual.");
            alert.showAndWait();
            return;
        }

        // Agregar tarea
        if (controller.addTask(title, descripcion, fecha, priorityBox.getValue(), statusBox.getValue())) {
            controller.saveTasks();
            actualizarLista();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No se pudo agregar la tarea");
            error.setContentText("Revisa los datos ingresados.");
            error.showAndWait();
        }
    }

    private void eliminarTarea() {
        Task seleccionada = taskListView.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Estás seguro de que deseas eliminar esta tarea?");
            confirmacion.setContentText("Título: " + seleccionada.getTitle());

            ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
            ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmacion.getButtonTypes().setAll(botonSi, botonNo);

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == botonSi) {
                    controller.deleteTask(seleccionada.getId());
                    controller.saveTasks();
                    actualizarLista(); // Refresca la vista
                }
            });
        }
    }

    private void editarTarea() {
        Task seleccionada = taskListView.getSelectionModel().getSelectedItem();
        if (seleccionada == null)
            return;

        // Diálogos para editar
        TextInputDialog titleDialog = new TextInputDialog(seleccionada.getTitle());
        titleDialog.setHeaderText("Nuevo título:");
        String nuevoTitulo = titleDialog.showAndWait().orElse(null);
        if (nuevoTitulo == null || nuevoTitulo.isBlank())
            return;

        TextInputDialog descDialog = new TextInputDialog(seleccionada.getDescription());
        descDialog.setHeaderText("Nueva descripción:");
        String nuevaDesc = descDialog.showAndWait().orElse(null);
        if (nuevaDesc == null)
            return;

        DatePicker datePicker = new DatePicker(seleccionada.getDueDate());
        Dialog<LocalDate> dateDialog = new Dialog<>();
        dateDialog.setTitle("Editar Fecha");
        dateDialog.setHeaderText("Selecciona nueva fecha:");
        dateDialog.getDialogPane().setContent(datePicker);
        dateDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dateDialog.setResultConverter(btn -> btn == ButtonType.OK ? datePicker.getValue() : null);
        LocalDate nuevaFecha = dateDialog.showAndWait().orElse(null);
        if (nuevaFecha == null)
            return;

        // Selector de prioridad y estado
        ComboBox<Priority> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll(Priority.values());
        priorityBox.setValue(seleccionada.getPriority());

        ComboBox<Status> statusBox = new ComboBox<>();
        statusBox.getItems().addAll(Status.values());
        statusBox.setValue(seleccionada.getStatus());

        Dialog<ButtonType> comboDialog = new Dialog<>();
        comboDialog.setTitle("Editar Prioridad y Estado");
        VBox content = new VBox(10, new Label("Prioridad:"), priorityBox, new Label("Estado:"), statusBox);
        content.setPadding(new Insets(10));
        comboDialog.getDialogPane().setContent(content);
        comboDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        if (comboDialog.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK)
            return;

        controller.updateTask(seleccionada.getId(), nuevoTitulo, nuevaDesc, nuevaFecha, priorityBox.getValue(),
                statusBox.getValue());
        controller.saveTasks();
        actualizarLista();
    }

    private void aplicarFiltros(ComboBox<Status> statusFilter, ComboBox<Priority> priorityFilter,
            DatePicker dateFilter, String query) {
        Status estado = statusFilter.getValue();
        Priority prioridad = priorityFilter.getValue();
        LocalDate fecha = dateFilter.getValue();

        List<Task> filtradas = controller.getAllTasks().stream()
                .filter(t -> (estado == null || t.getStatus() == estado))
                .filter(t -> (prioridad == null || t.getPriority() == prioridad))
                .filter(t -> (fecha == null || t.getDueDate().isEqual(fecha)))
                .filter(t -> (query == null || t.getTitle().toLowerCase().contains(query.toLowerCase())))
                .toList();

        taskObservableList.setAll(filtradas);
    }

    private void actualizarLista() {
        taskObservableList.setAll(controller.getAllTasks());
    }

    public BorderPane getMainLayout() {
        return mainLayout;
    }
}
