package model;

public class TaskHard extends Task{
    public TaskHard(int taskId, int duration, int volunteerId, String taskDescription) {
        super(taskId, "Hard", duration, volunteerId, taskDescription);
    }
}
