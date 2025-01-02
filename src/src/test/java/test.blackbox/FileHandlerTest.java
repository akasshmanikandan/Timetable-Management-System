package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.FileHandler;
import timetable.Timetable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private final String TEST_FILE = "test_timetable_data.ser";

    @BeforeEach
    void setUp() {
        //cleans test file before each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    void tearDown() {
        //cleans the test file after each test
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testSaveAndLoadTimetable() {
        // creating a sample timetable list
        List<Timetable> timetableList = new ArrayList<>();
        timetableList.add(new Timetable("Monday", "11:00 AM", "ServiceDesign", "DR. Genovefa"));
        timetableList.add(new Timetable("Tuesday", "10:00 AM", "SOA", "Mr.Reiko"));

        //save to and load from the test file
        FileHandler.saveToFile(timetableList, TEST_FILE);
        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);

        //validates that the loaded data match with saved data
        assertNotNull(loadedList, "Loaded timetable list should'nt null.");
        assertEquals(timetableList.size(), loadedList.size(), "Loaded data should match with saved data.");
        assertEquals(timetableList, loadedList, "Loaded list should match the saved list.");
    }

    @Test
    void testLoadFromNonexistentFile() {
        // load from a nonexistent file
        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);

        // validates that an empty list is returned
        assertNotNull(loadedList, "Loaded list should not be null.");
        assertTrue(loadedList.isEmpty(), "Loaded list should be empty when file does not exist.");
    }

    @Test
    void testSaveEmptyList() {
        List<Timetable> emptyList = new ArrayList<>();
        FileHandler.saveToFile(emptyList, TEST_FILE);

        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertNotNull(loadedList, "Loaded list should not be null.");
        assertTrue(loadedList.isEmpty(), "Loaded list should be empty.");
    }

    @Test
    void testFileNotFound() {
        File nonExistentFile = new File("non_existent_file.ser");
        List<Timetable> loadedList = FileHandler.loadFromFile(nonExistentFile.getAbsolutePath());
        assertNotNull(loadedList, "Loaded list should not be null.");
        assertTrue(loadedList.isEmpty(), "Loaded list should be empty for a non-existent file.");
    }

    @Test
    void testOverwriteFile() {
        //create an initial timetable entries
        List<Timetable> initialList = new ArrayList<>();
        initialList.add(new Timetable("Monday", "11:00 AM", "ServiceDesign", "DR. Genovefa"));

        //saves initial list to the test file
        FileHandler.saveToFile(initialList, TEST_FILE);

        //verifying initial save
        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertEquals(initialList.size(), loadedList.size(), "Initial save should have one entry.");

        //overwrite with a new timetable list
        List<Timetable> newList = new ArrayList<>();
        newList.add(new Timetable("Tuesday", "10:00 AM", "SOA", "Mr.Reiko"));
        FileHandler.saveToFile(newList, TEST_FILE);

        //verifys overwrite
        loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertEquals(newList.size(), loadedList.size(), "Overwrite save should have one entry.");
        assertEquals("SOA", loadedList.get(0).getSubject(), "Loaded subject should match new save.");
    }
}

