package timetable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableService {
    private List<Timetable> timetableList;
    private String fileName; //custom file 

    
    public TimetableService() {
        this("timetable_data.ser");
    }

    // Constructor for custom file (e.g., for testing)
    public TimetableService(String fileName) {
        this.fileName = fileName;
        this.timetableList = FileHandler.loadFromFile(fileName); 
        if (timetableList == null) {
            this.timetableList = new ArrayList<>();
        }
        System.out.println("Timetable data loaded from: " + fileName + ". Current entries: " + timetableList.size());
    }

    // add a new timetable entry
    public void addTimetableEntry(String day, String time, String subject, String teacher, String recurrence) {
        Timetable newEntry = new Timetable(day, time, subject, teacher, recurrence);
        timetableList.add(newEntry);
        FileHandler.saveToFile(timetableList, fileName); // Save updated list to file
        System.out.println("Timetable entry added: " + newEntry);
    }

    // Add a recurring timetable entry
    public void addRecurringEvent(String day, String time, String subject, String teacher, String recurrence) {
        List<String> days = generateRecurringDays(day, recurrence);
        for (String recurringDay : days) {
            Timetable recurringEntry = new Timetable(day, time, subject, teacher,recurrence);
            timetableList.add(recurringEntry);
        }
        FileHandler.saveToFile(timetableList, fileName);
        System.out.println("Recurring timetable entries added for: " + recurrence);
    }

    // Generate recurring days based on recurrence type
    private List<String> generateRecurringDays(String day, String recurrence) {
        List<String> recurringDays = new ArrayList<>();
        recurringDays.add(day);

        switch (recurrence.toLowerCase()) {
            case "daily":
                for (int i = 1; i <= 6; i++) {
                    recurringDays.add(getNextDay(day, i));
                }
                break;
            case "weekly":
                for (int i = 1; i <= 3; i++) {
                    recurringDays.add(getNextDay(day, i * 7));
                }
                break;
            case "monthly":
                for (int i = 1; i <= 2; i++) {
                    recurringDays.add("Month+" + i + " " + day); // Placeholder logic for monthly recurrence
                }
                break;
            default:
                System.out.println("Invalid recurrence type. Supported types: daily, weekly, monthly.");
        }
        return recurringDays;
    }

    // Placeholder for calculating the next day (to be enhanced with proper date handling)
    private String getNextDay(String currentDay, int offset) {
        return currentDay + " (+" + offset + " days)";
    }
 // Display recurring events based on recurrence type
    public void displayRecurringEvents(String recurrence) {
        List<Timetable> recurringEvents = getRecurringEvents(recurrence);
        if (recurringEvents.isEmpty()) {
            System.out.println("No recurring events found for recurrence type: " + recurrence);
        } else {
            System.out.println("\nRecurring Events (" + recurrence + "):");
            for (Timetable entry : recurringEvents) {
                System.out.println(entry);
            }
        }
    }
 // Get recurring events by recurrence type
    public List<Timetable> getRecurringEvents(String recurrence) {
        return timetableList.stream()
                .filter(entry -> entry.getRecurrence().equalsIgnoreCase(recurrence))
                .collect(Collectors.toList());
    }


    //update an existing timetable entry
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

    //deletes  timetable entry
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

    //get all timetable entries
    public List<Timetable> getTimetableEntries() {
        return timetableList;
    }
}
