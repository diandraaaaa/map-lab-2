package model;

import java.util.List;

//singleton pattern
public class President extends Volunteer {
    private static President instance; // Private static instance variable
    private int salary;

    private President(int volunteer_id, String name, String email, String phone, List<Task> tasksDone) {
        super(volunteer_id, name, email, phone, tasksDone );
        this.salary = 0; // Default salary
    }

    public static President getInstance(int volunteer_id, String name, String email, String phone, List<Task> tasksDone) {
        if (instance == null) {
            instance = new President(volunteer_id, name, email, phone, tasksDone);
        }
        return instance;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
