package test.whitebox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.assignmet.Assignment;
import timetable.assignmet.AssignmentService;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentProgressTest {

    private AssignmentService assignmentService;
    private final String TEST_FILE = "test_assignment_data.ser"; 

    @BeforeEach
    void setUp() {
        
        assignmentService = new AssignmentService(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
       
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testCalculateProgressWithNoAssignments() {
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(0.0, progress, "Progress should be 0% without any assignments.");
    }

    @Test
    void testCalculateProgressWithZeroCompletion() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-15", "High");
        assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-10", "Medium");
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(0.0, progress, "Progress should be 0% when all assignments are at 0% completion.");
    }

    @Test
    void testCalculateProgressWithMixedCompletion() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-15", "High");
        assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-10", "Medium");
        List<Assignment> assignments = assignmentService.getAssignments();
        assignments.get(0).setCompletionPercentage(100);
        assignments.get(1).setCompletionPercentage(50);
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(75.0, progress, "Progress should be 75% with mixed completion (30% and 70%).");
    }
    
    @Test
    void testCalculateProgressWithSingleAssignment() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-15", "High");
        List<Assignment> assignments = assignmentService.getAssignments();
        assignments.get(0).setCompletionPercentage(60);
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(60.0, progress, "Progress should be 60% with a single assignment.");
    }

    @Test
    void testCalculateProgressWithFullCompletion() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-15", "High");
        assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-10", "Medium");
        List<Assignment> assignments = assignmentService.getAssignments();
        assignments.get(0).setCompletionPercentage(100);
        assignments.get(1).setCompletionPercentage(100);
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(100.0, progress, "Progress should be 100%.");
    }
    @Test
    void testCalculateProgressWithPartiallyCompletedAssignments() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-15", "High");
        assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-10", "Medium");
        assignmentService.addAssignment("Assignment 3", "Description 3", "2025-01-20", "Low");
        List<Assignment> assignments = assignmentService.getAssignments();
        assignments.get(0).setCompletionPercentage(30);
        assignments.get(1).setCompletionPercentage(70);
        assignments.get(2).setCompletionPercentage(50);
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(50.0, progress, "Progress should be 50% with partial completion (30%, 70%, 50%).");
    }
    @Test
    void testCalculateProgressWithLargeNumberOfAssignments() {
        for (int i = 1; i <= 1000; i++) {
            assignmentService.addAssignment("Assignment " + i, "Description " + i, "2025-01-15", "Medium");
        }

        List<Assignment> assignments = assignmentService.getAssignments();
        assignments.forEach(assignment -> assignment.setCompletionPercentage(50));
        double progress = assignmentService.calculateOverallProgress();
        assertEquals(50.0, progress, "Progress should be 50% with 1000 assignments at 50% completion.");
    }
}
