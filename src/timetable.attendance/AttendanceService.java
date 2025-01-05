package timetable.attendance;

import timetable.FileHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceService {
    private List<Attendance> attendanceList; // List to hold attendance records
    private String fileName; // File name for serialized attendance data

    // Constructor to load attendance data from a file
    public AttendanceService(String fileName) {
        this.fileName = fileName;
        this.attendanceList = FileHandler.loadFromFile(fileName);
        if (attendanceList == null) {
            this.attendanceList = new ArrayList<>();
        }
        System.out.println("Attendance data loaded from: " + fileName);
    }

    // Method to mark attendance for a student
    public void markAttendance(String student, String date, boolean present) {
        Attendance attendance = new Attendance(student, date, present);
        attendanceList.add(attendance);
        FileHandler.saveToFile(attendanceList, fileName); // Save to file
        System.out.println("Attendance marked for: " + student + " on " + date);
    }

    // Method to fetch attendance records for a specific student
    public List<Attendance> fetchAttendanceByStudent(String student) {
        return attendanceList.stream()
            .filter(record -> record.getStudent().equalsIgnoreCase(student))
            .collect(Collectors.toList());
    }

    // Method to fetch attendance records for a specific date
    public List<Attendance> fetchAttendanceByDate(String date) {
        return attendanceList.stream()
            .filter(record -> record.getDate().equalsIgnoreCase(date))
            .collect(Collectors.toList());
    }

    // Method to generate attendance report for a student
    public void generateStudentReport(String student) {
        List<Attendance> studentRecords = fetchAttendanceByStudent(student);
        if (studentRecords.isEmpty()) {
            System.out.println("No attendance records found for student: " + student);
        } else {
            System.out.println("Attendance Report for " + student + ":");
            studentRecords.forEach(record -> {
                System.out.println("Date: " + record.getDate() + ", Present: " + record.isPresent());
            });
        }
    }

    // Method to generate attendance report for all students on a specific date
    public void generateDateReport(String date) {
        List<Attendance> dateRecords = fetchAttendanceByDate(date);
        if (dateRecords.isEmpty()) {
            System.out.println("No attendance records found for date: " + date);
        } else {
            System.out.println("Attendance Report for " + date + ":");
            dateRecords.forEach(record -> {
                System.out.println("Student: " + record.getStudent() + ", Present: " + record.isPresent());
            });
        }
    }
    public void generateClassReport(String date) {
        List<Attendance> records = fetchAttendanceByDate(date);
        if (records.isEmpty()) {
            System.out.println("No attendance records found for " + date);
        } else {
            System.out.println("Attendance Report for " + date + ":");
            records.forEach(record -> {
                System.out.println("Student: " + record.getStudent() + ", Present: " + record.isPresent());
            });
        }
    }

    // Method to display all attendance records
    public void displayAllAttendance() {
        if (attendanceList.isEmpty()) {
            System.out.println("No attendance records available.");
        } else {
            System.out.println("All Attendance Records:");
            attendanceList.forEach(System.out::println);
        }
    }
}
