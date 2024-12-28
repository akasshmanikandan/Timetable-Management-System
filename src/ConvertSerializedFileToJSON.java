package timetable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class ConvertSerializedFileToJSON {
    public static void main(String[] args) {
        String serializedFile = "timetable_data.ser"; //serialized file
        String jsonFile = "timetable_data.json";     // convert to JSON file

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedFile))) {
            // deserializes the data
            List<Timetable> timetableList = (List<Timetable>) ois.readObject();

            // convert to JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(timetableList);

            // save JSON file
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(json);
                System.out.println("JSON file created successfully: " + jsonFile);
            }

            //  print JSON to console
            System.out.println("Serialized data converted to JSON:");
            System.out.println(json);

        } catch (FileNotFoundException e) {
            System.err.println("Serialized file not found: " + serializedFile);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading serialized file: " + e.getMessage());
        }
    }
}
