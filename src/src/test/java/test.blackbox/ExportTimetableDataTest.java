package test.blackbox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Timetable;
import timetable.TimetableService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExportTimetableDataTest {

    private TimetableService timetableService;
    private final String csvFile = "test_timetable.csv";

    @BeforeEach
    void setUp() {
        timetableService = new TimetableService("export_timetable_data.ser");
        timetableService.addTimetableEntry("Monday", "09:00", "Math", "Mr. Smith", "Daily", 10);
        timetableService.addTimetableEntry("Tuesday", "10:00", "Science", "Ms. Johnson", "Daily", 10);
    }

    @AfterEach
    void tearDown() {
        File csv = new File(csvFile);
        if (csv.exists() && csv.delete()) {
            System.out.println("Deleted file: " + csvFile);
        } else {
            System.out.println("Failed to delete: " + csvFile);
        }
    }

    @Test
    void testExportToCSVFile() throws IOException {
        File csvFile = new File("test_output.csv");
        timetableService.exportToCSVFile(csvFile.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            assertEquals("Day,Time,Subject,Teacher", header, "Header should match.");
            assertEquals("Monday,09:00,Math,Mr. Smith", line1, "First row should match.");
            assertEquals("Tuesday,10:00,Science,Ms. Johnson", line2, "Second row should match.");
        } catch (IOException e) {
            fail("Failed to read the CSV file: " + e.getMessage());
        }
    }

    @Test
    void testExportEmptyTimetable() {
        timetableService = new TimetableService("empty_timetable.ser");
        File csvFile = new File("empty_timetable.csv");

        timetableService.exportToCSVFile(csvFile.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            String line = reader.readLine();

            assertEquals("Day,Time,Subject,Teacher", header, "Header should match.");
            assertNull(line, "There should be no entries in the CSV for an empty timetable.");
        } catch (IOException e) {
            fail("Failed to read the CSV file: " + e.getMessage());
        }
    }

    
}
