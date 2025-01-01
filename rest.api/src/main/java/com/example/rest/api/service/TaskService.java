package com.example.rest.api.service;

import com.example.rest.api.model.Task;
import com.example.rest.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Eine neue Aufgabe hinzufügen
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    // Alle Aufgaben abrufen
    @Transactional
    public List<Task> getAllTasks() {
        List<Task> tasks=taskRepository.findAll();
        // Initialize the tags collection to avoid LazyInitializationException
        tasks.forEach(task -> task.getTags().size());
        return tasks;
    }

    // Eine Aufgabe anhand ihrer ID finden
    public Task findTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);  // Gibt null zurück, wenn keine Aufgabe gefunden wird
    }

    // Eine Aufgabe nach ID löschen
    public boolean deleteTaskById(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Aufgabe als erledigt markieren
    public void markTaskAsCompleted(Long id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setTaskCompleted(true);
            task.setStatus("erledigt");
            task.setProgress(100);
            taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Aufgabe mit ID " + id + " nicht gefunden.");
        }
    }

    // Aufgabe starten (Status ändern)
    public void startTask(Long id) {
        Task task = findTaskById(id);
        if (task != null) {
            task.setStatus("in Arbeit");
            taskRepository.save(task);
        } else {
            throw new IllegalArgumentException("Aufgabe mit ID " + id + " nicht gefunden.");
        }
    }

    
}
