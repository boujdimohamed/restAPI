package com.example.rest.api.service;

import com.example.rest.api.model.Tag;
import com.example.rest.api.model.Task;
import com.example.rest.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService1 {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService1(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Tag zu einer Aufgabe hinzufügen
    public void addTagToTask(Long taskId, Tag tag) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        task.addTag(tag);
        taskRepository.save(task);
    }

    // Alle Tags einer Aufgabe abrufen
    public List<Tag> getTagsForTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        return task.getTags();
    }
}
