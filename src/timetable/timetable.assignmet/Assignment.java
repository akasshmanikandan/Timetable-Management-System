package timetable.assignmet;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Assignment implements Serializable {
	  private static final long serialVersionUID = 6021257783198521890L; 
    private String title;
    private String description;
    private String deadline; // Format: yyyy-MM-dd
    private String priority;
    private int completionPercentage;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor
    public Assignment(String title, String description, String deadline, String priority) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.completionPercentage = 0; // Default value
    }

    // Getter and setter methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                ", priority='" + priority + '\'' +
                ", completionPercentage=" + completionPercentage +
                '}';
    }

    // Utility method to check if the assignment is overdue
    public boolean isOverdue() {
        LocalDate deadlineDate = LocalDate.parse(deadline, DATE_FORMATTER);
        return LocalDate.now().isAfter(deadlineDate);
    }
}
