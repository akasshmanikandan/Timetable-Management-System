package test.whitebox;

import timetable.assignmet.Assignment;
import timetable.assignmet.AssignmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentServiceTest {

    private AssignmentService assignmentService;
    private final String CUSTOM_TEST_FILE = "C:\\Users\\sId nAgA\\git\\Timetable-Management-System\\custom_test_assignment_data.ser"; 

    @BeforeEach
    void setUp() {
        assignmentService = new AssignmentService(CUSTOM_TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        // deletes the custom file after each test
        File testFile = new File(CUSTOM_TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddAssignment() {
        // adds an new assignment
        assignmentService.addAssignment("SMQ", "Complete BY JAN 17", "2025-01-17", "High");

        // verifies wether assignment is added
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(1, assignments.size(), "There should be atleast 1 assignment.");
        assertEquals("SMQ", assignments.get(0).getTitle(), "name should match.");
    }

    @Test
    void testUpdateAssignment() {
        
        assignmentService.addAssignment("Agile", "Finish by JAN 9", "2025-01-09", "Medium");

        // update assignment
        assignmentService.updateAssignment("Agile", "ServiceDesign", "Prepare a Presentation", "2025-01-13", "High");

        // verify the update
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(1, assignments.size(), "There should still be 1 assignment.");
        Assignment updated = assignments.get(0);
        assertEquals("ServiceDesign", updated.getTitle(), "Updated name should match.");
        assertEquals("Prepare a Presentation", updated.getDescription(), "Updated description should match.");
    }

    @Test
    void testDeleteAssignment() {
      
        assignmentService.addAssignment("SOA", "Make a metamodel", "2025-01-12", "Low");
        assignmentService.addAssignment("GroupSkills", "Make PPT", "2025-01-08", "High");

        // delete one assignment
        assignmentService.deleteAssignment("SOA");

        // verify the deletion
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(1, assignments.size(), "There should be 1 assignment left.");
        assertEquals("GroupSkills", assignments.get(0).getTitle(), "Remaining assignment should be 'GroupSkills'.");
    }

    @Test
    void testDisplayAssignments() {
        // add assignments
        assignmentService.addAssignment("SMQ", "Complete BY JAN 17", "2025-01-17", "High");
        assignmentService.addAssignment("Cybersecurity", "Prepare for exam", "2025-01-17", "Medium");

        //shows remaining assignments
        assignmentService.displayAssignments();

        // verifies
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(2, assignments.size(), "There should be 2 assignments.");
        
        
        
    }
    
    @Test
    void testChangePassword() {
        authService.register("user1", "password1");
        assertTrue(authService.changePassword("user1", "password1", "newpassword"), "Password change should succeed.");
        assertFalse(authService.login("user1", "password1"), "Old password should not work.");
        assertTrue(authService.login("user1", "newpassword"), "New password should work.");
    }

    @Test
    void testResetPassword() {
        authService.register("user2", "password2");
        assertTrue(authService.resetPassword("user2", "newpassword2"), "Password reset should succeed.");
        assertFalse(authService.login("user2", "password2"), "Old password should not work.");
        assertTrue(authService.login("user2", "newpassword2"), "New password should work.");
    }
    
    @Test
    void testCalculateProgress() {
        assignmentService.addAssignment("SMQA", "Complete the report", "2025-01-17", "High", 0);
        assignmentService.calculateProgress("SMQA", 5, 10);

        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(50, assignments.get(0).getProgress(), "Progress should be 50%.");
    }

    @Test
    void testMarkAsComplete() {
        assignmentService.addAssignment("SMQA", "Complete the report", "2025-01-17", "High", 0);
        assignmentService.markAsComplete("SMQA");

        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(100, assignments.get(0).getProgress(), "Progress should be 100%.");
    }

    @Test
    void testSortByPriority() {
        assignmentService.addAssignment("Assignment1", "Task 1", "2025-01-17", "High", 0);
        assignmentService.addAssignment("Assignment2", "Task 2", "2025-01-15", "Low", 0);
        assignmentService.addAssignment("Assignment3", "Task 3", "2025-01-10", "Medium", 0);

        List<Assignment> sortedByPriority = assignmentService.sortByPriority();
        assertEquals("Low", sortedByPriority.get(0).getPriority(), "First priority should be 'Low'.");
        assertEquals("Medium", sortedByPriority.get(1).getPriority(), "Second priority should be 'Medium'.");
        assertEquals("High", sortedByPriority.get(2).getPriority(), "Third priority should be 'High'.");
    }

    @Test
    void testSortByDeadline() {
        assignmentService.addAssignment("Assignment1", "Task 1", "2025-01-17", "High", 0);
        assignmentService.addAssignment("Assignment2", "Task 2", "2025-01-15", "Low", 0);
        assignmentService.addAssignment("Assignment3", "Task 3", "2025-01-10", "Medium", 0);

        List<Assignment> sortedByDeadline = assignmentService.sortByDeadline();
        assertEquals("2025-01-10", sortedByDeadline.get(0).getDeadline(), "First deadline should be '2025-01-10'.");
        assertEquals("2025-01-15", sortedByDeadline.get(1).getDeadline(), "Second deadline should be '2025-01-15'.");
        assertEquals("2025-01-17", sortedByDeadline.get(2).getDeadline(), "Third deadline should be '2025-01-17'.");
    }

    
    
}

