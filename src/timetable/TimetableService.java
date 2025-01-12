package timetable;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableService {
    private List<Timetable> timetableList;
    private String fileName;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TimetableService() {
        this("timetable_data.ser");
    }

    public TimetableService(String fileName) {
        this.fileName = fileName;
        this.timetableList = FileHandler.loadFromFile(fileName);
        if (timetableList == null) {
            this.timetableList = new ArrayList<>();
        }
        System.out.println("Timetable data loaded from: " + fileName + ". Current entries: " + timetableList.size());
    }

    // Add a new timetable entry
    public void addTimetableEntry(String day, String time, String subject, String teacher, String recurrence, int reminderOffset) {
        try {
           
            String trimmedTime = time.trim();
            
            LocalTime.parse(trimmedTime, TIME_FORMATTER);

            // Add the new timetable entry
            Timetable newEntry = new Timetable(day, trimmedTime, subject, teacher, recurrence, reminderOffset);
            timetableList.add(newEntry);
            FileHandler.saveToFile(timetableList, fileName);
            System.out.println("Timetable entry added: " + newEntry);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid time format: " + time + ". Expected format: HH:mm (24-Format)");
        }
    }

    // Add recurring events
    public void addRecurringEvent(String day, String time, String subject, String teacher, String recurrence, int reminderOffset) {
        List<String> days = generateRecurringDays(day, recurrence);
        for (String recurringDay : days) {
            Timetable recurringEntry = new Timetable(recurringDay, time, subject, teacher, recurrence, reminderOffset);
            timetableList.add(recurringEntry);
        }
        FileHandler.saveToFile(timetableList, fileName);
        System.out.println("Recurring timetable entries added for: " + recurrence);
    }

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
                    recurringDays.add("Month+" + i + " " + day);
                }
                break;
            default:
                System.out.println("Invalid recurrence type. Supported types: daily, weekly, monthly.");
        }
        return recurringDays;
    }

    private String getNextDay(String currentDay, int offset) {
        return currentDay + " (+" + offset + " days)";
    }

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

    public List<Timetable> getRecurringEvents(String recurrence) {
        return timetableList.stream()
                .filter(entry -> entry.getRecurrence().equalsIgnoreCase(recurrence))
                .collect(Collectors.toList());
    }

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
            FileHandler.saveToFile(timetableList, fileName);
            System.out.println("Timetable entry updated successfully: " + newDay + " " + newTime);
        } else {
            System.out.println("Error: No matching timetable entry found to update.");
        }
    }

    public void deleteTimetableEntry(String teacher, String time) {
        boolean removed = timetableList.removeIf(entry -> entry.getTeacher().equalsIgnoreCase(teacher) && entry.getTime().equals(time));

        if (removed) {
            FileHandler.saveToFile(timetableList, fileName);
            System.out.println("Timetable entry deleted successfully for the teacher: " + teacher + " at: " + time);
        } else {
            System.out.println("Error: No matching timetable entry found for teacher: " + teacher + " at: " + time);
        }
    }

    public List<Timetable> getDailySummary(String day) {
        return timetableList.stream()
                .filter(entry -> entry.getDay().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    public List<Timetable> getWeeklySummary() {
        return new ArrayList<>(timetableList);
    }

    public List<Timetable> getMonthlySummary() {
        return new ArrayList<>(timetableList);
    }

    public void displaySummary(List<Timetable> summary) {
        if (summary == null || summary.isEmpty()) {
            System.out.println("No timetable entries found for the specified period.");
        } else {
            System.out.println("\nTimetable Summary:");
            for (Timetable entry : summary) {
                System.out.println("Day: " + entry.getDay() +
                        ", Time: " + LocalTime.parse(entry.getTime(), TIME_FORMATTER).format(TIME_FORMATTER) +
                        ", Subject: " + entry.getSubject() +
                        ", Teacher: " + entry.getTeacher());
            }
        }
    }


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

    public void displayTimetable() {
        if (timetableList.isEmpty()) {
            System.out.println("No timetable entries found.");
            return;
        }

        System.out.println("\nTimetable Entries:");
        for (Timetable entry : timetableList) {
            System.out.println("Day: " + entry.getDay() +
                    ", Time: " + LocalTime.parse(entry.getTime(), TIME_FORMATTER).format(TIME_FORMATTER) +
                    ", Subject: " + entry.getSubject() +
                    ", Teacher: " + entry.getTeacher() +
                    ", Recurrence: " + entry.getRecurrence());
        }
    }

    public List<Timetable> getTimetableEntries() {
        return timetableList;
    }

    // Calculate total utilized time in minutes
    public long calculateUtilizedTime() {
        long totalMinutes = 0;

        for (Timetable entry : timetableList) {
            try {
                String trimmedTime = entry.getTime().trim();
                LocalTime startTime = LocalTime.parse(trimmedTime, TIME_FORMATTER);
                LocalTime endTime = startTime.plusHours(1); // Assuming each event lasts 1 hour
                totalMinutes += Duration.between(startTime, endTime).toMinutes();
            } catch (DateTimeParseException e) {
                System.err.println("Invalid time format for entry: " + entry.getTime() + " - " + e.getMessage());
            }
        }

        return totalMinutes;
    }

    // Calculate daily utilization percentage
    public double calculateDailyUtilization() {
        long utilizedMinutes = calculateUtilizedTime();
        long totalAvailableMinutes = 24 * 60; // 24 hours in a day
        return (double) utilizedMinutes / totalAvailableMinutes * 100;
    }

    // Calculate weekly utilization percentage
    public double calculateWeeklyUtilization() {
        long utilizedMinutes = calculateUtilizedTime();
        long totalAvailableMinutes = 7 * 24 * 60; // 7 days in a week
        return (double) utilizedMinutes / totalAvailableMinutes * 100;
    }

    // Display time utilization report
    public void displayTimeUtilizationReport() {
        System.out.println("\n=== || Time Utilization Report || ===");
        System.out.printf("Total Utilized Time: %d minutes\n", calculateUtilizedTime());
        System.out.printf("Daily Utilization: %.2f%%\n", calculateDailyUtilization());
        System.out.printf("Weekly Utilization: %.2f%%\n", calculateWeeklyUtilization());
    }
}
