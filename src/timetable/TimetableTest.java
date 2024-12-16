package timetable;

import java.time.LocalDateTime;

public class TimetableTest {
    public static void main(String[] args) {
        TimetableManager manager = new TimetableManager();

        TimetableEvent event1 = new TimetableEvent("Math Class", LocalDateTime.of(2024, 11, 18, 10, 0),
                LocalDateTime.of(2024, 11, 18, 11, 0), "Algebra review");
        TimetableEvent event2 = new TimetableEvent("Science Class", LocalDateTime.of(2024, 11, 18, 12, 0),
                LocalDateTime.of(2024, 11, 18, 13, 0), "Physics lecture");

        manager.addEvent(event1);
        manager.addEvent(event2);

        System.out.println("Timetable Events:");
        for (TimetableEvent event : manager.getEvents()) {
            System.out.println(event);
        }

        TimetableEvent updatedEvent = new TimetableEvent("Updated Math Class", LocalDateTime.of(2024, 11, 18, 10, 30),
                LocalDateTime.of(2024, 11, 18, 11, 30), "Updated Algebra review");
        manager.updateEvent(0, updatedEvent);

        manager.deleteEvent(1);

        System.out.println("\nUpdated Timetable Events:");
        for (TimetableEvent event : manager.getEvents()) {
            System.out.println(event);
        }
    }
}
