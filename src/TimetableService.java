package timetable;

import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    private List<Timetable> timetableList;

    // Constructor: Load timetable entries from file
    public TimetableService() {
        // Load from file or initialize an empty list
        this.timetableList = FileHandler.loadFromFile();
        if (timetableList == null) {
            this.timetableList = new ArrayList<>();
        }
    }

    // Add a new timetable entry
    public void addTimetableEntry(String day, String time, String subject, String teacher) {
        Timetable newEntry = new Timetable(day, time, subject, teacher);

        // validate for conflicts before adding
        if (isConflict(newEntry)) {
            System.out.println("Error: The new timetable entry conflicts with an existing entry.");
            return;
        }

        timetableList.add(newEntry);
        FileHandler.saveToFile(timetableList);
        System.out.println("Timetable entry added successfully.");
    }

    // Check for overlapping/conflicting timetable entries
    private boolean isConflict(Timetable newEntry) {
        for (Timetable existingEntry : timetableList) {
            if (existingEntry.getDay().equalsIgnoreCase(newEntry.getDay()) &&
                existingEntry.getTime().equals(newEntry.getTime())) {
                return true; // Conflict detected
            }
        }
        return false;
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
            FileHandler.saveToFile(timetableList);
            System.out.println("Timetable entry updated successfully.");
        } else {
            System.out.println("No matching timetable entry found to update.");
        }
    }
    // Deletes the entry 
    public void deleteTimetableEntry(String teacher, String time) {
        boolean removed = timetableList.removeIf(entry ->
                entry.getTeacher().equalsIgnoreCase(teacher) && entry.getTime().equals(time));

        if (removed) {
            FileHandler.saveToFile(timetableList);
            System.out.println("Timetable entry deleted successfully.");
        } else {
            System.out.println("No matching timetable entry found for the given day and time.");
        }
    }
    // Display all timetable entries
    public void displayTimetable() {
        if (timetableList.isEmpty()) {
            System.out.println("No timetable entries found.");
            return;
        }

        System.out.println("Timetable Entries:");
        for (Timetable entry : timetableList) {
            System.out.println(entry);
        }
    }

    // Get all timetable entries
    public List<Timetable> getTimetableEntries() {
        return timetableList;
    }
}
