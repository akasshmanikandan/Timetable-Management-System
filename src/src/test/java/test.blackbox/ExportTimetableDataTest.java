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

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExportTimetableDataTest {

    private TimetableService timetableService;
    private final String csvFile = "test_timetable.csv";

    @BeforeEach
    void setUp() {
        timetableService = new TimetableService("export_timetable_data.ser");
        timetableService.addTimetableEntry("Monday", "09:00 AM", "Math", "Mr. Smith");
        timetableService.addTimetableEntry("Tuesday", "10:00 AM", "Science", "Ms. Johnson");
    }

    @AfterEach
    void tearDown() {
        // Delete the test files after each test
        File csv = new File(csvFile);


        if (csv.exists() && csv.delete()) {
            System.out.println("Deleted file: " + csvFile);
        } else {
            System.out.println("Failed to delete: " + csvFile);
        }
    }

    @Test
    void testExportToCSVFile() throws IOException {
        timetableService.exportToCSVFile(csvFile);

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            String line1 = reader.readLine();
            String line2 = reader.readLine();

            assertEquals("Day,Time,Subject,Teacher", header, "Header should match.");
            assertEquals("Monday,09:00 AM,Math,Mr. Smith", line1, "First row should match.");
            assertEquals("Tuesday,10:00 AM,Science,Ms. Johnson", line2, "Second row should match.");
        }
    }
}
