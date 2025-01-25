package com.example.rest.api.model;

import lombok.Data;
import lombok.Getter;

@Data
public class TaskStatistics {

    // Getter und Setter
    private long open;
    private long inProgress;
    private long completed;
    private long total;


    public TaskStatistics(long open, long inProgress, long completed) {
        this.open = open;
        this.inProgress = inProgress;
        this.completed = completed;
        this.total = open + inProgress + completed;
    }

}