package model;

import jakarta.persistence.*;


@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;
    private String name;
    private String description;

    // Default constructor required by JPA
    public Department() {
    }

    // Constructor for essential fields
    public Department(int departmentId, String name, String description) {
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
    }

    // Getters and setters

    public int getDepartmentId() {
        return departmentId;
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

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    // Other methods if needed
}
