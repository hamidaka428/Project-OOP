package bg.tu_varna.sit.f24621651.model;

import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
    private String note;

    public Event(LocalDate date, LocalTime startTime, LocalTime endTime, String name, String note) throws InvalidTimeException {
        if (!startTime.isBefore(endTime)) {
            throw new InvalidTimeException("Invalid event time.");
        }
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isOverlapping(Event other) {
        if (!date.equals(other.date)) {
            return false;
        }
        return startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime);
    }

    @Override
    public String toString() {
        return date + " " + startTime + " - " + endTime + " | " + name + " | " + note;
    }
}
