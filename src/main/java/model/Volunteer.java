package model;

import java.util.List;

public class Volunteer implements Observer {
    private int volunteerId;
    private String name;
    private String email;
    private String phone;
    private int departmentId;
    private List<Task> tasksDone;

    public Volunteer(int volunteerId, String name, String email, String phone,int departmentId, List<Task> tasksDone) {
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
}



