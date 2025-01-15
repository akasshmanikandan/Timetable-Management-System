package timetable.assignmet;

import timetable.FileHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentService {
    private List<Assignment> assignmentList;
    private String storageFileName; // File name for storing assignment data
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AssignmentService(String storageFileName) {
        this.storageFileName = storageFileName;
        this.assignmentList = FileHandler.loadFromFile(storageFileName); // Generic load method
        if (assignmentList == null) {
            this.assignmentList = new ArrayList<>();
        }
        System.out.println("Assignment data loaded from: " + storageFileName + ". Current assignments: " + assignmentList.size());
    }

    //add a new assignment
    public void addAssignment(String title, String description, String deadline, String priority) {
        Assignment newAssignment = new Assignment(title, description, deadline, priority);
        assignmentList.add(newAssignment);
        FileHandler.saveToFile(assignmentList, storageFileName); // Generic save method
        System.out.println("Assignment added successfully: " + newAssignment);
    }

    //update an existing assignment 
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

    //deletes an assignment
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

    //calculate overall completion progress
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

    //get assignments sorted by priority
    public List<Assignment> getAssignmentsSortedByPriority() {
        List<String> priorityOrder = List.of("High", "Medium", "Low");

        return assignmentList.stream()
                .sorted((a1, a2) -> Integer.compare(
                        priorityOrder.indexOf(a1.getPriority()),
                        priorityOrder.indexOf(a2.getPriority())
                ))
                .collect(Collectors.toList());
    }

    //retrieve assignments sorted by deadline
    public List<Assignment> getAssignmentsSortedByDeadline() {
        return assignmentList.stream()
                .sorted(Comparator.comparing(Assignment::getDeadline))
                .collect(Collectors.toList());
    }

    //display all assignments
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
    public List<Assignment> getAssignmentsSortedByPriorityAndDeadline() {
    	 return assignmentList.stream()
                .sorted((a1, a2) -> {
                    int priorityComparison = a1.getPriority().compareTo(a2.getPriority());
                    if (priorityComparison == 0) {
                        //if priorities are the same, sort by deadline
                        return a1.getDeadline().compareTo(a2.getDeadline());
                    }
                    return priorityComparison;
                })
                .collect(Collectors.toList());
    }
 //method to get assignments sorted by title
    public List<Assignment> getAssignmentsSortedByTitle() {
        return assignmentList.stream()
                .sorted(Comparator.comparing(Assignment::getTitle))
                .collect(Collectors.toList());
    }
    //analyze and display overdue assignments
    public List<Assignment> getOverdueAssignments() {
        LocalDate today = LocalDate.now();
        return assignmentList.stream()
                .filter(assignment -> {
                    LocalDate deadline = LocalDate.parse(assignment.getDeadline(), DATE_FORMATTER);
                    return today.isAfter(deadline);
                })
                .collect(Collectors.toList());
    }

    public void displayOverdueAssignments() {
        List<Assignment> overdueAssignments = getOverdueAssignments();
        if (overdueAssignments.isEmpty()) {
            System.out.println("No overdue assignments.");
        } else {
            System.out.println("\n=== || Overdue Assignments || ===");
            for (Assignment assignment : overdueAssignments) {
                System.out.println(assignment);
            }
        }
    }

    // Display sorted assignments by priority
    public void displaySortedAssignmentsByPriority() {
        assignmentList.stream()
                .sorted(Comparator.comparing(Assignment::getPriority))
                .forEach(System.out::println);
    }

    // Display sorted assignments by deadline
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
