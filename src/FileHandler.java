package timetable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import timetable.assignmet.Assignment;

public class FileHandler {

    // Default file name for production data
    private static final String DEFAULT_FILE_NAME = "timetable_data.ser";
    
    //Save timetable data to the default file (production use).
    public static void saveToFile(List<Timetable> timetableList) {
        saveToFile(timetableList, DEFAULT_FILE_NAME);
    }

    

   //Save timetable data to the specified file.
    public static void saveToFile(List<Timetable> timetableList, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            System.out.println("Saving data to: " + new File(fileName).getAbsolutePath());
            oos.writeObject(timetableList);
            System.out.println("Timetable data saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving timetable data: " + e.getMessage());
        }
    }
    public static void saveAssignmentsToFile(List<Assignment> assignmentList, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(assignmentList);
            System.out.println("Assignment data saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving assignment data: " + e.getMessage());
        }
    }
    //Load timetable data from the default file (production use).
    @SuppressWarnings("unchecked")
    public static List<Timetable> loadFromFile() {
        return loadFromFile(DEFAULT_FILE_NAME);
    }

    //Load timetable data from the specified file.
    @SuppressWarnings("unchecked")
    public static List<Timetable> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Loading data from: " + new File(fileName).getAbsolutePath());
            return (List<Timetable>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName + ". Returning an empty timetable list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading timetable data from " + fileName + ": " + e.getMessage());
        }
        return new ArrayList<>();
    }
    @SuppressWarnings("unchecked")
    public static List<Assignment> loadAssignmentsFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Assignment>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved assignment data found. Returning an empty list.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading assignment data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

   
}
