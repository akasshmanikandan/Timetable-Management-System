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
        File testFile = new File(CUSTOM_TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddAssignment() {
        //adds an new assignment
        assignmentService.addAssignment("SMQ", "Complete BY JAN 17", "2025-01-17", "High");
        //verifies wether assignment is added
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(1, assignments.size(), "There should be atleast 1 assignment.");
        assertEquals("SMQ", assignments.get(0).getTitle(), "name should match.");
    }

    @Test
    void testUpdateAssignment() { 
        assignmentService.addAssignment("Agile", "Finish by JAN 9", "2025-01-09", "Medium");
        //update assignment
        assignmentService.updateAssignment("Agile", "ServiceDesign", "Prepare a Presentation", "2025-01-13", "High");
        //verifying the update
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
        assignmentService.deleteAssignment("SOA");
        //verifying the deletion
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(1, assignments.size(), "There should be 1 assignment left.");
        assertEquals("GroupSkills", assignments.get(0).getTitle(), "Remaining assignment should be 'GroupSkills'.");
    }

    @Test
    void testDisplayAssignments() {
        //adds assignments
        assignmentService.addAssignment("SMQ", "Complete BY JAN 17", "2025-01-17", "High");
        assignmentService.addAssignment("Cybersecurity", "Prepare for exam", "2025-01-17", "Medium");
        assignmentService.displayAssignments();
        //verifies
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(2, assignments.size(), "There should be 2 assignments.");
    }
  
    @Test
    void testUpdateAssignmentPriority() {
        //adds an assignment
        assignmentService.addAssignment("Math", "Solve exercises", "2025-01-20", "Low");

        //updates the assignment's priority
        assignmentService.updateAssignment("Math", "Math", "Solve exercises", "2025-01-20", "High");
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals("High", assignments.get(0).getPriority(), "The priority should be updated to High.");
    }
    @Test
    void testAddDuplicateAssignments() {
        assignmentService.addAssignment("Project Report", "Complete final report", "2025-01-25", "High");
        assignmentService.addAssignment("Project Report", "Complete final report", "2025-01-25", "High");
        //verifying that both assignments are added
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(2, assignments.size(), "There should be 2 assignments.");
    }
    @Test
    void testAssignmentsSortedByTitle() {
        //adds multiple assignments with different titles
        assignmentService.addAssignment("Zoology", "Prepare notes", "2025-01-22", "Medium");
        assignmentService.addAssignment("Algebra", "Solve problems", "2025-01-18", "High");
        assignmentService.addAssignment("Geometry", "Complete worksheet", "2025-01-20", "Low");
        List<Assignment> sortedByTitle = assignmentService.getAssignmentsSortedByTitle();
        //verifying the sorting order
        assertEquals("Algebra", sortedByTitle.get(0).getTitle(), "First assignment should be 'Algebra'.");
        assertEquals("Geometry", sortedByTitle.get(1).getTitle(), "Second assignment should be 'Geometry'.");
        assertEquals("Zoology", sortedByTitle.get(2).getTitle(), "Third assignment should be 'Zoology'.");
    }

}
