package timetable;


import java.util.Scanner;

import timetable.assignmet.AssignmentService;


public class Main {
    public static void main(String[] args) {
        // initializes services with default file 
        TimetableService timetableService = new TimetableService("timetable_data.ser");
        AssignmentService assignmentService = new AssignmentService("assignment_data.ser");

        Scanner scanner = new Scanner(System.in);
        int choice = 3;

        do {
            System.out.println("\n=== || Welcome To Timetable Management System || ===");
            System.out.println("1. Manage Timetables");
            System.out.println("2. Manage Assignments");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter correct number.");
                scanner.next(); // invalid input
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine(); //newline

            switch (choice) {
                case 1:
                    manageTimetable(scanner, timetableService);
                    break;

                case 2:
                    manageAssignments(scanner, assignmentService);
                    break;

                case 3:
                    System.out.println("Exiting System.");
                    break;

                default:
                    System.out.println("Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void manageTimetable(Scanner scanner, TimetableService timetableService) {
        int timetableChoice = 5;
        do {
            System.out.println("\n=== || Timetable Management || ===");
            System.out.println("1. Add Timetable Event");
            System.out.println("2. View Timetable Events");
            System.out.println("3. Update Timetable Event");
            System.out.println("4. Delete Timetable Event");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid! Please enter a number between 1 and 5.");
                scanner.next(); // invalid input
                continue;
            }

            timetableChoice = scanner.nextInt();
            scanner.nextLine(); //newline

            switch (timetableChoice) {
                case 1:
                    System.out.print("Enter day: ");
                    String day = scanner.nextLine().trim();
                    System.out.print("Enter time (00:00 AM): ");
                    String time = scanner.nextLine().trim();
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine().trim();
                    System.out.print("Enter teacher: ");
                    String teacher = scanner.nextLine().trim();
                    timetableService.addTimetableEntry(day, time, subject, teacher);
                    break;

                case 2:
                    timetableService.displayTimetable();
                    break;

                case 3:
                    System.out.print("Enter faculty name to update: ");
                    String oldDay = scanner.nextLine().trim();
                    System.out.print("Enter time of the event to update: ");
                    String oldTime = scanner.nextLine().trim();
                    System.out.print("Enter new day: ");
                    String newDay = scanner.nextLine().trim();
                    System.out.print("Enter new time: ");
                    String newTime = scanner.nextLine().trim();
                    System.out.print("Enter new subject: ");
                    String newSubject = scanner.nextLine().trim();
                    System.out.print("Enter new faculty name: ");
                    String newTeacher = scanner.nextLine().trim();
                    timetableService.updateTimetableEntry(oldDay, oldTime, newDay, newTime, newSubject, newTeacher);
                    break;

                case 4:
                    System.out.print("Enter faculty name to delete: ");
                    String deleteTeacher = scanner.nextLine().trim();
                    System.out.print("Enter time to delete: ");
                    String deleteTime = scanner.nextLine().trim();
                    timetableService.deleteTimetableEntry(deleteTeacher, deleteTime);
                    break;

                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (timetableChoice != 5);
    }

    private static void manageAssignments(Scanner scanner, AssignmentService assignmentService) {
        int assignmentChoice = 5;
        do {
            System.out.println("\n=== || Assignment Management || ===");
            System.out.println("1. Add Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. Update Assignment");
            System.out.println("4. Delete Assignment");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid! enter a number between 1 to 5.");
                scanner.next(); // invalid input
                continue;
            }

            assignmentChoice = scanner.nextInt();
            scanner.nextLine(); // newline

            switch (assignmentChoice) {
                case 1:
                    System.out.print("Enter  Course name: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine().trim();
                    System.out.print("Enter deadline (yyyy-mm-dd): ");
                    String deadline = scanner.nextLine().trim();
                    System.out.print("Enter priority (High, Medium, Low): ");
                    String priority = scanner.nextLine().trim();
                    assignmentService.addAssignment(title, description, deadline, priority);
                    break;

                case 2:
                    assignmentService.displayAssignments();
                    break;

                case 3:
                    System.out.print("Enter name of the assignment to update: ");
                    String oldTitle = scanner.nextLine().trim();
                    System.out.print("Enter new name: ");
                    String newTitle = scanner.nextLine().trim();
                    System.out.print("Enter new description: ");
                    String newDescription = scanner.nextLine().trim();
                    System.out.print("Enter new deadline: ");
                    String newDeadline = scanner.nextLine().trim();
                    System.out.print("Enter new priority: ");
                    String newPriority = scanner.nextLine().trim();
                    assignmentService.updateAssignment(oldTitle, newTitle, newDescription, newDeadline, newPriority);
                    break;

                case 4:
                    System.out.print("Enter name of the assignment to delete: ");
                    String deleteTitle = scanner.nextLine().trim();
                    assignmentService.deleteAssignment(deleteTitle);
                    break;

                case 5:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (assignmentChoice != 5);
    }
}
