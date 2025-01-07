package timetable;

import timetable.classmanagement.ClassDetails;
import timetable.classmanagement.ClassManagementService;
import timetable.Authentication.AuthenticationService;
import timetable.assignmet.AssignmentService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // initialize services with default file names
        TimetableService timetableService = new TimetableService("timetable_data.ser");
        AssignmentService assignmentService = new AssignmentService("assignment_data.ser");
        ClassManagementService classService = new ClassManagementService("class_data.ser");
        AuthenticationService authService = new AuthenticationService("auth_data.ser"); 
        Scanner scanner = new Scanner(System.in);
        int choice = 4;

        do {
            System.out.println("\n=== || Welcome To Timetable Management System || ===");
            System.out.println("1. Manage Timetables");
            System.out.println("2. Manage Assignments");
            System.out.println("3. Manage Classes");
            System.out.println("4. Manage Authentication");
            System.out.println("5. View Recurring Events");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid! Please enter a valid number.");
                scanner.next();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageTimetable(scanner, timetableService);
                    break;

                case 2:
                    manageAssignments(scanner, assignmentService);
                    break;

                case 3:
                    manageClasses(scanner, classService);
                    break;
                case 4:
                    manageAuthentication(scanner, authService);
                case 5:
                    manageRecurringEvents(scanner, timetableService);
                    break;

                case 6:
                    System.out.println("Exiting System.");
                    break;

                default:
                    System.out.println("Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void manageTimetable(Scanner scanner, TimetableService timetableService) {
        int timetableChoice = 8; 
        do {
            System.out.println("\n=== || Timetable Management || ===");
            System.out.println("1. Add Timetable Event");
            System.out.println("2. View Timetable Events");
            System.out.println("3. Update Timetable Event");
            System.out.println("4. Delete Timetable Event");
            System.out.println("5. Daily Summary");
            System.out.println("6. Weekly Summary");
            System.out.println("7. Monthly Summary");
            System.out.println("8. Export Timetable Data");
            System.out.println("9. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                scanner.next();
                continue;
            }

            timetableChoice = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.print("Enter recurrence (Daily, Weekly, Monthly): ");
                    String recurrence = scanner.nextLine().trim();
                    timetableService.addTimetableEntry(day, time, subject, teacher, recurrence);
                    break;

                case 2:
                    timetableService.displayTimetable();
                    break;

                case 3:
                    System.out.print("Enter day of event to update: ");
                    String oldDay = scanner.nextLine().trim();
                    System.out.print("Enter time of event to update: ");
                    String oldTime = scanner.nextLine().trim();
                    System.out.print("Enter new day: ");
                    String newDay = scanner.nextLine().trim();
                    System.out.print("Enter new time: ");
                    String newTime = scanner.nextLine().trim();
                    System.out.print("Enter new subject: ");
                    String newSubject = scanner.nextLine().trim();
                    System.out.print("Enter new teacher: ");
                    String newTeacher = scanner.nextLine().trim();
                    timetableService.updateTimetableEntry(oldDay, oldTime, newDay, newTime, newSubject, newTeacher);
                    break;

                case 4:
                    System.out.print("Enter teacher of event to delete: ");
                    String deleteTeacher = scanner.nextLine().trim();
                    System.out.print("Enter time of event to delete: ");
                    String deleteTime = scanner.nextLine().trim();
                    timetableService.deleteTimetableEntry(deleteTeacher, deleteTime);
                    break;

                case 5:
                    System.out.print("Enter day for daily summary: ");
                    String summaryDay = scanner.nextLine().trim();
                    timetableService.displaySummary(timetableService.getDailySummary(summaryDay));
                    break;

                case 6:
                    System.out.println("Weekly Summary:");
                    timetableService.displaySummary(timetableService.getWeeklySummary());
                    break;

                case 7:
                    System.out.println("Monthly Summary:");
                    timetableService.displaySummary(timetableService.getMonthlySummary());
                    break;
                case 8:
                    exportTimetableData(scanner, timetableService);
                    break;
                case 9:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (timetableChoice != 9);
    }
    private static void exportTimetableData(Scanner scanner, TimetableService timetableService) {
        System.out.println("\n=== || Export Timetable Data || ===");
        System.out.println("1. Export to Text File");
        System.out.println("2. Export to CSV File");
        System.out.print("Enter your choice: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Error!. Please enter 1 or 2.");
            scanner.next();
            return;
        }

        int exportChoice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the file name (including extension, timetable.txt or timetable.csv): ");
        String exportFileName = scanner.nextLine().trim();

        switch (exportChoice) {
            case 1:
                timetableService.exportToTextFile(exportFileName);
                break;
            case 2:
                timetableService.exportToCSVFile(exportFileName);
                break;
            default:
                System.out.println("Please try again.");
        }
    }
                    //ASSIGNMENT MANAGEMENT//
    private static void manageAssignments(Scanner scanner, AssignmentService assignmentService) {
        int assignmentChoice = 8; 
        do {
            System.out.println("\n=== || Assignment Management || ===");
            System.out.println("1. Add Assignment");
            System.out.println("2. View Assignments");
            System.out.println("3. Update Assignment");
            System.out.println("4. Delete Assignment");
            System.out.println("5. Update Assignment Progress");
            System.out.println("6. View Assignments Sorted by Priority");
            System.out.println("7. View Assignments Sorted by Deadline");
            System.out.println("8. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid! Please enter a number between 1 and 8.");
                scanner.next();
                continue;
            }

            assignmentChoice = scanner.nextInt();
            scanner.nextLine();

            switch (assignmentChoice) {
                case 1:
                    System.out.print("Enter assignment title: ");
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
                    System.out.print("Enter title of assignment to update: ");
                    String oldTitle = scanner.nextLine().trim();
                    System.out.print("Enter new title: ");
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
                    System.out.print("Enter title of assignment to delete: ");
                    String deleteTitle = scanner.nextLine().trim();
                    assignmentService.deleteAssignment(deleteTitle);
                    break;

                case 5:
                    System.out.print("Enter title of assignment to update progress: ");
                    String progressTitle = scanner.nextLine().trim();
                    System.out.print("Enter completion percentage (0-100): ");
                    int completion = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    assignmentService.updateAssignmentProgress(progressTitle, completion);
                    break;
                case 6:
                    //assignments sorted by priority
                    System.out.println("\nAssignments Sorted by Priority:");
                    assignmentService.displaySortedAssignmentsByPriority();
                    break;

                case 7:
                    //assignments sorted by deadline
                    System.out.println("\nAssignments Sorted by Deadline:");
                    assignmentService.displaySortedAssignmentsByDeadline();
                    break;

                case 8:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (assignmentChoice != 8);
    }

    private static void manageClasses(Scanner scanner, ClassManagementService classService) {
        int classChoice = 7; 
        do {
            System.out.println("\n=== || Class Management || ===");
            System.out.println("1. Add Class");
            System.out.println("2. View Classes");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("5. Filter Classes by Subject");
            System.out.println("6. Filter Classes by Day");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Error! Please enter a number between 1 and 7.");
                scanner.next();
                continue;
            }

            classChoice = scanner.nextInt();
            scanner.nextLine();

            switch (classChoice) {
            //add new class
                case 1:
                    System.out.print("Enter day: ");
                    String day = scanner.nextLine().trim();
                    System.out.print("Enter subject: ");
                    String subject = scanner.nextLine().trim();
                    System.out.print("Enter time (00:00 AM): ");
                    String time = scanner.nextLine().trim();
                    System.out.print("Enter instructor: ");
                    String instructor = scanner.nextLine().trim();
                    classService.addClass(day, subject, time, instructor);
                    break;

                case 2:
                    classService.displayClasses();
                    break;

                case 3:
                	//updates a class
                    System.out.print("Enter subject of class to update: ");
                    String oldSubject = scanner.nextLine().trim();
                    System.out.print("Enter new subject: ");
                    String newSubject = scanner.nextLine().trim();
                    System.out.print("Enter new time: ");
                    String newTime = scanner.nextLine().trim();
                    System.out.print("Enter new instructor: ");
                    String newInstructor = scanner.nextLine().trim();
                    classService.updateClass(oldSubject, newSubject, newTime, newInstructor);
                    break;

                case 4:
                	//deletes a class
                    System.out.print("Enter subject of class to delete: ");
                    String deleteSubject = scanner.nextLine().trim();
                    classService.deleteClass(deleteSubject);
                    break;

                case 5:
                	//filter classes by subject
                    System.out.print("Enter subject to filter by: ");
                    String filterSubject = scanner.nextLine().trim();
                    List<ClassDetails> subjectFilteredClasses = classService.getClassesBySubject(filterSubject);
                    classService.displayFilteredClasses(subjectFilteredClasses);
                    break;

                case 6:
                	//filter by day 
                    System.out.print("Enter day to filter by: ");
                    String filterDay = scanner.nextLine().trim();
                    List<ClassDetails> dayFilteredClasses = classService.getClassesByDay(filterDay);
                    classService.displayFilteredClasses(dayFilteredClasses);
                    break;

                case 7:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (classChoice != 7);
    }

private static void manageAuthentication(Scanner scanner, AuthenticationService authService) {
    int authChoice = 3;
    do {
        System.out.println("\n=== || Authentication Management || ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Show Registered Users");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Error! Please enter a number between 1 and 4.");
            scanner.next();
            continue;
        }

        authChoice = scanner.nextInt();
        scanner.nextLine();

        switch (authChoice) {
            case 1:
            	//adds new user
                System.out.print("Enter username: ");
                String username = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String password = scanner.nextLine().trim();
                authService.register(username, password);
                break;

            case 2:
            	//login into account
                System.out.print("Enter username: ");
                String loginUsername = scanner.nextLine().trim();
                System.out.print("Enter password: ");
                String loginPassword = scanner.nextLine().trim();
                authService.login(loginUsername, loginPassword);
                break;
                //shows registered users
            case 3:
                authService.displayRegisteredUsers();
                break;
                
            case 4:
                System.out.println("Returning to Main Menu...");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }
    } while (authChoice != 4);
}

private static void manageRecurringEvents(Scanner scanner, TimetableService timetableService) {
    String recurrenceType;

    do {
        System.out.println("\n=== || Recurring Events Management || ===");
        System.out.print("Enter recurrence type (Daily, Weekly, Monthly) or enter Exit");
        recurrenceType = scanner.nextLine().trim();

        if (recurrenceType.equalsIgnoreCase("Exit")) {
            System.out.println("Returning to Timetable Menu...");
            break;
        }

        if (!recurrenceType.equalsIgnoreCase("Daily") &&
            !recurrenceType.equalsIgnoreCase("Weekly") &&
            !recurrenceType.equalsIgnoreCase("Monthly")) {
            System.out.println("Invalid recurrence type. Please enter 'Daily', 'Weekly', 'Monthly', or 'Exit'.");
        } else {
            timetableService.displayRecurringEvents(recurrenceType);
        }
    } while (!recurrenceType.equalsIgnoreCase("Exit"));
}
}


