package timetable.assignmet;

import java.io.Serializable;

public class Assignment implements Serializable {
    private String title;
    private String description;
    private String deadline;
    private String priority;
    private int completionPercentage;
    public Assignment(String title, String description, String deadline, String priority) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.completionPercentage = 0; 
    }

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

    public int getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(int completionPercentage) {
        this.completionPercentage = Math.max(0, Math.min(100, completionPercentage)); // Ensure valid range
    }
    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                ", priority='" + priority + '\'' +
                 ", completionPercentage=" + completionPercentage + "%" +
                '}';
    }
}
