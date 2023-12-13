package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Volunteer implements Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int volunteerId;

    private String name;
    private String email;
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

    // ... rest of your code

    public Volunteer() {
    }

    public Volunteer(int volunteerId, String name, String email, String phone,
                     Department department, List<Task> tasksDone,
                     Contract contract, List<Skill> skills, List<Training> trainings) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.tasksDone = tasksDone;
        this.contract = contract;
        this.skills = skills;
        this.trainings = trainings;
    }

    // ... rest of your code

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public void update(Task task) {

    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Task> getTasksDone() {
        return tasksDone;
    }
}

