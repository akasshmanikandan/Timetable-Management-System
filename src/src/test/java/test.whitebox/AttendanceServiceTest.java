package test.whitebox;

import timetable.attendance.Attendance;
import timetable.attendance.AttendanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceTest {

    private AttendanceService attendanceService;
    private final String TEST_FILE = "test_attendance_data.ser";

    @BeforeEach
    void setUp() {
        attendanceService = new AttendanceService(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        // Clean up the test file
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testMarkAttendance() {
        attendanceService.markAttendance("John Doe", "2025-01-05", true);

        List<Attendance> records = attendanceService.fetchAttendanceByStudent("John Doe");
        assertEquals(1, records.size(), "There should be 1 attendance record.");
        assertTrue(records.get(0).isPresent(), "John Doe should be marked as present.");
    }

    @Test
    void testFetchAttendanceByDate() {
        attendanceService.markAttendance("John Doe", "2025-01-05", true);
        attendanceService.markAttendance("Jane Doe", "2025-01-05", false);

        List<Attendance> records = attendanceService.fetchAttendanceByDate("2025-01-05");
        assertEquals(2, records.size(), "There should be 2 records for the date.");
    }

    @Test
    void testGenerateStudentReport() {
        attendanceService.markAttendance("John Doe", "2025-01-05", true);
        attendanceService.markAttendance("John Doe", "2025-01-06", false);

        System.out.println("Student Report Test:");
        attendanceService.generateStudentReport("John Doe");
    }
    @Test
    void testGenerateClassReport() {
        attendanceService.markAttendance("John Doe", "2025-01-05", true);
        attendanceService.markAttendance("Jane Doe", "2025-01-05", false);

        System.out.println("Class Report:");
        attendanceService.generateClassReport("2025-01-05");
    }

    @Test
    void testGenerateDateReport() {
        attendanceService.markAttendance("John Doe", "2025-01-05", true);
        attendanceService.markAttendance("Jane Doe", "2025-01-05", false);

        System.out.println("Date Report Test:");
        attendanceService.generateDateReport("2025-01-05");
    }

    @Test
    void testDisplayAllAttendance() {
        attendanceService.markAttendance("John Doe", "2025-01-05", true);
        attendanceService.markAttendance("Jane Doe", "2025-01-06", false);

        System.out.println("Display All Attendance Test:");
        attendanceService.displayAllAttendance();
    }
}
