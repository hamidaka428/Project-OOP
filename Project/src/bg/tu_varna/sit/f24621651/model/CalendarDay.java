package bg.tu_varna.sit.f24621651.model;

import bg.tu_varna.sit.f24621651.exception.EventNotFoundException;
import bg.tu_varna.sit.f24621651.exception.EventOverlapException;
import bg.tu_varna.sit.f24621651.exception.HolidayException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

public class CalendarDay {
    private final List<Event> events;
    private boolean holiday;

    public CalendarDay() {
        this.events = new ArrayList<>();
        this.holiday = false;
    }

    public void addEvent(Event event) throws HolidayException, EventOverlapException {
        if (holiday) {
            throw new HolidayException("The event cannot be added because it is a holiday.");
        }

        if (hasOverlappingEvent(event)) {
            throw new EventOverlapException("The event overlaps an existing one.");
        }

        events.add(event);
    }

    public void removeEvent(LocalTime startTime, LocalTime endTime) throws EventNotFoundException {
        Event eventToRemove = findEvent(startTime, endTime);

        if (eventToRemove == null) {
            throw new EventNotFoundException("No event found for the given time interval.");
        }

        events.remove(eventToRemove);
    }

    public List<Event> getEventsSorted() {
        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort((firstEvent, secondEvent) ->
                firstEvent.getStartTime().compareTo(secondEvent.getStartTime()));
        return sortedEvents;
    }

    public int getBusyHours() {
        int totalMinutes = 0;

        for (Event event : events) {
            totalMinutes += event.getDurationInMinutes();
        }

        return totalMinutes / 60;
    }

    public List<Event> getEvents() {
        return events;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    private boolean hasOverlappingEvent(Event event) {
        for (Event currentEvent : events) {
            if (currentEvent.isOverlapping(event)) {
                return true;
            }
        }
        return false;
    }

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