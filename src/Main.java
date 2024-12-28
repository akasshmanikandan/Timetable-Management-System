package timetable;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TimetableService timetableService = new TimetableService();
        Scanner scanner = new Scanner(System.in);
        int choice=4;

        do {
            System.out.println("\n=== ||Timetable-Management-System|| ===");
            System.out.println("1. Add Timetable Event");
            System.out.println("2. See Timetable Events");
            System.out.println("3. Delete a Timetable Entry");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            //handling the menu
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); // consume invalid input
                continue;       // re-directs the menu
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // new line

            switch (choice) {
                case 1:
                    // add a new timetable event
                    System.out.print("Enter day: ");
                    String day = scanner.nextLine().trim();
                    System.out.print("Enter time (00:00 AM format): ");
                    String time = scanner.nextLine().trim();
                    System.out.print("Enter Subject: ");
                    String subject = scanner.nextLine().trim();
                    System.out.print("Enter Factulty Name: ");
                    String teacher = scanner.nextLine().trim();
                    timetableService.addTimetableEntry(day, time, subject, teacher);
                    break;

                case 2:
                    //shows all events
                    timetableService.displayTimetable();
                    break;

                case 3:
                    // deletes a timetable entry
                    System.out.print("Enter day to delete: ");
                    String deleteDay = scanner.nextLine().trim();
                    System.out.print("Enter time to delete: ");
                    String deleteTime = scanner.nextLine().trim();
                    timetableService.deleteTimetableEntry(deleteDay, deleteTime);
                    break;

                case 4:
                    // exit from program
                    System.out.println("Exiting Timetable-Management-System.");
                    break;

                default:
                    // invalid menu
                    System.out.println("Invalid choice!. Please enter a number between 1 and 4.");
            }
        } 
        while (choice != 4);

        scanner.close();
    } 
    }

