package test.whiteboxbox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.FileHandler;
import timetable.Timetable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        // initialize the service with the custom test file
        timetableService = new TimetableService(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        // delete the custom test file once after each testcase
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddTimetableEntry() {
        // add new timetable entry
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan");

        // verify entry is added
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "timetable should have 1 entry.");
        assertEquals("SMQ", entries.get(0).getSubject(), "Subject should match.");
    }

    @Test
    void testUpdateTimetableEntry() {
        // add an entry to update
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan");

        // update the entry
        timetableService.updateTimetableEntry("Monday", "10:00 AM", "Tuesday", "09:00 AM", "Agile", "Mr. Artur");

        // verify the update
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should still have 1 entry.");
        assertEquals("Tuesday", entries.get(0).getDay(), "Day should be updated.");
        assertEquals("09:00 AM", entries.get(0).getTime(), "Time should be updated.");
        assertEquals("Agile", entries.get(0).getSubject(), "Subject should be updated.");
        assertEquals("Mr. Artur", entries.get(0).getTeacher(), "Teacher should be updated.");
    }

    @Test
    void testDeleteTimetableEntry() {
        // add entries
        timetableService.addTimetableEntry("Monday", "10:00 AM", "SMQ", "Mr. Chan");
        timetableService.addTimetableEntry("Tuesday", "09:00 AM", "Agile", "Mr. Artur");

        // delete one entry
        timetableService.deleteTimetableEntry("Mr. Chan", "10:00 AM");

        // verify the deletion
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(1, entries.size(), "Timetable should have 1 entry after deletion.");
        assertEquals("Agile", entries.get(0).getSubject(), "Remaining entry should be 'Agile'.");
    }

    @Test
    void testDisplayTimetable() {
        // add entries
        timetableService.addTimetableEntry("Monday", "09:00 AM", "Math", "Mr. Smith");
        timetableService.addTimetableEntry("Tuesday", "09:00 AM", "Agile", "Mr. Artur");

        // display timetable
        timetableService.displayTimetable();

        // verify entries
        List<Timetable> entries = timetableService.getTimetableEntries();
        assertEquals(2, entries.size(), "Timetable should have 2 entries.");
    }
}

