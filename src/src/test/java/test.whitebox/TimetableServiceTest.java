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
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily",10);
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry.");
        assertEquals("SMQ", entries.get(0).getSubject(), "Subject should match.");
        assertEquals("Daily", entries.get(0).getRecurrence(), "Recurrence should match.");
    }

    @Test
    void testUpdateTimetableEntry() {
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily",10);
        timetableService.updateTimetableEntry("Monday", "10:00", "Tuesday", "09:00", "Agile", "Mr. Artur");
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should still have 1 entry.");
        Timetable updatedEntry = entries.get(0);
        assertEquals("Tuesday", updatedEntry.getDay(), "Day should be updated.");
        assertEquals("09:00", updatedEntry.getTime(), "Time should be updated.");
    }

    @Test
    void testDeleteTimetableEntry() {
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily",10);
        timetableService.addTimetableEntry("Tuesday", "09:00", "Agile", "Mr. Artur", "Weekly",12);
        timetableService.deleteTimetableEntry("Mr. Chan", "10:00");
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry after deletion.");
    }

    @Test
    void testDisplayTimetable() {
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily",10);
        timetableService.addTimetableEntry("Tuesday", "10:00", "Physics", "Ms. Johnson", "Weekly",15);
        timetableService.displayTimetable();
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(2, entries.size(), "Timetable should have 2 entries.");
    }

    @Test
    void testGetDailySummary() {
        // Add entries with proper recurrence
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily", 10);
        timetableService.addTimetableEntry("Tuesday", "11:00", "Physics", "Ms. Smith", "Weekly", 15);

        // Debugging: Print all stored entries
        System.out.println("Stored Timetable Entries:");
        timetableService.getTimetableEntries().forEach(System.out::println);

        // Fetch daily events
        List<Timetable> dailyEvents = timetableService.getRecurringEvents("Daily");
        assertEquals(1, dailyEvents.size(), "There should be 1 daily event.");
        assertEquals("SMQ", dailyEvents.get(0).getSubject(), "The subject of the daily event should match.");
    }

//As a tester, I want to validate the recurring event logic with unit tests to ensure accuracy.
    @Test
    void testGetWeeklySummary() {
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily",10);
        timetableService.addTimetableEntry("Tuesday", "10:00", "Agile", "Mr. Artur","Weekly",12);
        List<Timetable> weeklyEvents = timetableService.getRecurringEvents("Weekly");
        assertEquals(1, weeklyEvents.size(), "There should be 1 weekly event.");
    }

    @Test
    void testInvalidRecurrence() {
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily",10);
        List<Timetable> invalidEvents = timetableService.getRecurringEvents("Yearly");
        assertTrue(invalidEvents.isEmpty(), "There should be no events for an invalid recurrence type.");
    }

@Test
void testAddInvalidTimetableEntry() {
    assertThrows(IllegalArgumentException.class, () -> {
        timetableService.addTimetableEntry("Monday", "10:00", "", "Mr. Chan", "Daily", 10);
    }, "Adding an entry with an empty subject should throw an exception.");

    assertThrows(IllegalArgumentException.class, () -> {
        timetableService.addTimetableEntry("Monday", "10:00", "SMQ", "Mr. Chan", "Daily", -5);
    }, "Adding an entry with a negative reminder offset should throw an exception.");
}
}

