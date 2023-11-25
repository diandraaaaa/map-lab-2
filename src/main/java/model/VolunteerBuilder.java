package model;

import java.util.List;

public class VolunteerBuilder {
    private int volunteerId;
    private String name;
    private String email;
    private String phone;
    private int departmentId;
    private List<Task> tasksDone;

    public VolunteerBuilder setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
        return this;
    }

    public VolunteerBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public VolunteerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public VolunteerBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public VolunteerBuilder setTasksDone(List<Task> tasksDone) {
        this.tasksDone = tasksDone;
        return this;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Volunteer build() {
        return new Volunteer(volunteerId, name, email, phone, departmentId, tasksDone);
    }
}
