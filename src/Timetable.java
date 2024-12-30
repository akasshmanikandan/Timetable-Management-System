package timetable;
import java.io.Serializable;

public class Timetable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String day;
    private String time;
    private String subject;
    private String teacher;

    public Timetable(String day, String time, String subject, String teacher) {
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.teacher = teacher;
    }

    // Getters and setters
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return "Day: " + day + ", Time: " + time + ", Subject: " + subject + ", Teacher: " + teacher;
    }
}

