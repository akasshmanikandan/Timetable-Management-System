package test.blackbox;

import org.junit.jupiter.api.Test;
import timetable.Notifications.ReminderService;
import timetable.Timetable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ReminderServiceTest {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Test
    void testReminderTriggeredCorrectly() {
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "10:00", "Math", "Mr. Smith", "Daily", 15));
        ReminderService reminderService = new ReminderService(timetableEntries);
        LocalTime currentTime = LocalTime.parse("09:45", TIME_FORMATTER);
        assertTrue(reminderService.isReminderTriggered(timetableEntries.get(0), currentTime),
                "Reminder should trigger at 09:45 for a 10:00 event.");
    }

    @Test
    void testReminderNotTriggeredForPastEvent() {
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "08:00", "History", "Ms. Johnson", "Daily", 30));
        //initialize reminderService
        ReminderService reminderService = new ReminderService(timetableEntries);


        LocalTime currentTime = LocalTime.parse("09:00", TIME_FORMATTER);
        assertFalse(reminderService.isReminderTriggered(timetableEntries.get(0), currentTime),
                "Reminder should not trigger for past events.");
    }

    @Test
    void testMultipleRemindersTriggered() {
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "11:00", "Physics", "Dr. Brown", "Weekly", 20));
        timetableEntries.add(new Timetable("Monday", "12:30", "Chemistry", "Dr. Green", "Weekly", 15));
        ReminderService reminderService = new ReminderService(timetableEntries);
        LocalTime currentTimeEvent1 = LocalTime.parse("10:40", TIME_FORMATTER);
        LocalTime currentTimeEvent2 = LocalTime.parse("12:15", TIME_FORMATTER);
        assertTrue(reminderService.isReminderTriggered(timetableEntries.get(0), currentTimeEvent1),
                "Reminder for Event 1 should trigger at 10:40.");
        assertTrue(reminderService.isReminderTriggered(timetableEntries.get(1), currentTimeEvent2),
                "Reminder for Event 2 should trigger at 12:15.");
    }


    @Test
    void testReminderNotTriggeredBeforeReminderTime() {
        // Mock timetable entries
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "10:00", "Math", "Mr. Smith", "Daily", 20));
        ReminderService reminderService = new ReminderService(timetableEntries);

        //simulate current time
        LocalTime currentTime = LocalTime.parse("09:29", TIME_FORMATTER);
        assertFalse(reminderService.isReminderTriggered(timetableEntries.get(0), currentTime),
                "Reminder should not trigger before reminder time.");
    }
    @Test
    void testReminderTriggeredAtExactEventTime() {
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "11:00", "Physics", "Dr. Brown", "Weekly", 20)); 
        ReminderService reminderService = new ReminderService(timetableEntries);
        LocalTime currentTime = LocalTime.parse("11:00",TIME_FORMATTER);
        assertFalse(reminderService.isReminderTriggered(timetableEntries.get(0), currentTime),
                "Reminder should trigger at the exact event time.");
    }

    @Test
    void testReminderNotTriggeredForFutureEvent() {
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "12:00", "Biology", "Dr. Green", "Weekly", 20));
        ReminderService reminderService = new ReminderService(timetableEntries);
        LocalTime currentTime = LocalTime.parse("11:30", TIME_FORMATTER);
        assertFalse(reminderService.isReminderTriggered(timetableEntries.get(0), currentTime),
                "Reminder should not trigger.");
    }
    @Test
    void testReminderWithNoEntries() {
        List<Timetable> emptyTimetable = new ArrayList<>();
        ReminderService reminderService = new ReminderService(emptyTimetable);
        //check that no reminders are triggered
        LocalTime currentTime = LocalTime.parse("09:45", TIME_FORMATTER);
        assertFalse(emptyTimetable.stream().anyMatch(entry -> 
            reminderService.isReminderTriggered(entry, currentTime)),
            "No reminders should be triggered when the timetable is empty.");
    }
    @Test
    void testReminderWithOverlappingEvents() {
        //timetable with overlapping events
        List<Timetable> timetableEntries = new ArrayList<>();
        timetableEntries.add(new Timetable("Monday", "09:00", "Math", "Mr. Smith", "Daily", 15));
        timetableEntries.add(new Timetable("Monday", "09:00", "Physics", "Dr. Brown", "Daily", 10));
        ReminderService reminderService = new ReminderService(timetableEntries);

        //simulates the the current time
        LocalTime currentTime = LocalTime.parse("08:50", TIME_FORMATTER); 
        assertTrue(reminderService.isReminderTriggered(timetableEntries.get(0), currentTime),
                "Reminder for Math should trigger at 08:50.");
        assertTrue(reminderService.isReminderTriggered(timetableEntries.get(1), currentTime),
                "Reminder for Physics should trigger at 08:50.");
    }
}

