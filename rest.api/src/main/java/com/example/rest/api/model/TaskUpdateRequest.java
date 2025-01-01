package com.example.rest.api.model;

/**
 * Request-Objekt für das dynamische Aktualisieren einer Aufgabe.
 */
public class TaskUpdateRequest {

    private String name;        // Neuer Name der Aufgabe
    private String date;        // Neues Datum der Aufgabe
    private String startTime;   // Neue Startzeit der Aufgabe
    private String endTime;     // Neue Endzeit der Aufgabe
    private String priority;    // Neue Priorität: hoch, mittel, niedrig
    private Integer progress;   // Neuer Fortschritt: 0-100 in 10er-Schritten

    // Getter und Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        if (progress != null && (progress < 0 || progress > 100 || progress % 10 != 0)) {
            throw new IllegalArgumentException("Fortschritt muss zwischen 0 und 100 in 10er-Schritten liegen.");
        }
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "TaskUpdateRequest{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", priority='" + priority + '\'' +
                ", progress=" + progress +
                '}';
    }
}