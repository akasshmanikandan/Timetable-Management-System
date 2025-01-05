package timetable.attendance;

import java.io.Serializable;

public class Attendance implements Serializable {
    private String student;
    private String date;
    private boolean present;

    public Attendance(String student, String date, boolean present) {
        this.student = student;
        this.date = date;
        this.present = present;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "student='" + student + '\'' +
                ", date='" + date + '\'' +
                ", present=" + present +
                '}';
    }
}
