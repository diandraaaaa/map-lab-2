package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skillId")
    private int skillId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Default constructor required by JPA
    public Skill() {
    }

    // Constructor for essential fields
    public Skill(int skillId, String name, String description) {
        this.skillId = skillId;
        this.name = name;
        this.description = description;
    }

    // Getters and setters

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
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
