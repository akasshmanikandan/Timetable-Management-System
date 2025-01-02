package timetable.assignmet;

import timetable.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class AssignmentService {
    private List<Assignment> assignmentList;
    private String fileName;

    // Constructor to load with a custom file name
    public AssignmentService(String fileName) {
        this.fileName = fileName;
        this.assignmentList = FileHandler.loadAssignmentsFromFile(fileName);
        if (assignmentList == null) {
            this.assignmentList = new ArrayList<>();
        }
        System.out.println("Assignment data loaded from: " + fileName + ". Current assignments: " + assignmentList.size());
    }

    // add a new assignment
    public void addAssignment(String title, String description, String deadline, String priority) {
        Assignment newAssignment = new Assignment(title, description, deadline, priority);
        assignmentList.add(newAssignment);
        FileHandler.saveAssignmentsToFile(assignmentList, fileName); // Use fileName to save data
        System.out.println("Assignment added successfully: " + newAssignment);
    }

    // update an existing assignment
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
            FileHandler.saveAssignmentsToFile(assignmentList, fileName); // Use fileName to save updated data
            System.out.println("Assignment updated successfully: " + newTitle);
        } else {
            System.out.println("Error: No matching assignment found to update.");
        }
    }

    // delete a assignment
    public void deleteAssignment(String title) {
        boolean removed = assignmentList.removeIf(assignment -> assignment.getTitle().equalsIgnoreCase(title));

        if (removed) {
            FileHandler.saveAssignmentsToFile(assignmentList, fileName); // Use fileName to save updated data
            System.out.println("Assignment deleted successfully: " + title);
        } else {
            System.out.println("Error: No matching assignment found to delete.");
        }
    }

    // display all assignments
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

    // gets all assignments
    public List<Assignment> getAssignments() {
        return assignmentList;
    }
}
