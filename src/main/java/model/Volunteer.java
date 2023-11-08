package model;

import java.util.ArrayList;
import java.util.List;

public class Volunteer implements Observer {
    public int volunteer_id;
    public String name;
    public String email;
    public String phone;
    public List<Task> tasksDone ;

    public List<Task> getTasksDone() {
        return tasksDone;
    }

    public void setTasksDone(List<Task> tasksDone) {
        this.tasksDone = tasksDone;
    }

    public Volunteer(int taskId, String difficulty, String taskDescription, int duration, int volunteerId) {
        tasksDone= new ArrayList<>();
    }

    public int getVolunteerId() {
        return volunteer_id;
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

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
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

    public Volunteer(int volunteer_id, String name, String email, String phone) {
        this.volunteer_id = volunteer_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    @Override
    public void update(Task task) {

    }
}
