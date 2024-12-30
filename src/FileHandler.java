package timetable;

import java.io.*;
import java.util.List;

public class FileHandler {

    private static final String FILE_NAME = "C:\\Users\\Public\\Timetable-Management-System\\timetable_data.ser.";

    // Save the timetable list to a file
    public static void saveToFile(List<Timetable> timetableList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(timetableList);
            System.out.println("Timetable data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving timetable data: " + e.getMessage());
        }
    }

    // Load the timetable list from a file
    @SuppressWarnings("unchecked")
    public static List<Timetable> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Timetable>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Returning an empty list.");
            return new java.util.ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading timetable data: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
}
