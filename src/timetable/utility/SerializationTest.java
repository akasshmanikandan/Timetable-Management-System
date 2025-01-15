package utility; // Or use 'timetable' if you prefer

import timetable.FileHandler;
import timetable.Timetable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
    public static void main(String[] args) {
        String fileName = "optimized_timetable_data.ser";

        // Create a large dataset
        List<Timetable> largeDataset = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            largeDataset.add(new Timetable("Day " + (i % 7), String.format("%02d:00", i % 24), 
                    "Subject " + i, "Teacher " + i, "Weekly", 15));
        }

        // Measure write time
        long startWriteTime = System.nanoTime();
        FileHandler.saveToFile(largeDataset, fileName);
        long endWriteTime = System.nanoTime();

        System.out.println("Write Time: " + (endWriteTime - startWriteTime) / 1_000_000 + " ms");

        // Measure file size
        File file = new File(fileName);
        System.out.println("Serialized File Size: " + file.length() + " bytes");

        // Measure read time
        long startReadTime = System.nanoTime();
        List<Timetable> loadedDataset = FileHandler.loadFromFile(fileName);
        long endReadTime = System.nanoTime();

        System.out.println("Read Time: " + (endReadTime - startReadTime) / 1_000_000 + " ms");

        // Verify data integrity
        boolean isDataEqual = largeDataset.equals(loadedDataset);
        System.out.println("Data Integrity Check: " + (isDataEqual ? "PASSED" : "FAILED"));
    }
}
