package timetable.classmanagement;

import timetable.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class ClassManagementService {
    private List<ClassDetails> classList;
    private String storageFileName; // Custom file name for storing class details

    public ClassManagementService(String storageFileName) {
        this.storageFileName = storageFileName;
        this.classList = FileHandler.loadFromFile(storageFileName);
        if (classList == null) {
            this.classList = new ArrayList<>();
        }
        System.out.println("Class data loaded from: " + storageFileName + ". Current entries: " + classList.size());
    }

    // Add a new class
    public void addClass(String subject, String time, String instructor) {
        ClassDetails newClass = new ClassDetails(subject, time, instructor);
        classList.add(newClass);
        FileHandler.saveToFile(classList, storageFileName);
        System.out.println("Class added: " + newClass);
    }

    // Update an existing class
    public void updateClass(String oldSubject, String newSubject, String newTime, String newInstructor) {
        boolean found = false;

        for (ClassDetails classDetails : classList) {
            if (classDetails.getSubject().equalsIgnoreCase(oldSubject)) {
                classDetails.setSubject(newSubject);
                classDetails.setTime(newTime);
                classDetails.setInstructor(newInstructor);
                found = true;
                break;
            }
        }

        if (found) {
            FileHandler.saveToFile(classList, storageFileName);
            System.out.println("Class updated successfully.");
        } else {
            System.out.println("Error: No matching class found to update.");
        }
    }

    // Delete a class
    public void deleteClass(String subject) {
        boolean removed = classList.removeIf(classDetails -> classDetails.getSubject().equalsIgnoreCase(subject));

        if (removed) {
            FileHandler.saveToFile(classList, storageFileName);
            System.out.println("Class deleted successfully: " + subject);
        } else {
            System.out.println("Error: No matching class found to delete.");
        }
    }

    // Display all classes
    public void displayClasses() {
        if (classList.isEmpty()) {
            System.out.println("No class details found.");
            return;
        }

        System.out.println("\nClass Details:");
        for (ClassDetails classDetails : classList) {
            System.out.println(classDetails);
        }
    }

    // Get all classes
    public List<ClassDetails> getClassList() {
        return classList;
    }
}