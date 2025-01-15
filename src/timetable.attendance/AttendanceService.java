package timetable.Attendance;

import timetable.FileHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceService {
    private List<attendance> attendanceList;
    private String storageFileName;
    public AttendanceService(String storageFileName) {
        this.storageFileName = storageFileName;
        this.attendanceList = FileHandler.loadFromFile(storageFileName);
        if (attendanceList == null) {
            this.attendanceList = new ArrayList<>();
        }
        System.out.println("Attendance data loaded from: " + storageFileName + ". Current records: " + attendanceList.size());
    }

    //add a new attendance record
    public void addAttendance(String studentName, String className, String date, boolean isPresent) {
        attendance newAttendance = new attendance(studentName, className, date, isPresent);
        attendanceList.add(newAttendance);
        FileHandler.saveToFile(attendanceList, storageFileName);
        System.out.println("Attendance added: " + newAttendance);
    }

    //update an existing attendance record
    public void updateAttendance(String studentName, String className, String date, boolean isPresent) {
        boolean found = false;

        for (attendance record : attendanceList) {
            if (record.getStudentName().equalsIgnoreCase(studentName) &&
                record.getClassName().equalsIgnoreCase(className) &&
                record.getDate().equals(date)) {
                record.setPresent(isPresent);
                found = true;
                break;
            }
        }
        if (found) {
            FileHandler.saveToFile(attendanceList, storageFileName);
            System.out.println("Attendance updated for: " + studentName + " in class " + className);
        } else {
            System.out.println("Error: No matching attendance record found to update.");
        }
    }
    
    //method to mark attendance
    public void markAttendance(String studentName, String className, String date, boolean isPresent) {
        attendance record = new attendance(studentName, className, date, isPresent);
        attendanceList.add(record);
        FileHandler.saveToFile(attendanceList, storageFileName); // Save updated list
        System.out.println("Attendance marked for student: " + studentName + " in class: " + className);
    }
    public double calculateAttendancePercentage(String studentName) {
        List<attendance> studentRecords = getAttendanceByStudent(studentName);
        if (studentRecords.isEmpty()) {
            return 0.0; // No attendance records, return 0%
        }

        long presentCount = studentRecords.stream()
        .filter(attendance::isPresent)
        .count();
        return (double) presentCount / studentRecords.size() * 100;
    }
   

    //fetch attendance report by student
    public List<attendance> getAttendanceByStudent(String studentName) {
        return attendanceList.stream()
                .filter(record -> record.getStudentName().equalsIgnoreCase(studentName))
                .collect(Collectors.toList());
    }

    //fetch attendance report by class
    public List<attendance> getAttendanceByClass(String className) {
        return attendanceList.stream()
                .filter(record -> record.getClassName().equalsIgnoreCase(className))
                .collect(Collectors.toList());
    }

    //display filtered attendance records
    public void displayFilteredAttendance(List<attendance> filteredRecords) {
        if (filteredRecords.isEmpty()) {
            System.out.println("No matching attendance records found.");
        } else {
            System.out.println("\nFiltered Attendance Records:");
            for (attendance record : filteredRecords) {
                System.out.println(record);
            }
        }
    }


    //display all attendance records
    public void displayAttendance() {
        if (attendanceList.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }

        System.out.println("\nAttendance Records:");
        for (attendance record : attendanceList) {
            System.out.println(record);
        }
    }

    //get all attendance records
    public List<attendance> getAttendanceList() {
        return attendanceList;
    }
}
