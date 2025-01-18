package com.example.rest.api.dto;

import com.example.rest.api.model.Tag;
import com.example.rest.api.model.Task;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskDTO {
    private Long id;
    private List<String> tags;
    private String name;              // Name der Aufgabe
    private String startDate;         // Startdatum der Aufgabe
    private String endDate;           // Enddatum der Aufgabe
    private String startTime;         // Startzeit der Aufgabe
    private String endTime;           // Endzeit der Aufgabe
    private String priority;          // Priorität: hoch, mittel, niedrig
    private Integer progress;         // Fortschritt: 0-100 in 10er-Schritten
    private String status;            // Status: offen, in Arbeit, erledigt
    private boolean taskCompleted;    // Boolean-Status für "erledigt"

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.tags = task.getTags().stream()
                .map(Tag::getName) // Mapping from Tag to Tag name
                .collect(Collectors.toList());
    }
}
