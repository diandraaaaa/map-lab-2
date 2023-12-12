package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainingId")
    private int trainingId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Default constructor required by JPA
    public Training() {
    }

    // Constructor for essential fields
    public Training(int trainingId, String name, String description) {
        this.trainingId = trainingId;
        this.name = name;
        this.description = description;
    }

    // Getters and setters

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Other methods if needed
}
