package timetable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class ConvertSerializedFileToJSON {
    public static void main(String[] args) {
        // Serialized files and JSON output files
        String timetableSerializedFile = "timetable_data.ser";
        String timetableJSONFile = "timetable_data.json";
        String assignmentSerializedFile = "assignment_data.ser";
        String assignmentJSONFile = "assignment_data.json";

        // Convert timetable data
        convertSerializedToJSON(timetableSerializedFile, timetableJSONFile, "Timetable");

        // Convert assignment data
        convertSerializedToJSON(assignmentSerializedFile, assignmentJSONFile, "Assignment");
    }

    private static <T> void convertSerializedToJSON(String serializedFile, String jsonFile, String dataType) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedFile))) {
            // Deserialize the data
            List<T> dataList = (List<T>) ois.readObject();

            // Convert to JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dataList);

            // Save to JSON file
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(json);
                System.out.println(dataType + " JSON file created successfully: " + jsonFile);
            }

            // Print JSON to console
            System.out.println(dataType + " data converted to JSON:");
            System.out.println(json);

        } catch (FileNotFoundException e) {
            System.err.println(dataType + " serialized file not found: " + serializedFile);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading " + dataType + " serialized file: " + e.getMessage());
        }
    }
}
