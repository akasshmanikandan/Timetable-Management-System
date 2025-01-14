package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.FileHandler;
import timetable.Timetable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
        timetableList.add(new Timetable("Monday", "11:00 AM", "ServiceDesign", "DR. Genovefa","None", 12));
        timetableList.add(new Timetable("Tuesday", "10:00 AM", "SOA", "Mr.Reiko","None",15));

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
        initialList.add(new Timetable("Monday", "11:00 AM", "ServiceDesign", "DR. Genovefa","None", 15));

        //saves initial list to the test file
        FileHandler.saveToFile(initialList, TEST_FILE);

        //verifying initial save
        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertEquals(initialList.size(), loadedList.size(), "Initial save should have one entry.");

        //overwrite with a new timetable list
        List<Timetable> newList = new ArrayList<>();
        newList.add(new Timetable("Tuesday", "10:00 AM", "SOA", "Mr.Reiko","None",13));
        FileHandler.saveToFile(newList, TEST_FILE);

        //verifys overwrite
        loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertEquals(newList.size(), loadedList.size(), "Overwrite save should have one entry.");
        assertEquals("SOA", loadedList.get(0).getSubject(), "Loaded subject should match new save.");
    }

    @Test
    void testSaveAndLoadWithSpecialCharacters() {
        List<Timetable> specialCharList = new ArrayList<>();
        specialCharList.add(new Timetable("Monday", "11:00 AM", "Math&Science", "Dr. Smith@Uni", "None", 10));
        FileHandler.saveToFile(specialCharList, TEST_FILE);

        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertNotNull(loadedList, "Loaded list should not be null.");
        assertEquals(1, loadedList.size(), "Loaded list should contain one entry.");
        assertEquals("Math&Science", loadedList.get(0).getSubject(), "Subject should match special characters.");
        assertEquals("Dr. Smith@Uni", loadedList.get(0).getTeacher(), "Teacher name should match special characters.");
    }
    @Test
    void testSaveAndLoadLargeDataset() {
        List<Timetable> largeList = new ArrayList<>();

        // Add 10,000 entries to the list
        for (int i = 0; i < 10000; i++) {
            largeList.add(new Timetable("Day" + i, "10:00 AM", "Subject" + i, "Teacher" + i, "Daily", i));
        }

        // Save and load the large dataset
        FileHandler.saveToFile(largeList, TEST_FILE);
        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);

        // Validate the loaded data
        assertNotNull(loadedList, "Loaded list should not be null.");
        assertEquals(largeList.size(), loadedList.size(), "Loaded list size should match the saved list size.");
        assertEquals(largeList.get(9999).getSubject(), loadedList.get(9999).getSubject(),
                "The last entry in the loaded list should match the last entry in the saved list.");
    }
    @Test
    void testLoadFromCorruptedFile() {
        // Create a corrupted file
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("This is not a serialized object.");
        } catch (IOException e) {
            fail("Failed to create corrupted file for test.");
        }

        // Attempt to load from corrupted file
        List<Timetable> loadedList = FileHandler.loadFromFile(TEST_FILE);
        assertNotNull(loadedList, "Loaded list should not be null.");
        assertTrue(loadedList.isEmpty(), "Loaded list should be empty for corrupted file.");
    }
    @Test
    void testPartialDataRetrievalFromCorruptedFile() {
        // Create a file with mixed valid and corrupted data
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_FILE))) {
            oos.writeObject(new Timetable("Monday", "11:00 AM", "Math", "Mr. Smith", "None", 15)); // Valid entry
            oos.writeBytes("Corrupted data"); // Corrupted entry
            oos.writeObject(new Timetable("Tuesday", "01:00 PM", "Science", "Dr. Brown", "Weekly", 10)); // Valid entry
        } catch (IOException e) {
            fail("Failed to create a test file with mixed data.");
        }
    }
}
