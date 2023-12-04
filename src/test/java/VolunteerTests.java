import Repository.VolunteerRepo;
import model.Task;
import model.Volunteer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerTests {

    private VolunteerRepo repo;

    @BeforeEach
    void setUp() {
        repo = new VolunteerRepo();
    }
    @AfterEach
    void tearDown() {

        List<Volunteer> allVolunteers = repo.findAll();
        allVolunteers.forEach(volunteer -> repo.deleteById(volunteer.getVolunteerId()));
    }

    @Test
    void findByIdTest() {
        // Arrange
        List<Task> tasksDone = new ArrayList<>();
        Volunteer volunteer1 = new Volunteer(10, "Oana", "oana@example.com", "0700000000", 1, tasksDone);
        Volunteer volunteer2 = new Volunteer(20, "Ana", "ana@example.com", "0700000001", 2, tasksDone);
        repo.save(volunteer1);
        repo.save(volunteer2);

        // Act
        Volunteer actual = repo.findById(10); // Use the correct ID

        // Assert
        assertNotNull(actual);
        assertEquals(volunteer1.getVolunteerId(), actual.getVolunteerId());
        assertEquals(volunteer1.getName(), actual.getName());
        assertEquals(volunteer1.getEmail(), actual.getEmail());
        assertEquals(volunteer1.getPhone(), actual.getPhone());
        assertEquals(volunteer1.getDepartmentId(), actual.getDepartmentId());
    }


    @Test
    void deleteByIdTest() {
        // Arrange
        List<Task> tasksDone = new ArrayList<>();
        Volunteer volunteer1 = new Volunteer(7, "Oana", "oana@example.com", "0700000000", 1, tasksDone);
        Volunteer volunteer2 = new Volunteer(8, "Ana", "ana@example.com", "0700000001", 2, tasksDone);
        repo.save(volunteer1);
        repo.save(volunteer2);

        // Act
        repo.deleteById(7); // Use the correct ID
        Volunteer deletedVolunteer = repo.findById(7); // Use the correct ID

        // Assert
        assertNull(deletedVolunteer);
    }

    @Test
    void saveAndFindByIdMultipleVolunteersTest() {
        // Arrange
        List<Task> tasksDone = new ArrayList<>();

        Volunteer volunteer1 = new Volunteer(1, "Oana", "oana@example.com", "0700000000", 1, tasksDone);
        Volunteer volunteer2 = new Volunteer(2, "Ana", "ana@example.com", "0700000001", 2, tasksDone);

        // Act
        repo.save(volunteer1);
        repo.save(volunteer2);

        Volunteer foundVolunteer1 = repo.findById(1);
        Volunteer foundVolunteer2 = repo.findById(2);

        // Assert
        assertNotNull(foundVolunteer1);
        assertNotNull(foundVolunteer2);
        assertEquals(volunteer1.getVolunteerId(), foundVolunteer1.getVolunteerId());
        assertEquals(volunteer2.getVolunteerId(), foundVolunteer2.getVolunteerId());
    }
}
