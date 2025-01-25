package com.example.rest.api.controller;

import com.example.rest.api.dto.TaskDTO;
import com.example.rest.api.model.Assignee;
import com.example.rest.api.model.Tag;
import com.example.rest.api.model.Task;
import com.example.rest.api.model.TaskStatistics;
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
@CrossOrigin(origins = "http://localhost:5173")
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
        // update Task
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            taskService.updateTask(id, task);
            return ResponseEntity.ok("Task erfolgreich aktualisiert.");
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

    // all predefined tags to select
    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getTags() {
        List<Tag> tags = taskService1.getAllTags();
        if (tags.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tags);
    }
    // all existing assignees to select from
    @GetMapping("/assignees")
    public ResponseEntity<List<Assignee>> getAssignees() {
        List<Assignee> assignees = taskService1.getAllAssignees();
        if (assignees.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(assignees);
    }
     //end point update task tag (required: the max of tags is 3 for a task)
    @PutMapping("/{id}/tags")
    public ResponseEntity<String> updateTaskTags(@PathVariable Long id, @RequestBody List<Tag> tags) {
        try {
            taskService1.updateTaskTags(id, tags);
            return ResponseEntity.ok("Tags erfolgreich aktualisiert.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    //end point update task assignees (required: the max of assignees is 3 for a task)
    @PutMapping("/{id}/assignees")
    public ResponseEntity<String> updateTaskAssignees(@PathVariable Long id, @RequestBody List<Assignee> assignees) {
        try {
            taskService1.updateTaskAssignees(id, assignees);
            return ResponseEntity.ok("Assignees erfolgreich aktualisiert.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //count of tags for a task
    @GetMapping("/{id}/tags/count")
    public ResponseEntity<Integer> countTagsForTask(@PathVariable Long id) {
        try {
            int count = taskService1.getTagsForTask(id).size();
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    // Hinzufügen eines Tags zu einer Aufgabe
    @PostMapping("/{id}/tags")
    public ResponseEntity<String> addTagToTask(@PathVariable Long id, @RequestBody Tag tag) {
        try {
            taskService1.addTagToTask(id, tag);
            return ResponseEntity.ok("Tag '" + tag.getLabel() + "' erfolgreich zur Aufgabe hinzugefügt.");
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
// Abrufen von Assignees für eine Aufgabe
    @GetMapping("/{id}/assignees")
    public ResponseEntity<List<Assignee>> getAssigneesForTask(@PathVariable Long id) {
        try {
            List<Assignee> assignees = taskService1.getAssigneesForTask(id);
            return ResponseEntity.ok(assignees);
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

    @GetMapping("/statistics")
    public TaskStatistics getTaskStatistics() {
        return taskService.getTaskStatistics();
    }

    // add assignee to predifined list of assignees
    @PostMapping("/assignees/add")
    public ResponseEntity<String> addAssignee(@RequestBody Assignee assignee) {
        taskService1.addAssignee(assignee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Assignee erfolgreich hinzugefügt.");
    }

    //add tag to predifined list of tags
    @PostMapping("/tags/add")
    public ResponseEntity<String> addTag(@RequestBody Tag tag) {
        taskService1.addTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag erfolgreich hinzugefügt.");
    }
}
