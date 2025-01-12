package timetable.Notifications;

import timetable.Timetable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderService {
    private final List<Timetable> timetableEntries;
    private final Timer timer;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public ReminderService(List<Timetable> timetableEntries) {
        this.timetableEntries = new ArrayList<>(timetableEntries); // Create a copy of the list
        this.timer = new Timer(true); 
    }

    // Start the scheduler
    public void startScheduler() {
        System.out.println("Reminder scheduler started.");
        for (Timetable entry : timetableEntries) {
            scheduleReminder(entry);
        }
    }

    // Stop the scheduler
    public void stopScheduler() {
        timer.cancel();
        System.out.println("Reminder scheduler stopped.");
    }

    // Schedule a reminder for a specific timetable entry
    private void scheduleReminder(Timetable entry) {
        try {
            LocalTime eventTime = LocalTime.parse(entry.getTime(), timeFormatter);
            LocalTime reminderTime = eventTime.minusMinutes(entry.getReminderOffset());

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Reminder: Upcoming event - " + entry);
                }
            };

            long delay = calculateDelay(reminderTime);
            if (delay > 0) {
                timer.schedule(task, delay);
                System.out.println("Reminder scheduled for: " + entry + " (Reminder set " + entry.getReminderOffset() + " minutes before)");
            } else {
                System.out.println("Skipping past reminder for: " + entry);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid time format for entry: " + entry.getTime());
        }
    }
    
    public boolean isReminderTriggered(Timetable entry, LocalTime currentTime) {
        try {
            // Parse the event time
            LocalTime eventTime = LocalTime.parse(entry.getTime(),timeFormatter);
            // Calculate the reminder trigger time
            LocalTime reminderTime = eventTime.minusMinutes(entry.getReminderOffset());
            // Check if the current time matches the reminder time
            return !currentTime.isBefore(reminderTime) && currentTime.isBefore(eventTime);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid time format for entry: " + entry.getTime());
            return false;
        }
    }

    // Display all scheduled reminders
    public void displayScheduledReminders() {
        System.out.println("\n=== || Scheduled Reminders || ===");
        if (timetableEntries.isEmpty()) {
            System.out.println("No reminders scheduled.");
        } else {
            for (Timetable entry : timetableEntries) {
                System.out.println("Event: " + entry.getSubject() + " at " + entry.getTime() + " (" + entry.getDay() + ")");
            }
        }
    }

    //calculate the delay in milliseconds until the event time
    private long calculateDelay(LocalTime eventTime) {
        long delay = java.time.Duration.between(LocalTime.now(), eventTime).toMillis();
        if (delay < 0) { 
            delay += 24 * 60 * 60 * 1000; 
        }
        return delay;
    }

    //add a new timetable entry for reminders
    public void addTimetableEntry(Timetable entry) {
        timetableEntries.add(entry);
        scheduleReminder(entry); 
        System.out.println("New timetable entry added and reminder scheduled: " + entry);
    }

    public void removeTimetableEntry(Timetable entry) {
        timetableEntries.remove(entry);
        System.out.println("Timetable entry removed: " + entry);
    }
}
