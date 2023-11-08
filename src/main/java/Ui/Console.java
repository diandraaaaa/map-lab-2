package Ui;

import Repository.DepartmentRepo;
import Repository.TaskRepo;
import Repository.VolunteerRepo;
import model.Department;
import model.Task;
import model.Volunteer;

import java.util.Scanner;

public class Console {
    private VolunteerRepo volunteerManager;
    private DepartmentRepo departmentManager;
    private TaskRepo taskManager;
    public Console() {
        // Initialize the VolunteerManager (you need to implement this class)
        this.volunteerManager = new VolunteerRepo();
        this.departmentManager = new DepartmentRepo();
        this.taskManager = new TaskRepo();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println("\n1. View volunteers");
            System.out.println("2. Add a volunteer");
            System.out.println("3. Delete a volunteer");
            System.out.println("4. Search a volunteer by an ID");
            System.out.println("5. View departments");
            System.out.println("6. Add a department");
            System.out.println("7. Delete a department");
            System.out.println("8. Search a department by an ID");
            System.out.println("9. View tasks");
            System.out.println("10. Add a task");
            System.out.println("11. Delete a task");
            System.out.println("12. Search a task by an ID");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewVolunteers();
                    break;
                case 2:
                    addVolunteer();
                    break;
                case 3:
                    deleteVolunteer();
                    break;
                case 4:
                    searchVolunteerById();
                    break;
                case 5:
                    viewDepartments();
                    break;
                case 6:
                    addDepartment();
                    break;
                case 7:
                    deleteDepartment();
                    break;
                case 8:
                    searchDepartmentById();
                    break;
                case 9:
                    viewTasks();
                    break;
                case 10:
                    addTask();
                    break;
                case 11:
                    deleteTask();
                    break;
                case 12:
                    searchTaskById();
                    break;
                case 0:
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("Goodbye!");
    }

    private void viewVolunteers() {
        for( Volunteer volunteer : volunteerManager.findAll()){
            System.out.println(volunteer.getVolunteerId() + " " + volunteer.getName() + " " + volunteer.getEmail() + " " + volunteer.getPhone());
        }
    }

    private void addVolunteer() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Volunteer ID: ");
        int volunteerId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        // Create a new Volunteer object
        Volunteer newVolunteer = new Volunteer(volunteerId, name, email, phone);

        volunteerManager.save(newVolunteer);
    }

    private void deleteVolunteer() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Volunteer ID: ");
        int volunteerId = scanner.nextInt();
        volunteerManager.deleteById(volunteerId);
    }

    private void searchVolunteerById() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Volunteer ID: ");
        int volunteerId = scanner.nextInt();
        volunteerManager.findById(volunteerId);
    }

    private void viewDepartments() {
        for(Department department : departmentManager.findAll()){
            System.out.println(department.getDepartmentId() +" "+ department.getName()+ " " + department.getCoordinatorId() + " " + department.getDescription() );
        }
    }

    private void addDepartment() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Department Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter coordinatorId: ");
        int coordinatorId = scanner.nextInt();

        // Create a new Volunteer object
        Department newDepartment = new Department(departmentId, name, description, coordinatorId);
        volunteerManager.save(newDepartment);
    }

    private void deleteDepartment() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Department ID: ");
        int departmentId = scanner.nextInt();
        departmentManager.deleteById(departmentId);
    }

    private void searchDepartmentById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Department ID: ");
        int departmentId = scanner.nextInt();
        departmentManager.findById(departmentId);    }

    private void viewTasks() {
        for(Task task : taskManager.findAll()){
            System.out.println(task.getTaskId() + " " + task.getDifficulty() + " " + task.getDuration() + " " + task.getTaskDescription() + " " + task.getVolunteerId());
        }
    }

    private void addTask() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Task ID: ");
        int taskId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter difficulty: ");
        String difficulty = scanner.nextLine();

        System.out.print("Enter taskDescription: ");
        String taskDescription = scanner.nextLine();

        System.out.print("Enter duration: ");
        int duration = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter Volunteer ID: ");
        int volunteerId = scanner.nextInt();

        // Create a new Volunteer object
        Volunteer newVolunteer = new Volunteer(taskId, difficulty, taskDescription, duration, volunteerId);

        volunteerManager.save(newVolunteer);
    }

    private void deleteTask() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Task ID: ");
        int taskId = scanner.nextInt();
        taskManager.deleteById(taskId);
    }

    private void searchTaskById() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter Task ID: ");
        int taskId = scanner.nextInt();
        taskManager.findById(taskId);
    }

    public static void main(String[] args) {
        Console console = new Console();
        console.start();
    }
}
