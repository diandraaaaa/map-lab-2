package Repository;

import model.Department;
import model.Volunteer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepo implements Repo{
    private List<Department> departments = new ArrayList<>(); // Initialize ArrayList instead of List

    @Override
    public void save(Object entity) {
        departments.add((Department) entity);
    }

    @Override
    public Department findById(int id) {
        for (Department department : departments){
            if(department.getDepartmentId()==id)
                return department;
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (Department department : departments){
            if(department.getDepartmentId()==id)
                departments.remove(department);
        }
    }

    @Override
    public List<Department> findAll() {
        return departments;
    }
}
