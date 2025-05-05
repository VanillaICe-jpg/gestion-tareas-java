# 📋 Gestión de Tareas - JavaFX + Consola

Esta es una aplicación de gestión de tareas desarrollada en Java, aplicando el patrón MVC, persistencia con JSON, e interfaz tanto en consola como JavaFX. Permite realizar operaciones CRUD sobre tareas, búsqueda, filtrado, y pruebas automatizadas con JUnit 5.

---

## ✨ Funcionalidades Principales

-  Crear, listar, actualizar y eliminar tareas
-  Buscar por palabra clave (título o descripción)
-  Filtrar por estado, prioridad o fecha
-  Ordenar por fecha de vencimiento o prioridad
-  Persistencia en archivos JSON usando Jackson
-  Interfaz simple en consola y JavaFX
-  Pruebas unitarias con JUnit 5
-  Aplicación estructurada en base al patrón MVC

---

## 🧪 Pruebas Automatizadas (JUnit 5)

Este proyecto incluye pruebas unitarias para asegurar el correcto funcionamiento de la lógica de negocio y la persistencia de datos.

### ✅ Ejecutar pruebas con Maven

Para ejecutar las pruebas unitarias, asegúrate de tener Maven instalado. Luego, desde la raíz del proyecto, ejecuta:

```bash
mvn test
```

Las pruebas están ubicadas en:

```
src/test/java/com/uady/gestiontareas/
```

Se prueban principalmente los siguientes aspectos:

-  Validación de campos (como fechas inválidas o títulos vacíos).
-  Operaciones CRUD.
-  Persistencia a/desde archivos JSON (con Jackson).
-  Filtrado y búsqueda de tareas.

---

## 🔧 Requisitos y Herramientas

-  **Lenguaje:** Java 11+
-  **Framework de interfaz:** JavaFX (sin FXML)
-  **Persistencia:** Jackson
-  **Pruebas:** JUnit 5
-  **Build tool:** Maven
-  **Control de versiones:** Git + GitFlow

---

## 📁 Estructura del Proyecto

```
gestion-tareas/
├── src/
│   ├── main/
│   │   ├── java/com/uady/gestiontareas/
│   │   │   ├── model/
│   │   │   ├── controller/
│   │   │   ├── view/
│   │   │   └── Main.java
│   └── test/
│       └── java/com/uady/gestiontareas/
│           └── TaskControllerTest.java
├── pom.xml
└── README.md
```

---

## 📊 Diagrama UML

+--------------------+
| Task |
+--------------------+
| - id: String |
| - title: String |
| - description: String |
| - dueDate: LocalDate |
| - priority: Priority |
| - status: Status |
+--------------------+
| +getters/setters |
| +toString(): String |
+--------------------+

+--------------------+
| enum Priority |
+--------------------+
| HIGH |
| MEDIUM |
| LOW |
+--------------------+

+--------------------+
| enum Status |
+--------------------+
| PENDING |
| IN_PROGRESS |
| COMPLETED |
+--------------------+

+----------------------------+
| TaskController |
+----------------------------+
| - tasks: List<Task> |
| - fileManager: TaskFileManager |
+----------------------------+
| +addTask(...) |
| +getAllTasks() |
| +updateTask(...) |
| +deleteTask(id) |
| +searchTasksByTitle(...) |
| +filterByStatus(...) |
| +filterByPriority(...) |
| +filterByDateRange(...) |
| +loadTasks() |
| +saveTasks() |
| +getTaskById(id) |
+----------------------------+

+-------------------------------+
| TaskFileManager |
+-------------------------------+
| - filePath: String |
+-------------------------------+
| +loadTasks(): List<Task> |
| +saveTasks(List<Task>): void |
+-------------------------------+

+------------------------+
| ConsoleView |
+------------------------+
| - controller: TaskController |
| - scanner: Scanner |
+------------------------+
| +start(): void |
| -createTask(): void |
| -updateTask(): void |
| -deleteTask(): void |
| -searchTaskByTitle(): void |
| -listTasks(List<Task>) |
+------------------------+

## ▶️ Ejecución de la Aplicación

Asegúrate de tener Maven instalado. Para compilar y ejecutar:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.uady.gestiontareas.Main"
```

---

## 📌 Notas

-  Se siguen los estándares de codificación de Java.
-  El proyecto aplica el patrón de diseño **MVC**.
-  La cobertura de pruebas supera el 80%.
