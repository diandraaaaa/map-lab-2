package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Volunteer implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "volunteerId")
    private int volunteerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "departmentId", referencedColumnName = "departmentId")
    private Department department;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Task> tasksDone;

    @OneToOne(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private Contract contract;

    @ManyToMany
    @JoinTable(
            name = "Volunteer_Skill",
            joinColumns = @JoinColumn(name = "volunteerId"),
            inverseJoinColumns = @JoinColumn(name = "skillId")
    )
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "Volunteer_Training",
            joinColumns = @JoinColumn(name = "volunteerId"),
            inverseJoinColumns = @JoinColumn(name = "trainingId")
    )
    private List<Training> trainings;

    @OneToOne(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private Award award;

    // Assuming you have a many-to-many relationship with projects
    @ManyToMany(mappedBy = "volunteers")
    private List<Project> projects;

    // ... rest of your code

    public Volunteer() {
    }

    public Volunteer(int volunteerId, String name, String email, String phone,
                     Department department, List<Task> tasksDone,
                     Contract contract, List<Skill> skills, List<Training> trainings,
                     Award award, List<Project> projects) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.tasksDone = tasksDone;
        this.contract = contract;
        this.skills = skills;
        this.trainings = trainings;
        this.award = award;
        this.projects = projects;
    }

    // ... rest of your code

    // Getters and setters for the new fields

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public void update(Task task) {
        // Implementation of the update method
    }

    // ... rest of your code
}
