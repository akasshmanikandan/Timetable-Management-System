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
    void testCalculateProgressWithFullCompletion() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-15", "High");
        assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-10", "Medium");

        List<Assignment> assignments = assignmentService.getAssignments();
        assignments.get(0).setCompletionPercentage(100);
        assignments.get(1).setCompletionPercentage(100);

        double progress = assignmentService.calculateOverallProgress();
        assertEquals(100.0, progress, "Progress should be 100% when all assignments are fully completed.");
    }
}
