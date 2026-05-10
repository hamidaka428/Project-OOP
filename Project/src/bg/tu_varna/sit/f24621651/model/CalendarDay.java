package bg.tu_varna.sit.f24621651.model;

import bg.tu_varna.sit.f24621651.exception.EventNotFoundException;
import bg.tu_varna.sit.f24621651.exception.EventOverlapException;
import bg.tu_varna.sit.f24621651.exception.HolidayException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

/**
 * Represents a single day in the calendar. Holds the events for that day
 * and a flag that marks the day as a holiday.
 */
public class CalendarDay {

    private final List<Event> events;
    private boolean holiday;

    /**
     * Constructs an empty day that is not a holiday.
     */
    public CalendarDay() {
        this.events = new ArrayList<>();
        this.holiday = false;
    }

    /**
     * Adds an event to the day.
     *
     * @param event the event to add
     * @throws HolidayException      if the day is a holiday
     * @throws EventOverlapException if the event overlaps an existing one
     */
    public void addEvent(Event event) throws HolidayException, EventOverlapException {
        if (holiday) {
            throw new HolidayException("The event cannot be added because it is a holiday.");
        }

        if (hasOverlappingEvent(event)) {
            throw new EventOverlapException("The event overlaps an existing one.");
        }

        events.add(event);
    }

    /**
     * Removes the event with the given start and end times.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @throws EventNotFoundException if no event with this interval exists
     */
    public void removeEvent(LocalTime startTime, LocalTime endTime) throws EventNotFoundException {
        Event eventToRemove = findEvent(startTime, endTime);

        if (eventToRemove == null) {
            throw new EventNotFoundException("No event found for the given time interval.");
        }

        events.remove(eventToRemove);
    }

    /**
     * Returns the events sorted by start time.
     *
     * @return a sorted copy of the event list
     */
    public List<Event> getEventsSorted() {
        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort((firstEvent, secondEvent) ->
                firstEvent.getStartTime().compareTo(secondEvent.getStartTime()));
        return sortedEvents;
    }

    /**
     * Returns the total busy time on this day in whole hours.
     *
     * @return the busy time in hours
     */
    public int getBusyHours() {
        int totalMinutes = 0;

        for (Event event : events) {
            totalMinutes += event.getDurationInMinutes();
        }

        return totalMinutes / 60;
    }

    /**
     * Returns the live event list.
     *
     * @return the event list
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Returns whether the day is marked as a holiday.
     *
     * @return {@code true} if the day is a holiday
     */
    public boolean isHoliday() {
        return holiday;
    }

    /**
     * Sets the holiday flag.
     *
     * @param holiday the new flag value
     */
    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    /**
     * Checks whether any existing event overlaps the given one.
     *
     * @param event the candidate event
     * @return {@code true} if there is an overlap
     */
    private boolean hasOverlappingEvent(Event event) {
        for (Event currentEvent : events) {
            if (currentEvent.isOverlapping(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds an event with exactly the given start and end times.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the event, or {@code null} if none matches
     */
    private Event findEvent(LocalTime startTime, LocalTime endTime) {
        for (Event event : events) {
            boolean sameStartTime = event.getStartTime().equals(startTime);
            boolean sameEndTime = event.getEndTime().equals(endTime);

            if (sameStartTime && sameEndTime) {
                return event;
            }
        }
        return null;
    }
}
