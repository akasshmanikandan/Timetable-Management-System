package timetable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;

public class ConvertSerializedFileToJSON {

    public static void main(String[] args) {
        //serialized files and corresponding JSON output files
        String timetableSerializedFile = "timetable_data.ser";
        String timetableJSONFile = "timetable_data.json";

        String assignmentSerializedFile = "assignment_data.ser";
        String assignmentJSONFile = "assignment_data.json";

        String classManagementSerializedFile = "class_data.ser";
        String classManagementJSONFile = "class_data.json";

        String attendanceSerializedFile = "attendance_data.ser";
        String attendanceJSONFile = "attendance_data.json";

        String authenticationSerializedFile = "auth_data.ser";
        String authenticationJSONFile = "authentication_data.json";

        //converts serialized files to JSON
        convertSerializedToJSON(timetableSerializedFile, timetableJSONFile, "Timetable");
        convertSerializedToJSON(assignmentSerializedFile, assignmentJSONFile, "Assignment");
        convertSerializedToJSON(classManagementSerializedFile, classManagementJSONFile, "ClassManagement");
        convertSerializedToJSON(attendanceSerializedFile, attendanceJSONFile, "Attendance");
        convertSerializedToJSON(authenticationSerializedFile, authenticationJSONFile, "Authentication");
    }

    private static <T> void convertSerializedToJSON(String serializedFile, String jsonFile, String dataType) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedFile))) {
            //deserialize the data
            List<T> dataList = (List<T>) ois.readObject();

            //convert to JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(dataList);

            //save to JSON file
            try (FileWriter writer = new FileWriter(jsonFile)) {
                writer.write(json);
                System.out.println(dataType + " JSON file created successfully: " + jsonFile);
            }

            //print JSON to console
            System.out.println(dataType + " data converted to JSON:");
            System.out.println(json);

        } catch (FileNotFoundException e) {
            System.err.println(dataType + " serialized file not found: " + serializedFile);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading " + dataType + " serialized file: " + e.getMessage());
        }
    }
}
