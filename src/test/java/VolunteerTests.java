import Repository.VolunteerRepo;
import model.Volunteer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerTests {

    @Test
    void findByIdTest() {
        // Arrange
        Volunteer volunteer1 = new Volunteer(1, "Oana", "oana@example.com", "0700000000");
        Volunteer volunteer2 = new Volunteer(2, "Ana", "ana@example.com", "0700000001");
        VolunteerRepo repo = new VolunteerRepo();
        repo.save(volunteer1);
        repo.save(volunteer2);

        // Act
        Volunteer actual = repo.findById(1);

        // Assert
        assertEquals(volunteer1.getVolunteerId(), actual.getVolunteerId());
        assertEquals(volunteer1.getName(), actual.getName());
        assertEquals(volunteer1.getEmail(), actual.getEmail());
        assertEquals(volunteer1.getPhone(), actual.getPhone());
    }

    @Test
    void deleteByIdTest() {
        // Arrange
        Volunteer volunteer1 = new Volunteer(1, "Oana", "oana@example.com", "0700000000");
        Volunteer volunteer2 = new Volunteer(2, "Ana", "ana@example.com", "0700000001");
        VolunteerRepo repo = new VolunteerRepo();
        repo.save(volunteer1);
        repo.save(volunteer2);

        // Act
        repo.deleteById(1);
        Volunteer deletedVolunteer = repo.findById(1);

        // Assert
        assertNull(deletedVolunteer);
    }

//    @Test
//    void findAllTest() {
//        // Arrange
//        Volunteer volunteer1 = new Volunteer(1, "Oana", "oana@example.com", "0700000000");
//        Volunteer volunteer2 = new Volunteer(2, "Ana", "ana@example.com", "0700000001");
//        VolunteerRepo repo = new VolunteerRepo();
//        repo.save(volunteer1);
//        repo.save(volunteer2);
//
//        // Act
//        List allVolunteers = repo.findAll();
//
//        // Assert
//        assertNotNull(allVolunteers);
//        assertEquals(2, allVolunteers.size());
//        assertTrue(allVolunteers.contains(volunteer1));
//        assertTrue(allVolunteers.contains(volunteer2));
//    }

    @Test
    void saveAndFindByIdMultipleVolunteersTest() {
        // Arrange
        Volunteer volunteer1 = new Volunteer(1, "Oana", "oana@example.com", "0700000000");
        Volunteer volunteer2 = new Volunteer(2, "Ana", "ana@example.com", "0700000001");
        VolunteerRepo repo = new VolunteerRepo();

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
