package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.assignmet.Assignment;
import timetable.assignmet.AssignmentService;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortingAssignments {
    private AssignmentService assignmentService;
    private final String TEST_FILE = "test_assignment_data.ser";

    @BeforeEach
    void setUp() {
        assignmentService = new AssignmentService(TEST_FILE);
        assignmentService.deleteAssignment("All"); // Clear existing data for a clean test.
    }
    @AfterEach
    void tearDown() {
        // Delete the test data file after each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            boolean deleted = testFile.delete();
            if (deleted) {
                System.out.println("Test file deleted: " + TEST_FILE);
            } else {
                System.err.println("Failed to delete test file: " + TEST_FILE);
            }
        }
    }
    @Test
    void testSortingByPriority() {
        assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-17", "Low");
        assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-08", "High");
        assignmentService.addAssignment("Assignment 3", "Description 3", "2025-01-12", "Medium");

        List<Assignment> sortedByPriority = assignmentService.getAssignmentsSortedByPriority();
        assertEquals("High", sortedByPriority.get(0).getPriority(), "First assignment should have High priority.");
        assertEquals("Medium", sortedByPriority.get(1).getPriority(), "Second assignment should have Medium priority.");
        assertEquals("Low", sortedByPriority.get(2).getPriority(), "Third assignment should have Low priority.");
    }

    @Test
    void testSortingByDeadline() {
        assignmentService.addAssignment("Assignment1", "Description1", "2025-01-17", "Low");
        assignmentService.addAssignment("Assignment2", "Description2", "2025-01-08", "High");
        assignmentService.addAssignment("Assignment3", "Description3", "2025-01-12", "Medium");

        List<Assignment> sortedByDeadline = assignmentService.getAssignmentsSortedByDeadline();
        assertEquals("2025-01-08", sortedByDeadline.get(0).getDeadline(), "First assignment should have the earliest deadline.");
        assertEquals("2025-01-12", sortedByDeadline.get(1).getDeadline(), "Second assignment should have the second earliest deadline.");
        assertEquals("2025-01-17", sortedByDeadline.get(2).getDeadline(), "Third assignment should have the latest deadline.");
    }
}
