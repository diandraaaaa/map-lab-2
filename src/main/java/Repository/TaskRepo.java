package Repository;

import model.Department;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepo implements Repo{
    private List<Task> tasks = new ArrayList<>(); // Initialize ArrayList instead of List

    @Override
    public void save(Object entity) {
        tasks.add((Task) entity);
    }

    @Override
    public Object findById(int id) {
        for (Task task : tasks){
            if(task.getTaskId()==id)
                return task;
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (Task task : tasks){
            if(task.getTaskId()==id){
                tasks.remove(task);
            }
        }
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }
}
