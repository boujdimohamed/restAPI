package com.example.rest.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Modellklasse für Aufgaben, die in der SQLite-Datenbank gespeichert werden.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                  // Eindeutige ID der Aufgabe

    private String name;              // Name der Aufgabe
    private String startDate;         // Startdatum der Aufgabe
    private String endDate;           // Enddatum der Aufgabe
    private String startTime;         // Startzeit der Aufgabe
    private String endTime;           // Endzeit der Aufgabe
    private String priority;          // Priorität: hoch, mittel, niedrig
    private Integer progress;         // Fortschritt: 0-100 in 10er-Schritten
    private String status;            // Status: offen, in Arbeit, erledigt
    private boolean taskCompleted;    // Boolean-Status für "erledigt"

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")     // Fremdschlüssel für die Tags in der Task-Tabelle
    private List<Tag> tags;           // Liste von Tags für die Aufgabe

    // Standardkonstruktor
    public Task() {
        this.tags = new ArrayList<>(); // Initialisierung der Tags
        this.progress = 0;             // Standardfortschritt: 0%
        this.status = "offen";         // Standardstatus: offen
        this.taskCompleted = false;    // Standard: nicht erledigt
    }

    // Konstruktor mit Parametern
    public Task(Long id, String name, String startDate, String endDate, String startTime, String endTime, String priority) {
        this();
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        if (progress >= 0 && progress <= 100 && progress % 10 == 0) {
            this.progress = progress;

            // Automatische Statuslogik
            if (progress == 100) {
                this.setTaskCompleted(true);
            } else if (progress > 0) {
                this.status = "in Arbeit";
            }
        } else {
            throw new IllegalArgumentException("Fortschritt muss zwischen 0 und 100 in 10er-Schritten liegen.");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
        if (taskCompleted) {
            this.status = "erledigt"; // Status auf erledigt setzen
            this.progress = 100;     // Fortschritt auf 100 setzen
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        if (this.tags != null) {
            this.tags.remove(tag);
        }
    }

    // toString-Methode für Debugging
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", priority='" + priority + '\'' +
                ", progress=" + progress +
                ", status='" + status + '\'' +
                ", taskCompleted=" + taskCompleted +
                ", tags=" + tags +
                '}';
    }
}
