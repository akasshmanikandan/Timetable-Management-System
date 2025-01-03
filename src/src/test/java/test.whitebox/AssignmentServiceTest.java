package test.whiteboxbox;

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
}
