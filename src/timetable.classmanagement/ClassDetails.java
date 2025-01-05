package timetable.classmanagement;

import java.io.Serializable;

public class ClassDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String day;
    private String subject;
    private String time;
    private String instructor;

    public ClassDetails(String day, String subject, String time, String instructor) {
        this.day = day;
        this.subject = subject;
        this.time = time;
        this.instructor = instructor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "ClassDetails{" +
                "day='" + day + '\'' +
                ", subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}
