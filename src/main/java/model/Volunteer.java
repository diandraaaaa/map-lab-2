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

    private int departmentId;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Task> tasksDone;

    // Default constructor required by JPA
    public Volunteer() {
    }

    // Constructor for dependency injection
    public Volunteer(int volunteerId, String name, String email, String phone, int departmentId, List<Task> tasksDone) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
        this.tasksDone = tasksDone;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public List<Task> getTasksDone() {
        return tasksDone;
    }

    @Override
    public void update(Task task) {
        tasksDone.add(task);
    }

    public static VolunteerBuilder builder() {
        return new VolunteerBuilder();
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setTasksDone(List<Task> tasksDone) {
        this.tasksDone = tasksDone;
    }
}
