package timetable.classmanagement;

import timetable.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassManagementService {
    private List<ClassDetails> classList;
    private String storageFileName; // Custom file name for class details

    public ClassManagementService(String storageFileName) {
        this.storageFileName = storageFileName;
        this.classList = FileHandler.loadFromFile(storageFileName);
        if (classList == null) {
            this.classList = new ArrayList<>();
        }
        System.out.println("Class data loaded from: " + storageFileName + ". Current entries: " + classList.size());
    }

    //add a new class
    public void addClass(String day, String subject, String time, String instructor) {
        ClassDetails newClass = new ClassDetails(day, subject, time, instructor);
        classList.add(newClass);
        FileHandler.saveToFile(classList, storageFileName);
        System.out.println("Class added: " + newClass);
    }

    //update an existing class
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

    //delete a class
    public void deleteClass(String subject) {
        boolean removed = classList.removeIf(classDetails -> classDetails.getSubject().equalsIgnoreCase(subject));

        if (removed) {
            FileHandler.saveToFile(classList, storageFileName);
            System.out.println("Class deleted successfully: " + subject);
        } else {
            System.out.println("Error: No matching class found to delete.");
        }
    }

    //shows all classes
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

    //get all classes
    public List<ClassDetails> getClassList() {
        return classList;
    }

    //fetch classes by subject
    public List<ClassDetails> getClassesBySubject(String subject) {
        return classList.stream()
                .filter(classDetails -> classDetails.getSubject().equalsIgnoreCase(subject))
                .collect(Collectors.toList());
    }

    //fetch classes by day
    public List<ClassDetails> getClassesByDay(String day) {
        return classList.stream()
                .filter(classDetails -> classDetails.getDay().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    //display filtered classes
    public void displayFilteredClasses(List<ClassDetails> filteredClasses) {
        if (filteredClasses.isEmpty()) {
            System.out.println("No matching classes found.");
        } else {
            System.out.println("\nFiltered Classes:");
            for (ClassDetails classDetails : filteredClasses) {
                System.out.println(classDetails);
            }
        }
    }
}
