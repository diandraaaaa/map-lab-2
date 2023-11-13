package model;

public class TaskMedium extends Task{
    public TaskMedium(int taskId, int duration, int volunteerId, String taskDescription) {
        super(taskId, "Medium", duration, volunteerId, taskDescription);
    }
}
