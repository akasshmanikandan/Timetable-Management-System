package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.classmanagement.ClassDetails;
import timetable.classmanagement.ClassManagementService;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassManagementServiceTest {

    private ClassManagementService classService;
    private final String TEST_FILE = "test_class_data.ser"; //custom test file

    @BeforeEach
    void setUp() {
       
        classService = new ClassManagementService(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    void testAddAndRetrieveClass() {
        // add a new class
        classService.addClass("Monday", "SMQA", "09:00 AM", "Mr.Chan");

        //retrieve the class list
        List<ClassDetails> classList = classService.getClassList();

        //validates the data
        assertNotNull(classList, "Class list should not be null");
        assertEquals(1, classList.size(), "Class list should contain 1 entry");
        assertEquals("SMQA", classList.get(0).getSubject(), "Subject should be 'SMQA'");
        assertEquals("Monday", classList.get(0).getDay(), "Day should be 'Monday'");
        assertEquals("09:00 AM", classList.get(0).getTime(), "Time should be '09:00 AM'");
        assertEquals("Mr.Chan", classList.get(0).getInstructor(), "Instructor should be 'Mr.Chan'");
    }

    @Test
    void testDisplayEmptyClasses() {
        //ensures the class list is empty
        List<ClassDetails> classList = classService.getClassList();
        assertTrue(classList.isEmpty(), "Class list should be empty initially");

        //test displaying empty classes
        classService.displayClasses();
    }
    
    @Test
    void testAddDuplicateClass() {
        classService.addClass("Monday", "SMQA", "09:00 AM", "Mr.Chan");
        classService.addClass("Monday", "SMQA", "09:00 AM", "Mr.Chan");

        //validate both classes are added (if duplicates are allowed)
        List<ClassDetails> classList = classService.getClassList();
        assertEquals(2, classList.size(), "Class list should allow duplicate entries");
    }


    @Test
    void testLoadFromFile() {
        //add a class and save to the test file
        classService.addClass("Tuesday", "Agile", "10:00 AM", "Mr.Artur");

        ClassManagementService newClassService = new ClassManagementService(TEST_FILE);
        List<ClassDetails> loadedClassList = newClassService.getClassList();

        //validates loaded data
        assertNotNull(loadedClassList, "Loaded class list should not be null");
        assertEquals(1, loadedClassList.size(), "Loaded class list should contain 1 entry");
        assertEquals("Agile", loadedClassList.get(0).getSubject(), "Subject should be 'Agile'");
        assertEquals("Tuesday", loadedClassList.get(0).getDay(), "Day should be 'Tuesday'");
        assertEquals("10:00 AM", loadedClassList.get(0).getTime(), "Time should be '10:00 AM'");
        assertEquals("Mr.Artur", loadedClassList.get(0).getInstructor(), "Instructor should be 'Mr.Artur'");
    }
}
