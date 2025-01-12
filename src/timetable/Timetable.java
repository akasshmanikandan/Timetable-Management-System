package timetable;

import java.io.Serializable;
import java.util.Objects;

public class Timetable implements Serializable {
   
	private static final long serialVersionUID = 1L;
	private String day;
    private String time;
    private String subject;
    private String teacher;
    private String recurrence; 
    private int reminderOffset;// daily, weekly, or monthly
   
    //constructor with default recurrence
    public Timetable(String day, String time, String subject, String teacher,int reminderOffset) {
        this(day, time, subject, teacher, "None",reminderOffset); //default recurrence is "None"
    }

   
    public Timetable(String day, String time, String subject, String teacher, String recurrenceint, int reminderOffset) {
        this.day = day;
        this.time = time.trim();
        this.subject = subject;
        this.teacher = teacher;
        this.recurrence = recurrence != null ? recurrence : "None";
        this.reminderOffset = reminderOffset;
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
    public int getReminderOffset() {
        return reminderOffset;
    }

    public void setReminderOffset(int reminderOffset) {
        this.reminderOffset = reminderOffset;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                ", recurrence='" + recurrence + '\'' +
                ", reminderOffset=" + reminderOffset + " minutes before event" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return reminderOffset == timetable.reminderOffset &&
               Objects.equals(day, timetable.day) &&
               Objects.equals(time, timetable.time) &&
               Objects.equals(subject, timetable.subject) &&
               Objects.equals(teacher, timetable.teacher) &&
               Objects.equals(recurrence, timetable.recurrence);
        
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time, subject, teacher, recurrence, reminderOffset);
    }
}
