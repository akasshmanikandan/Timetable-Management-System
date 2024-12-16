package timetable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TimetableEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;

    public TimetableEvent(String eventName, LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event: " + eventName + ", Start: " + startTime + ", End: " + endTime + ", Description: " + description;
    }
}
