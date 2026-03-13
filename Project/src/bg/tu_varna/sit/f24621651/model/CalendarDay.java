package bg.tu_varna.sit.f24621651.model;

import bg.tu_varna.sit.f24621651.exception.EventNotFoundException;
import bg.tu_varna.sit.f24621651.exception.EventOverlapException;
import bg.tu_varna.sit.f24621651.exception.HolidayException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CalendarDay {
    private List<Event> events = new ArrayList<>();
    private boolean isHoliday;

    public CalendarDay() {
        this.events = new ArrayList<>();
        this.isHoliday = false;
    }

    public void addEvent(Event event) throws HolidayException, EventOverlapException {
        if (isHoliday) {
            throw new HolidayException("Cannot add event. It's a holiday.");
        }
        for (Event existing : events) {
            if (existing.isOverlapping(event)) {
                throw new EventOverlapException("Cannot add event. It's overlapping.");
            }
        }
        events.add(event);
    }

    public void removeEvent(LocalTime start, LocalTime end) throws EventNotFoundException {
        Event toRemove = null;
        for (Event event : events) {
            if (event.getStartTime().equals(start) && event.getEndTime().equals(end)) {
                toRemove = event;
                break;
            }
        }
        if (toRemove == null) {
            throw new EventNotFoundException("Event not found.");
        }
        events.remove(toRemove);
    }

    public List<Event> getEventsSorted() {
        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort(new Comparator<Event>()
        {
            @Override
            public int compare(Event e1, Event e2) {
                return e1.getStartTime().compareTo(e2.getStartTime());
            }
        });
        return sortedEvents;
    }

    public int getBusyHours() {
        int totalHours = 0;

        for (Event event : events) {
            int startMinutes = event.getStartTime().getHour() * 60 + event.getStartTime().getMinute();
            int endMinutes = event.getEndTime().getHour() * 60 + event.getEndTime().getMinute();

            totalHours += endMinutes - startMinutes;
        }
            return totalHours / 60;
    }
    public List<Event> getEvents() {
        return events;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean isHoliday) {
        this.isHoliday = isHoliday;
    }

}
