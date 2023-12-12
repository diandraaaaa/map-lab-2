package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    private String difficulty;
    private int duration;
    private int volunteerId;
    private String taskDescription;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Observer> observers = new ArrayList<>();

    // Constructors, getters, setters, and other methods

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    // Default constructor required by JPA
    public Task() {
    }

    // Constructor for essential fields
    public Task(int taskId, String difficulty, int duration, int volunteerId, String taskDescription) {
        this.taskId = taskId;
        this.difficulty = difficulty;
        this.duration = duration;
        this.volunteerId = volunteerId;
        this.taskDescription = taskDescription;
        if (Objects.equals(taskDescription, "Assignment Completed")) {
            notifyObservers();
        }
    }

    // Getters and setters

    public int getTaskId() {
        return taskId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        if (Objects.equals(taskDescription, "Assignment Completed")) {
            notifyObservers();
        }
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    // Other methods if needed
}
