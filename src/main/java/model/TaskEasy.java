package model;

public class TaskEasy extends Task{
    public TaskEasy(int taskId, int duration, int volunteerId, String taskDescription) {
        super(taskId, "Easy", duration, volunteerId, taskDescription);
    }
}
