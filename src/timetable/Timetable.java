package timetable;

import java.io.Serializable;
import java.util.Objects;

public class Timetable implements Serializable {

    private static final long serialVersionUID = 1L;

    private String day;
    private String time;
    private String subject;
    private String teacher;
    private boolean isRecurring; // Indicates if the event is recurring
    private String recurrencePattern; // Options: "daily", "weekly"


    public Timetable(String day, String time, String subject, String teacher) {
        this.day = day;
        this.time = time;
        this.subject = subject;
        this.teacher = teacher;
        this.isRecurring = isRecurring;
        this.recurrencePattern = recurrencePattern;
    }

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Timetable timetable = (Timetable) obj;
        return Objects.equals(day, timetable.day) &&
               Objects.equals(time, timetable.time) &&
               Objects.equals(subject, timetable.subject) &&
               Objects.equals(teacher, timetable.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time, subject, teacher);
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", subject='" + subject + '\'' +
                ", teacher='" + teacher + '\'' +
                 ", isRecurring=" + isRecurring +
            ", recurrencePattern='" + recurrencePattern + '\'' +
                '}';
    }
}
