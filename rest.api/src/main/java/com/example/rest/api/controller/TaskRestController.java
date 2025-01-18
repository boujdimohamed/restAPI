package com.example.rest.api.controller;

import com.example.rest.api.dto.TaskDTO;
import com.example.rest.api.model.Tag;
import com.example.rest.api.model.Task;
import com.example.rest.api.service.TaskService;
import com.example.rest.api.service.TaskService1;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController
@RequestMapping(path = "api/v1/task")
public class TaskRestController {

    private final TaskService taskService;
    private final TaskService1 taskService1;

    public TaskRestController(TaskService taskService, TaskService1 taskService1) {
        this.taskService = taskService;
        this.taskService1 = taskService1;
    }

    // Starten einer Aufgabe (Status ändern)
    @PutMapping("/{id}/start")
    public ResponseEntity<String> startTask(@PathVariable Long id) {
        try {
            taskService.startTask(id); // Ruft die Logik im Service auf
            return ResponseEntity.ok("Task status updated to 'in Arbeit'");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Hinzufügen einer neuen Aufgabe
    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aufgabe erfolgreich hinzugefügt.");
    }

    // Löschen einer Aufgabe
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTaskById(id);
        if (deleted) {
            return ResponseEntity.ok("Aufgabe erfolgreich gelöscht.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aufgabe mit ID " + id + " nicht gefunden.");
        }
    }

    // Abrufen aller Aufgaben
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = taskService.getAllTasks();
                /*.stream()
                .map(TaskDTO::new)
                .collect(Collectors.toList());*/
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    // Markieren einer Aufgabe als abgeschlossen
    @PutMapping("/{id}/complete")
    public ResponseEntity<String> markTaskAsCompleted(@PathVariable Long id) {
        try {
            taskService.markTaskAsCompleted(id);
            return ResponseEntity.ok("Aufgabe erfolgreich als 'erledigt' markiert.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Hinzufügen eines Tags zu einer Aufgabe
    @PostMapping("/{id}/tags")
    public ResponseEntity<String> addTagToTask(@PathVariable Long id, @RequestBody Tag tag) {
        try {
            taskService1.addTagToTask(id, tag);
            return ResponseEntity.ok("Tag '" + tag.getName() + "' erfolgreich zur Aufgabe hinzugefügt.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Abrufen von Tags für eine Aufgabe
    @GetMapping("/{id}/tags")
    public ResponseEntity<List<Tag>> getTagsForTask(@PathVariable Long id) {
        try {
            List<Tag> tags = taskService1.getTagsForTask(id);
            return ResponseEntity.ok(tags);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Abrufen einer Aufgabe nach ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.findTaskById(id);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
