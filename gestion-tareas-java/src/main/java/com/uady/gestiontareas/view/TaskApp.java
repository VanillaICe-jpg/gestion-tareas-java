package com.uady.gestiontareas.view;

import com.uady.gestiontareas.controller.TaskController;
import com.uady.gestiontareas.view.TaskViewFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TaskApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Iniciando aplicación...");

            TaskController controller = new TaskController();
            System.out.println("Controlador creado");

            TaskViewFX view = new TaskViewFX(controller);
            System.out.println("Vista creada");

            Scene scene = new Scene(view.getMainLayout(), 800, 600);
            primaryStage.setTitle("Gestor de Tareas");
            primaryStage.setScene(scene);
            primaryStage.show();

            System.out.println("Interfaz mostrada");

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación:");
            e.printStackTrace();
        }
    }

}
