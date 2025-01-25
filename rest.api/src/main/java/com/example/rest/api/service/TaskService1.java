package com.example.rest.api.service;

import com.example.rest.api.model.Assignee;
import com.example.rest.api.model.Tag;
import com.example.rest.api.model.Task;
import com.example.rest.api.repository.AssigneeRepository;
import com.example.rest.api.repository.TagRepository;
import com.example.rest.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService1 {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final AssigneeRepository assigneeRepository;

    @Autowired
    public TaskService1(TaskRepository taskRepository, TagRepository tagRepository, AssigneeRepository assigneeRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
        this.assigneeRepository = assigneeRepository;
    }

    // Tag zu einer Aufgabe hinzufügen
    public void addTagToTask(Long taskId, Tag tag) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        task.getTags().add(tag);
        taskRepository.save(task);
    }

    // Alle Tags einer Aufgabe abrufen
    public List<Tag> getTagsForTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        return task.getTags();
    }

    // Alle assignees einer Aufgabe abrufen
    public List<Assignee> getAssigneesForTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        return task.getAssignees();
    }

    public List<Tag> getAllTags() {
        return tagRepository.AllTags();
    }

    public void updateTaskTags(Long id, List<Tag> tags) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        //first check 3 max tags allowed
        if (tags.size()+task.getTags().size() > 3) {
            throw new IllegalArgumentException("Nur maximal 3 Tags sind erlaubt");
        }
        task.setTags(tags);
        taskRepository.save(task);
    }

    public void updateTaskAssignees(Long id, List<Assignee> assignees) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aufgabe nicht gefunden"));
        //max 3 assignees allowed
        if (assignees.size()+ task.getAssignees().size() > 3) {
            throw new IllegalArgumentException("Nur maximal 3 Assignees sind erlaubt");
        }
        task.setAssignees(assignees);
        taskRepository.save(task);
    }

    public List<Assignee> getAllAssignees() {
        return assigneeRepository.AllAssignees();
    }

    public void addAssignee(Assignee assignee) {
        //check if assignee already exists
        if (assigneeRepository.existsById(assignee.getId())) {
            throw new IllegalArgumentException("Assignee mit ID " + assignee.getId() + " existiert bereits");
        }
        assigneeRepository.save(assignee);
    }
     public void addTag(Tag tag) {
         //check if tag already exists
         if (tagRepository.existsById(tag.getId())) {
             throw new IllegalArgumentException("Tag mit ID " + tag.getId() + " existiert bereits");
         }
         tagRepository.save(tag);
     }
}
