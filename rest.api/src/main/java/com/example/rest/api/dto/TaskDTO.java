package com.example.rest.api.dto;

import com.example.rest.api.model.Tag;
import com.example.rest.api.model.Task;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskDTO {
    private Long id;
    private String name;
    private List<String> tags;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.tags = task.getTags().stream()
                .map(Tag::getName) // Mapping from Tag to Tag name
                .collect(Collectors.toList());
    }
}
