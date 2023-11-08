package model;

public class Department {
    public int departmentId;
    public String name;
    public String description;

    public int coordinatorId;

    public int getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCoordinatorId() {
        return coordinatorId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCoordinator(int coordinator) {
        this.coordinatorId = coordinatorId;
    }

    public Department(int departmentId, String name, String description, int coordinatorId) {
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
        this.coordinatorId = coordinatorId;
    }
}
