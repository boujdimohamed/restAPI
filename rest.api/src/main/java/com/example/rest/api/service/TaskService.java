package com.example.rest.api.service;

import com.example.rest.api.model.Task;
import com.example.rest.api.model.TaskStatistics;
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
        task.setTaskCompleted(task.getProgress() == 100);
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


    public TaskStatistics getTaskStatistics() {
            List<Task> tasks = taskRepository.findAll(); // Holt alle Aufgaben aus der DB

            long open = tasks.stream()
                    .filter(task -> "offen".equalsIgnoreCase(task.getStatus()))
                    .count();

            long inProgress = tasks.stream()
                    .filter(task -> "in Arbeit".equalsIgnoreCase(task.getStatus()))
                    .count();

            long completed = tasks.stream()
                    .filter(task -> "erledigt".equalsIgnoreCase(task.getStatus()))
                    .count();

            return new TaskStatistics(open, inProgress, completed);
        }

    public void updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setStartDate(task.getStartDate());
        existingTask.setEndDate(task.getEndDate());
        existingTask.setStartTime(task.getStartTime());
        existingTask.setEndTime(task.getEndTime());
        existingTask.setPriority(task.getPriority());
        existingTask.setProgress(task.getProgress());
        existingTask.setStatus(task.getStatus());
        existingTask.setTaskCompleted(task.getProgress() == 100);
        taskRepository.save(existingTask);
    }
}
