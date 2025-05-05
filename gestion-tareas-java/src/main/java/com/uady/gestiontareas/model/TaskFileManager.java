package com.uady.gestiontareas.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class TaskFileManager {

    private static final String FILE_PATH = "task.json";

    // Gson configurado con soporte para LocalDate
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, type, context) -> new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();

    // Cargar tareas desde el archivo JSON
    public static List<Task> loadTasks() {
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return List.of(); // Si el archivo no existe, retorna lista vacía
            }

            FileReader reader = new FileReader(FILE_PATH);
            Type listType = new TypeToken<List<Task>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Guardar tareas en el archivo JSON
    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
