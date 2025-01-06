package timetable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableService {
    private List<Timetable> timetableList;
    private String fileName; // Custom file name for storing timetable data

    // Default constructor uses "timetable_data.ser" for production
    public TimetableService() {
        this("timetable_data.ser"); // Default to production file
    }

    // Constructor for custom file (e.g., for testing)
    public TimetableService(String fileName) {
        this.fileName = fileName;
        this.timetableList = FileHandler.loadFromFile(fileName); // Correct method to load data
        if (timetableList == null) {
            this.timetableList = new ArrayList<>();
        }
        System.out.println("Timetable data loaded from: " + fileName + ". Current entries: " + timetableList.size());
    }

    // Add a new timetable entry
    public void addTimetableEntry(String day, String time, String subject, String teacher) {
        Timetable newEntry = new Timetable(day, time, subject, teacher);
        timetableList.add(newEntry);
        FileHandler.saveToFile(timetableList, fileName); // Save updated list to file
        System.out.println("Timetable entry added: " + newEntry);
    }

    // Update an existing timetable entry
    public void updateTimetableEntry(String day, String time, String newDay, String newTime, String newSubject, String newTeacher) {
        boolean found = false;

        for (Timetable entry : timetableList) {
            if (entry.getDay().equalsIgnoreCase(day) && entry.getTime().equals(time)) {
                entry.setDay(newDay);
                entry.setTime(newTime);
                entry.setSubject(newSubject);
                entry.setTeacher(newTeacher);
                found = true;
                break;
            }
        }

        if (found) {
            FileHandler.saveToFile(timetableList, fileName); // Save updated list to file
            System.out.println("Timetable entry updated successfully: " + newDay + " " + newTime);
        } else {
            System.out.println("Error: No matching timetable entry found to update.");
        }
    }

    // Delete a timetable entry
    public void deleteTimetableEntry(String teacher, String time) {
        boolean removed = timetableList.removeIf(entry -> {
            System.out.println("Checking entry: " + entry); // Debug log
            return entry.getTeacher().equalsIgnoreCase(teacher) && entry.getTime().equals(time);
        });

        if (removed) {
            FileHandler.saveToFile(timetableList, fileName); // Save updated list to file
            System.out.println("Timetable entry deleted successfully for the teacher: " + teacher + " at: " + time);
        } else {
            System.out.println("Error: No matching timetable entry found for teacher: " + teacher + " at: " + time);
        }
    }
    
    //generates a daily summary
    public List<Timetable> getDailySummary(String day) {
        return timetableList.stream()
                .filter(entry -> entry.getDay().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    //weekly summary
    public List<Timetable> getWeeklySummary() {
        return new ArrayList<>(timetableList); 
    }

    //monthly summary
    public List<Timetable> getMonthlySummary() {
      
        return new ArrayList<>(timetableList);
    }
    public void displaySummary(List<Timetable> summary) {
        if (summary == null || summary.isEmpty()) {
            System.out.println("No timetable entries found for the specified period.");
        } else {
            System.out.println("\nTimetable Summary:");
            for (Timetable entry : summary) {
                System.out.println(entry);
            }
        }
    }
    //export data to text file
    public void exportToTextFile(String exportFileName) {
        try (FileWriter writer = new FileWriter(exportFileName)) {
            for (Timetable entry : timetableList) {
                writer.write(entry.toString() + "\n");
            }
            System.out.println("Timetable data exported successfully to " + exportFileName);
        } catch (IOException e) {
            System.err.println("Error exporting to text file: " + e.getMessage());
        }
    }

    //export to a CSV file
    public void exportToCSVFile(String exportFileName) {
        try (FileWriter writer = new FileWriter(exportFileName)) {
            // Write CSV headers
            writer.write("Day,Time,Subject,Teacher\n");
            for (Timetable entry : timetableList) {
                writer.write(entry.getDay() + "," + entry.getTime() + "," + entry.getSubject() + "," + entry.getTeacher() + "\n");
            }
            System.out.println("Timetable data exported successfully to " + exportFileName);
        } catch (IOException e) {
            System.err.println("Error exporting to CSV file: " + e.getMessage());
        }
    }

    //display all timetable entries
    public void displayTimetable() {
        if (timetableList.isEmpty()) {
            System.out.println("No timetable entries found.");
            return;
        }

        System.out.println("\nTimetable Entries:");
        for (Timetable entry : timetableList) {
            System.out.println(entry);
        }
    }

    // Get all timetable entries
    public List<Timetable> getTimetableEntries() {
        return timetableList;
    }
}
