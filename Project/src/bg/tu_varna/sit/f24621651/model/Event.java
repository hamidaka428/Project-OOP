package bg.tu_varna.sit.f24621651.model;

import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single event in the calendar with date, start/end time,
 * name and note.
 */
public class Event {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
    private String note;

    /**
     * Constructs an event after checking that {@code startTime} is before
     * {@code endTime}.
     *
     * @param date      the date of the event
     * @param startTime the start time
     * @param endTime   the end time
     * @param name      the name of the event
     * @param note      a free-form note
     * @throws InvalidTimeException if the start time is not before the end time
     */
    public Event(LocalDate date, LocalTime startTime, LocalTime endTime, String name, String note)
            throws InvalidTimeException {
        if (!startTime.isBefore(endTime)) {
            throw new InvalidTimeException("Invalid event time.");
        }

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.note = note;
    }

    /**
     * Returns the date of the event.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the event.
     *
     * @param date the new date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the start time.
     *
     * @return the start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the new start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time.
     *
     * @return the end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     *
     * @param endTime the new end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note.
     *
     * @param note the new note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Checks whether this event overlaps with another one on the same date.
     *
     * @param other the other event
     * @return {@code true} if the two events overlap
     */
    public boolean isOverlapping(Event other) {
        if (!date.equals(other.date)) {
            return false;
        }

        return startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime);
    }

    /**
     * Returns the duration of the event in minutes.
     *
     * @return the duration in minutes
     */
    public int getDurationInMinutes() {
        return (int) Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * Returns a string representation of the event.
     *
     * @return the formatted event
     */
    @Override
    public String toString() {
        return date + " " + startTime + " - " + endTime + " | " + name + " | " + note;
    }
}
