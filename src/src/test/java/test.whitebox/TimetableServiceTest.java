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
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry.");
        assertEquals("SMQ", entries.get(0).getSubject(), "Subject should match.");
        assertEquals("Daily", entries.get(0).getRecurrence(), "Recurrence should match.");
    }

    @Test
    void testUpdateTimetableEntry() {
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        timetableService.updateTimetableEntry("Monday", "10:00 AM", "Tuesday", "09:00 AM", "Agile", "Mr. Artur");
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should still have 1 entry.");
        Timetable updatedEntry = entries.get(0);
        assertEquals("Tuesday", updatedEntry.getDay(), "Day should be updated.");
        assertEquals("09:00 AM", updatedEntry.getTime(), "Time should be updated.");
    }

    @Test
    void testDeleteTimetableEntry() {
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        timetableService.addTimetableEntry("Tuesday", "09:00 AM", "Agile", "Mr. Artur", "Weekly");
        timetableService.deleteTimetableEntry("Mr. Chan", "10:00 AM");
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry after deletion.");
    }

    @Test
    void testDisplayTimetable() {
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        timetableService.addTimetableEntry("Tuesday", "10:00 AM", "Physics", "Ms. Johnson", "Weekly");
        timetableService.displayTimetable();
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(2, entries.size(), "Timetable should have 2 entries.");
    }

    @Test
    void testGetDailySummary() {
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        timetableService.addTimetableEntry("Tuesday", "10:00 AM", "Agile", "Mr. Artur","Weekly");
        List<Timetable> dailyEvents = timetableService.getRecurringEvents("Daily");
        assertEquals(1, dailyEvents.size(), "There should be 1 daily event.");
    }
//As a tester, I want to validate the recurring event logic with unit tests to ensure accuracy.
    @Test
    void testGetWeeklySummary() {
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        timetableService.addTimetableEntry("Tuesday", "10:00 AM", "Agile", "Mr. Artur","Weekly");
        List<Timetable> weeklyEvents = timetableService.getRecurringEvents("Weekly");
        assertEquals(1, weeklyEvents.size(), "There should be 1 weekly event.");
    }

    @Test
    void testInvalidRecurrence() {
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan", "Daily");
        List<Timetable> invalidEvents = timetableService.getRecurringEvents("Yearly");
        assertTrue(invalidEvents.isEmpty(), "There should be no events for an invalid recurrence type.");
    }
}
