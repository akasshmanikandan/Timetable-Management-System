package test.whitebox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Timetable;
import timetable.TimetableService;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimetableServiceTest {

    private TimetableService timetableService;
    private final String TEST_FILE = "test_timetable_data.ser"; // Custom test file

    @BeforeEach
    void setUp() {
        // Initialize the service with the custom test file
        timetableService = new TimetableService(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        // Delete the custom test file after each test case
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            boolean deleted = testFile.delete();
            if (!deleted) {
                System.err.println("Warning: Failed to delete test file: " + TEST_FILE);
            }
        }
    }

    @Test
    void testAddTimetableEntry() {
        // Add a new timetable entry
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan");

        // Verify the entry is added
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry.");
        assertEquals("SMQ", entries.get(0).getSubject(), "Subject should match.");
        assertEquals("Mr. Chan", entries.get(0).getTeacher(), "Teacher should match.");
    }

    @Test
    void testUpdateTimetableEntry() {
        // Add an entry to update
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan");

        // Update the entry
        timetableService.updateTimetableEntry("Monday", "10:00 AM", "Tuesday", "09:00 AM", "Agile", "Mr. Artur");

        // Verify the update
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should still have 1 entry.");
        Timetable updatedEntry = entries.get(0);
        assertEquals("Tuesday", updatedEntry.getDay(), "Day should be updated.");
        assertEquals("09:00 AM", updatedEntry.getTime(), "Time should be updated.");
        assertEquals("Agile", updatedEntry.getSubject(), "Subject should be updated.");
        assertEquals("Mr. Artur", updatedEntry.getTeacher(), "Teacher should be updated.");
    }

    @Test
    void testDeleteTimetableEntry() {
        // Add entries
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan");
        timetableService.addTimetableEntry("Tuesday", "09:00 AM", "Agile", "Mr. Artur");

        // Delete one entry
        timetableService.deleteTimetableEntry("Mr. Chan", "10:00 AM");

        // Verify the deletion
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry after deletion.");
        assertEquals("Agile", entries.get(0).getSubject(), "Remaining entry should be 'Agile'.");
        assertEquals("Mr. Artur", entries.get(0).getTeacher(), "Remaining teacher should be 'Mr. Artur'.");
    }


    @Test
    void testDisplayTimetable() {
        // Add entries
        timetableService.addTimetableEntry("Monday", "09:00 AM", "SMQ", "Mr. Chan");
        timetableService.addTimetableEntry("Tuesday", "09:00 AM", "Agile", "Mr. Artur");

        // Display timetable
        timetableService.displayTimetable();

        // Verify entries
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(2, entries.size(), "Timetable should have 2 entries.");
        assertEquals("SMQ", entries.get(0).getSubject(), "First entry should match.");
        assertEquals("Agile", entries.get(1).getSubject(), "Second entry should match.");
    }
    
    @Test
    void testAddRecurringEvent() {
        timetableService.addRecurringEvent("Monday", "09:00 AM", "SMQA", "Mr. Chan", "daily");

        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "One recurring event should be added.");
        assertTrue(entries.get(0).isRecurring(), "Event should be marked as recurring.");
        assertEquals("daily", entries.get(0).getRecurrencePattern(), "Recurrence pattern should match.");
    }

    @Test
    void testProcessRecurringEvents() {
        timetableService.addRecurringEvent("Monday", "09:00 AM", "SMQA", "Mr. Chan", "daily");
        timetableService.processRecurringEvents();

        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(5, entries.size(), "Five events should be added for daily recurrence.");
    }

}
