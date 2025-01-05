package timetable.assignmet;

import timetable.FileHandler;

import java.util.ArrayList;
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
    public void calculateProgress(String title, int completedTasks, int totalTasks) {
        for (Assignment assignment : assignmentList) {
            if (assignment.getTitle().equalsIgnoreCase(title)) {
                assignment.setProgress((completedTasks * 100) / totalTasks);
                FileHandler.saveToFile(assignmentList, storageFileName);
                System.out.println("Progress updated for assignment: " + title);
                return;
            }
        }
        System.out.println("Error: Assignment not found.");
    }
    public void markAsComplete(String title) {
        calculateProgress(title, 1, 1);
        System.out.println("Assignment marked as complete: " + title);
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

    // Get all assignments
    public List<Assignment> getAssignments() {
        return assignmentList;
        
    }
    public List<Assignment> sortByPriority() {
        return assignmentList.stream()
            .sorted((a1, a2) -> a1.getPriority().compareToIgnoreCase(a2.getPriority()))
            .collect(Collectors.toList());
    }

    public List<Assignment> sortByDeadline() {
        return assignmentList.stream()
            .sorted((a1, a2) -> a1.getDeadline().compareTo(a2.getDeadline()))
            .collect(Collectors.toList());
    }

}
