package timetable;

import java.io.Serializable;
import java.util.Objects;

public class Timetable implements Serializable {
    private String day;
    private String time;
    private String subject;
    private String teacher;
    private String recurrence; // daily, weekly, or monthly

    //constructor with default recurrence
    public Timetable(String day, String time, String subject, String teacher) {
        this(day, time, subject, teacher, "None"); //default recurrence is "None"
    }

   
    public Timetable(String day, String time, String subject, String teacher, String recurrence) {
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.teacher = teacher;
        this.recurrence = recurrence != null ? recurrence : "None";
    }

    //getters and setters
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

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence != null ? recurrence : "None";
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", recurrence='" + recurrence + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return Objects.equals(day, timetable.day) &&
               Objects.equals(time, timetable.time) &&
               Objects.equals(subject, timetable.subject) &&
               Objects.equals(teacher, timetable.teacher) &&
               Objects.equals(recurrence, timetable.recurrence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time, subject, teacher, recurrence);
    }
}
