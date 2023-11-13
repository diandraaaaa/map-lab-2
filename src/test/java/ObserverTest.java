import model.Task;
import model.Volunteer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverTest {

    @Test
    void testObserverPattern() {
        // Arrange
        List<Task> tasksDone = new ArrayList<>();
        Volunteer volunteer = Volunteer.builder()
                .setVolunteerId(1)
                .setName("John")
                .setEmail("john@example.com")
                .setPhone("1234567890")
                .setTasksDone(tasksDone)
                .build();

        Task task = new Task(123, "hard",30, 1, "." );

        // Act
        task.addObserver(volunteer);
        task.setTaskDescription("Assignment Completed");

        // Assert
        assertEquals(1, volunteer.getTasksDone().size(), "Volunteer's tasksDone list should contain one task");
        assertEquals("Assignment Completed", volunteer.getTasksDone().get(0).getTaskDescription(), "Task description mismatch");
    }
}
