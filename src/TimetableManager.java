package timetable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableManager {
    private static final String FILE_PATH = "C:\\Users\\sId nAgA\\git\\Timetable-Management-System\\Timetable-Management-System\\resources";
 
    private List<TimetableEvent> events;

    public TimetableManager() {
        events = loadEvents();
    }
   
    

   
    public void addEvent(TimetableEvent event) {
        events.add(event);
        saveEvents();
    }

    public List<TimetableEvent> getEvents() {
        return new ArrayList<>(events);
    }

    public void updateEvent(int index, TimetableEvent updatedEvent) {
        if (index >= 0 && index < events.size()) {
            events.set(index, updatedEvent);
            saveEvents();
        }
    }

    public void deleteEvent(int index) {
        if (index >= 0 && index < events.size()) {
            events.remove(index);
            saveEvents();
        }
    }

    private void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(events);
        } catch (IOException e) {
            System.err.println("Error saving events: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<TimetableEvent> loadEvents() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<TimetableEvent>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading events: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
