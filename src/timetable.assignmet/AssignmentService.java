package timetable.assignmet;

import timetable.FileHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AssignmentService {
    private List<Assignment> assignmentList;
    private String storageFileName; // File name for storing assignment data

    public AssignmentService(String storageFileName) {
        this.storageFileName = storageFileName;
        this.assignmentList = FileHandler.loadFromFile(storageFileName); // Generic load method
        if (assignmentList == null) {
            this.assignmentList = new ArrayList<>();
        }
        System.out.println("Assignment data loaded from: " + storageFileName + ". Current assignments: " + assignmentList.size());
    }

    // Add a new assignment
    public void addAssignment(String title, String description, String deadline, String priority) {
        Assignment newAssignment = new Assignment(title, description, deadline, priority);
        assignmentList.add(newAssignment);
        FileHandler.saveToFile(assignmentList, storageFileName); // Generic save method
        System.out.println("Assignment added successfully: " + newAssignment);
    }

    // Update an existing assignment
    public void updateAssignment(String oldTitle, String newTitle, String newDescription, String newDeadline, String newPriority) {
        boolean found = false;

        for (Assignment assignment : assignmentList) {
            if (assignment.getTitle().equalsIgnoreCase(oldTitle)) {
                assignment.setTitle(newTitle);
                assignment.setDescription(newDescription);
                assignment.setDeadline(newDeadline);
                assignment.setPriority(newPriority);
                found = true;
                break;
            }
        }

        if (found) {
            FileHandler.saveToFile(assignmentList, storageFileName); // Generic save method
            System.out.println("Assignment updated successfully: " + newTitle);
        } else {
            System.out.println("Error: No matching assignment found to update.");
        }
    }

    // Delete an assignment
    public void deleteAssignment(String title) {
        boolean removed = assignmentList.removeIf(assignment -> assignment.getTitle().equalsIgnoreCase(title));

        if (removed) {
            FileHandler.saveToFile(assignmentList, storageFileName); // Generic save method
            System.out.println("Assignment deleted successfully: " + title);
        } else {
            System.out.println("Error: No matching assignment found to delete.");
        }
    }
    //assignment progress
    public void updateAssignmentProgress(String title, int completionPercentage) {
        for (Assignment assignment : assignmentList) {
            if (assignment.getTitle().equalsIgnoreCase(title)) {
                assignment.setCompletionPercentage(completionPercentage);
                FileHandler.saveToFile(assignmentList, storageFileName); // Save updated progress
                System.out.println("Progress updated for assignment: " + title);
                return;
            }
        }
        System.out.println("Error: No matching assignment found to update progress.");
    }

    // Calculate overall completion progress
    public double calculateOverallProgress() {
        if (assignmentList.isEmpty()) {
            System.out.println("No assignments available to calculate progress.");
            return 0.0;
        }

        int totalProgress = assignmentList.stream()
                .mapToInt(Assignment::getCompletionPercentage)
                .sum();

        return (double) totalProgress / assignmentList.size();
    }
    
    // Retrieve assignments sorted by priority
    public List<Assignment> getAssignmentsSortedByPriority() {
        List<Assignment> sortedList = new ArrayList<>(assignmentList);
        sortedList.sort(Comparator.comparing(Assignment::getPriority));
        return sortedList;
    }

    // Retrieve assignments sorted by deadline
    public List<Assignment> getAssignmentsSortedByDeadline() {
        List<Assignment> sortedList = new ArrayList<>(assignmentList);
        sortedList.sort(Comparator.comparing(Assignment::getDeadline));
        return sortedList;
    }

    // Display sorted assignments
    public void displaySortedAssignments(List<Assignment> sortedList) {
        if (sortedList.isEmpty()) {
            System.out.println("No assignments found.");
            return;
        }

        System.out.println("\nSorted Assignment List:");
        for (Assignment assignment : sortedList) {
            System.out.println(assignment);
        }
    }


    // Display all assignments
    public void displayAssignments() {
        if (assignmentList.isEmpty()) {
            System.out.println("No assignments found.");
            return;
        }

        System.out.println("\nAssignment List:");
        for (Assignment assignment : assignmentList) {
            System.out.println(assignment);
        }
    }
    //display assignments sorted by priority
    public void displaySortedAssignmentsByPriority() {
        assignmentList.stream()
                .sorted(Comparator.comparing(Assignment::getPriority))
                .forEach(System.out::println);
    }

    //display assignments sorted by deadline
    public void displaySortedAssignmentsByDeadline() {
        assignmentList.stream()
                .sorted(Comparator.comparing(Assignment::getDeadline))
                .forEach(System.out::println);
    }


    // Get all assignments
    public List<Assignment> getAssignments() {
        return assignmentList;
    }
}
