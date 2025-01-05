package timetable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // Generic method to save data to a file
    public static <T> void saveToFile(List<T> dataList, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            System.out.println("Saving data to: " + new File(fileName).getAbsolutePath());
            oos.writeObject(dataList);
            System.out.println("Data saved successfully to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Generic method to load data from a file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            System.out.println("Loading data from: " + new File(fileName).getAbsolutePath());
            return (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName + ". Returning an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
