package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import timetable.TimetableService;
import timetable.assignmet.Assignment;
import timetable.assignmet.AssignmentService;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsTest {

    private static final String TEST_FILE = "test_assignments.ser";
    private AssignmentService assignmentService = new AssignmentService(TEST_FILE);

    @AfterEach
    void tearDown1() {
        // Delete the test file after each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            if (testFile.delete()) {
                System.out.println("Test file deleted: " + TEST_FILE);
            } else {
                System.err.println("Failed to delete test file: " + TEST_FILE);
            }
        }
    }
    private static final String TEST_TIMETABLE_FILE = "test_timetable.ser";
    private TimetableService timetableService = new TimetableService(TEST_TIMETABLE_FILE);

    @AfterEach
    void tearDown() {
        // Delete the test timetable file after each test
        File testFile = new File(TEST_TIMETABLE_FILE);
        if (testFile.exists()) {
            if (testFile.delete()) {
                System.out.println("Test file deleted: " + TEST_TIMETABLE_FILE);
            } else {
                System.err.println("Failed to delete test file: " + TEST_TIMETABLE_FILE);
            }
        }
    }

	 @Test
	    void testOverdueAssignments() {
	       
	        // Add test data
	        assignmentService.addAssignment("Project Report", "Final report submission", "2025-01-05", "High");
	        assignmentService.addAssignment("Lab Work", "Physics lab experiment", "2025-01-03", "Low");
	        assignmentService.addAssignment("Essay", "History essay", "2025-01-20", "Medium");

	        // Validate overdue assignments (assuming today's date is 2025-01-06)
	        List<Assignment> overdue = assignmentService.getOverdueAssignments();
	        assertEquals(2, overdue.size(), "There should be 2 overdue assignments.");
	        assertTrue(overdue.stream().anyMatch(a -> a.getTitle().equals("Project Report")));
	        assertTrue(overdue.stream().anyMatch(a -> a.getTitle().equals("Lab Work")));
	    }
	

    @Test
    void testAssignmentProgress() {
        // Sample data
        AssignmentService assignmentService = new AssignmentService("test_assignments.ser");
        assignmentService.addAssignment("Project Report", "Final report submission", "2025-01-05", "High");
        assignmentService.updateAssignmentProgress("Project Report", 80);

        // Validate progress
        List<Assignment> assignments = assignmentService.getAssignments();
        assertEquals(80, assignments.get(0).getCompletionPercentage(), "Completion percentage for 'Project Report' should be 80%.");
    }

    @Test
    void testSortingByDeadline() {
        // Sample data
        AssignmentService assignmentService = new AssignmentService("test_assignments.ser");
        assignmentService.addAssignment("Essay", "History essay", "2025-01-20", "Medium");
        assignmentService.addAssignment("Lab Work", "Physics lab experiment", "2025-01-03", "Low");

        // Validate sorting
        List<Assignment> sorted = assignmentService.getAssignmentsSortedByDeadline();
        assertEquals("Lab Work", sorted.get(0).getTitle(), "First sorted assignment should be 'Lab Work' by deadline.");
        assertEquals("Essay", sorted.get(1).getTitle(), "Second sorted assignment should be 'Essay' by deadline.");
    }

    @Test
    void testTimeUtilization() {
        // Sample data
        
        timetableService.addTimetableEntry("Monday", "09:00", "Math", "Mr. Smith", "Daily", 15);
        timetableService.addTimetableEntry("Tuesday", "10:00", "Physics", "Dr. Brown", "Weekly", 30);

        // Validate time utilization
        long totalMinutes = timetableService.calculateUtilizedTime();
        assertEquals(120, totalMinutes, "Total utilized time should be 120 minutes.");
    }

@Test
void testHighPriorityAssignments() {
    // Add sample data
    assignmentService.addAssignment("Project Report", "Final report submission", "2025-01-15", "High");
    assignmentService.addAssignment("Lab Work", "Physics lab experiment", "2025-01-10", "Low");
    assignmentService.addAssignment("Presentation", "Project presentation", "2025-01-12", "High");

    // Filter high-priority assignments
    List<Assignment> highPriorityAssignments = assignmentService.getAssignmentsSortedByPriority().stream()
            .filter(a -> a.getPriority().equalsIgnoreCase("High"))
            .toList();

    assertEquals(2, highPriorityAssignments.size(), "There should be 2 high-priority assignments.");
    assertTrue(highPriorityAssignments.stream().anyMatch(a -> a.getTitle().equals("Project Report")));
    assertTrue(highPriorityAssignments.stream().anyMatch(a -> a.getTitle().equals("Presentation")));
}
@Test
void testWeeklyUtilizationPercentage() {
    // Sample data
    timetableService.addTimetableEntry("Monday", "09:00", "Math", "Mr. Smith", "Daily", 15);
    timetableService.addTimetableEntry("Wednesday", "11:00", "Science", "Ms. Johnson", "Weekly", 20);

    // Validate weekly utilization
    double utilizationPercentage = timetableService.calculateWeeklyUtilization();
    assertTrue(utilizationPercentage > 0, "Weekly utilization percentage should be greater than 0.");
    System.out.printf("Weekly Utilization: %.2f%%\n", utilizationPercentage);
}
@Test
void testNoOverdueAssignments() {
    // Add assignments with future deadlines
    assignmentService.addAssignment("Assignment 1", "Description 1", "2025-01-20", "Medium");
    assignmentService.addAssignment("Assignment 2", "Description 2", "2025-01-25", "High");

    // Validate no overdue assignments
    List<Assignment> overdue = assignmentService.getOverdueAssignments();
    assertTrue(overdue.isEmpty(), "There should be no overdue assignments.");
}
@Test
void testTimeUtilizationMultipleDays() {
    // Add timetable entries for different days
    timetableService.addTimetableEntry("Monday", "09:00", "Math", "Mr. Smith", "Daily", 60);
    timetableService.addTimetableEntry("Tuesday", "10:30", "Science", "Ms. Johnson", "Weekly", 90);

    // Validate total utilized time
    long totalMinutes = timetableService.calculateUtilizedTime();
    assertEquals(120, totalMinutes, "Total utilized time should be 120 minutes.");
}
}
