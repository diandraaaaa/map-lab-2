import model.Task;
import model.Volunteer;
import model.VolunteerBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VolunteerBuilderTest {

    @Test
    void testVolunteerBuilder() {
        // Arrange
        int volunteerId = 1;
        String name = "John";
        String email = "john@example.com";
        String phone = "1234567890";
        List<Task> tasksDone = new ArrayList<>();

        // Act
        Volunteer volunteer = new VolunteerBuilder()
                .setVolunteerId(volunteerId)
                .setName(name)
                .setEmail(email)
                .setPhone(phone)
                .setTasksDone(tasksDone)
                .build();

        // Assert
        assertEquals(volunteerId, volunteer.getVolunteerId(), "VolunteerId mismatch");
        assertEquals(name, volunteer.getName(), "Name mismatch");
        assertEquals(email, volunteer.getEmail(), "Email mismatch");
        assertEquals(phone, volunteer.getPhone(), "Phone mismatch");
        assertEquals(tasksDone, volunteer.getTasksDone(), "TasksDone mismatch");
    }
}
