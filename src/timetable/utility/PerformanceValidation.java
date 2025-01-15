package utility;

import timetable.FileHandler;
import timetable.Timetable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PerformanceValidation {

    private static final String ORIGINAL_FILE = "large_dataset.ser";
    private static final String OPTIMIZED_FILE = "optimized_timetable_data.ser";

    public static void main(String[] args) {
        generateTestData(10000); // Generate a dataset with 10,000 entries

        long serializationTime = measureSerializationTime();
        long deserializationTime = measureDeserializationTime();

        compareFileSizes();
        validateDataIntegrity();

        generatePerformanceReport(List.of(1000, 5000, 10000, 20000));

        System.out.printf("Serialization Time: %d ms%n", serializationTime);
        System.out.printf("Deserialization Time: %d ms%n", deserializationTime);
    }

    // Generate test data
    private static void generateTestData(int size) {
        List<Timetable> largeDataset = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            largeDataset.add(new Timetable("Day" + (i % 7), "08:00", "Subject" + i, "Teacher" + i, "Weekly", 10));
        }
        FileHandler.saveToFile(largeDataset, ORIGINAL_FILE);
        System.out.println("Test data generated with " + size + " entries.");
    }

    // Measure serialization time
    private static long measureSerializationTime() {
        List<Timetable> data = FileHandler.loadFromFile(ORIGINAL_FILE);
        long startTime = System.currentTimeMillis();
        FileHandler.saveToFile(data, OPTIMIZED_FILE);
        return System.currentTimeMillis() - startTime;
    }

    // Measure deserialization time
    private static long measureDeserializationTime() {
        long startTime = System.currentTimeMillis();
        FileHandler.loadFromFile(OPTIMIZED_FILE);
        return System.currentTimeMillis() - startTime;
    }

    // Compare file sizes
    private static void compareFileSizes() {
        File originalFile = new File(ORIGINAL_FILE);
        File optimizedFile = new File(OPTIMIZED_FILE);

        System.out.printf("Original File Size: %d bytes%n", originalFile.length());
        System.out.printf("Optimized File Size: %d bytes%n", optimizedFile.length());
    }

    // Validate data integrity
    private static void validateDataIntegrity() {
        List<Timetable> originalData = FileHandler.loadFromFile(ORIGINAL_FILE);
        List<Timetable> optimizedData = FileHandler.loadFromFile(OPTIMIZED_FILE);

        if (originalData.equals(optimizedData)) {
            System.out.println("Data integrity validated successfully!");
        } else {
            System.err.println("Data integrity validation failed!");
        }
    }

    // Generate a performance report
    private static void generatePerformanceReport(List<Integer> datasetSizes) {
        System.out.println("\n=== Performance Report ===");
        for (int size : datasetSizes) {
            List<Timetable> dataset = generateDataset(size);

            long serializationTime = measureSerializationTimeForDataset(dataset, "temp_" + size + ".ser");
            long deserializationTime = measureDeserializationTimeForDataset("temp_" + size + ".ser");

            System.out.printf("Dataset Size: %d | Serialization: %d ms | Deserialization: %d ms%n",
                    size, serializationTime, deserializationTime);
        }
    }

    private static List<Timetable> generateDataset(int size) {
        List<Timetable> dataset = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataset.add(new Timetable("Day" + (i % 7), "09:00", "Subject" + i, "Teacher" + i, "Weekly", 15));
        }
        return dataset;
    }

    private static long measureSerializationTimeForDataset(List<Timetable> dataset, String fileName) {
        long startTime = System.currentTimeMillis();
        FileHandler.saveToFile(dataset, fileName);
        return System.currentTimeMillis() - startTime;
    }

    private static long measureDeserializationTimeForDataset(String fileName) {
        long startTime = System.currentTimeMillis();
        FileHandler.loadFromFile(fileName);
        return System.currentTimeMillis() - startTime;
    }
}
