package model;

public class Factory {
    public static Task createTask(Difficulty taskType) {
        switch (taskType) {
            case Easy:
                return new TaskEasy(1,10, 0, "Incomplete");
            case Medium:
                return new TaskMedium(2, 20, 0, "Incomplete");
            case Hard:
                return new TaskHard(3, 30, 0, "Incomplete");
            default:
                throw new IllegalArgumentException("Unsupported task type: " + taskType);
        }
    }

    private Factory(){}
}
