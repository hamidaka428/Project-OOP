package bg.tu_varna.sit.f24621651.model;

import bg.tu_varna.sit.f24621651.exception.EventNotFoundException;
import bg.tu_varna.sit.f24621651.exception.EventOverlapException;
import bg.tu_varna.sit.f24621651.exception.HolidayException;
import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private final Map<LocalDate, CalendarDay> days;

    public Calendar() {
        this.days = new HashMap<>();
    }

    public void book(LocalDate date, LocalTime startTime, LocalTime endTime, String name, String note) throws InvalidTimeException, HolidayException, EventOverlapException {
        CalendarDay day = getOrCreateDay(date);
        Event event = new Event(date, startTime, endTime, name, note);
        day.addEvent(event);
    }

    public void unbook(LocalDate date, LocalTime startTime, LocalTime endTime) throws EventNotFoundException {
        CalendarDay day = days.get(date);
        if (day == null) {
            throw new EventNotFoundException("No events for this day.");
        }

        day.removeEvent(startTime, endTime);

        if (day.getEvents().isEmpty()) {
            days.remove(date);
        }
    }

    public List<Event> agenda(LocalDate date) {
        CalendarDay day = days.get(date);
        if (day == null) {
            return new ArrayList<>();
        }

        return day.getEventsSorted();
    }

    public List<Event> find(String searchedText) {
        List<Event> foundEvents = new ArrayList<>();

        for (CalendarDay day : days.values()) {
            for (Event event : day.getEvents()) {
                boolean nameMatches = event.getName() != null && event.getName().contains(searchedText);
                boolean noteMatches = event.getNote() != null && event.getNote().contains(searchedText);

                if (nameMatches || noteMatches) {
                    foundEvents.add(event);
                }
            }
        }

        return foundEvents;
    }

    public void holiday(LocalDate date) {
        CalendarDay day = getOrCreateDay(date);
        day.setHoliday(true);
    }

    public void change(LocalDate date, LocalTime startTime, String option, String newValue) throws EventNotFoundException, InvalidTimeException, EventOverlapException, HolidayException {
        CalendarDay day = days.get(date);
        if (day == null) {
            throw new EventNotFoundException("No event found for the given date.");
        }

        Event targetEvent = findEventByStart(day, startTime);
        if (targetEvent == null) {
            throw new EventNotFoundException("No event found for the given date.");
        }

        if (option.equalsIgnoreCase("name")) {
            targetEvent.setName(newValue);
        } else if (option.equalsIgnoreCase("note")) {
            targetEvent.setNote(newValue);
        } else if (option.equalsIgnoreCase("starttime")) {
            changeStartTime(day, targetEvent, newValue);
        } else if (option.equalsIgnoreCase("endtime")) {
            changeEndTime(day, targetEvent, newValue);
        } else if (option.equalsIgnoreCase("date")) {
            changeDate(day, targetEvent, newValue);
        } else {
            throw new IllegalArgumentException("Invalid change option.");
        }
    }

    public List<String> busydays(LocalDate from, LocalDate to) {
        int[] totalMinutesPerDay = new int[7];
        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (Map.Entry<LocalDate, CalendarDay> entry : days.entrySet()) {
            LocalDate currentDate = entry.getKey();

            boolean isAfterOrEqualFrom = currentDate.isAfter(from) || currentDate.equals(from);
            boolean isBeforeOrEqualTo = currentDate.isBefore(to) || currentDate.equals(to);

            if (isAfterOrEqualFrom && isBeforeOrEqualTo) {
                int dayIndex = currentDate.getDayOfWeek().getValue() - 1;
                int busyMinutes = 0;
                for (Event event : entry.getValue().getEvents()) {
                    busyMinutes += event.getDurationInMinutes();
                }
                totalMinutesPerDay[dayIndex] += busyMinutes;
            }
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (totalMinutesPerDay[i] > 0) {
                indices.add(i);
            }
        }

        for (int i = 0; i < indices.size() - 1; i++) {
            for (int j = i + 1; j < indices.size(); j++) {
                if (totalMinutesPerDay[indices.get(j)] > totalMinutesPerDay[indices.get(i)]) {
                    int temp = indices.get(i);
                    indices.set(i, indices.get(j));
                    indices.set(j, temp);
                }
            }
        }

        List<String> result = new ArrayList<>();
        for (int index : indices) {
            int hours = totalMinutesPerDay[index] / 60;
            int minutes = totalMinutesPerDay[index] % 60;
            if (minutes > 0) {
                result.add(dayNames[index] + " - " + hours + "h " + minutes + "min");
            } else {
                result.add(dayNames[index] + " - " + hours + "h");
            }
        }
        return result;
    }

    private CalendarDay getOrCreateDay(LocalDate date) {
        CalendarDay day = days.get(date);
        if (day == null) {
            day = new CalendarDay();
            days.put(date, day);
        }
        return day;
    }

    private Event findEventByStart(CalendarDay day, LocalTime startTime) {
        for (Event event : day.getEvents()) {
            if (event.getStartTime().equals(startTime)) {
                return event;
            }
        }
        return null;
    }

    private void changeStartTime(CalendarDay day, Event event, String newValue) throws InvalidTimeException, EventOverlapException {
        LocalTime newStartTime = LocalTime.parse(newValue);

        if (!newStartTime.isBefore(event.getEndTime())) {
            throw new InvalidTimeException("Start time must be before end time.");
        }

        LocalTime oldStartTime = event.getStartTime();
        event.setStartTime(newStartTime);

        if (hasOverlap(day, event)) {
            event.setStartTime(oldStartTime);
            throw new EventOverlapException("The event overlaps an existing one.");
        }
    }

    private void changeEndTime(CalendarDay day, Event event, String newValue) throws InvalidTimeException, EventOverlapException {
        LocalTime newEndTime = LocalTime.parse(newValue);

        if (!newEndTime.isAfter(event.getStartTime())) {
            throw new InvalidTimeException("End time must be after start time.");
        }

        LocalTime oldEndTime = event.getEndTime();
        event.setEndTime(newEndTime);

        if (hasOverlap(day, event)) {
            event.setEndTime(oldEndTime);
            throw new EventOverlapException("The event overlaps an existing one.");
        }
    }

    private void changeDate(CalendarDay currentDay, Event event, String newValue) throws EventNotFoundException, HolidayException, EventOverlapException {
        LocalDate oldDate = event.getDate();
        LocalDate newDate = LocalDate.parse(newValue);

        currentDay.removeEvent(event.getStartTime(), event.getEndTime());

        CalendarDay targetDay = getOrCreateDay(newDate);
        if (targetDay.isHoliday()) {
            currentDay.addEvent(event);
            throw new HolidayException("The event cannot be added because it is a holiday.");
        }

        event.setDate(newDate);

        if (hasOverlap(targetDay, event)) {
            event.setDate(oldDate);
            currentDay.addEvent(event);
            throw new EventOverlapException("The event overlaps an existing one.");
        }

        targetDay.addEvent(event);

        if (currentDay.getEvents().isEmpty()) {
            days.remove(oldDate);
        }
    }

    private boolean hasOverlap(CalendarDay day, Event changedEvent) {
        for (Event currentEvent : day.getEvents()) {
            if (currentEvent != changedEvent && currentEvent.isOverlapping(changedEvent)) {
                return true;
            }
        }
        return false;
    }

    public LocalTime[] findSlotInDay(LocalDate date, int durationMinutes) {
        if (durationMinutes <= 0) {
            return null;
        }

        CalendarDay day = days.get(date);

        if (day == null || day.getEvents().isEmpty()) {
            return createSlotIfPossible(LocalTime.MIN, durationMinutes);
        }

        List<Event> events = day.getEventsSorted();

        LocalTime dayStart = LocalTime.MIN;
        LocalTime dayEnd = LocalTime.MAX;

        Event firstEvent = events.get(0);
        if (hasEnoughTime(dayStart, firstEvent.getStartTime(), durationMinutes)) {
            return createSlotIfPossible(dayStart, durationMinutes);
        }

        for (int i = 0; i < events.size() - 1; i++) {
            LocalTime gapStart = events.get(i).getEndTime();
            LocalTime gapEnd = events.get(i + 1).getStartTime();

            if (hasEnoughTime(gapStart, gapEnd, durationMinutes)) {
                return createSlotIfPossible(gapStart, durationMinutes);
            }
        }

        Event lastEvent = events.get(events.size() - 1);
        if (hasEnoughTime(lastEvent.getEndTime(), dayEnd, durationMinutes)) {
            return createSlotIfPossible(lastEvent.getEndTime(), durationMinutes);
        }
        return null;
    }


    private boolean hasEnoughTime(LocalTime startTime, LocalTime endTime, int durationMinutes) {
        return startTime.plusMinutes(durationMinutes).compareTo(endTime) <= 0;
    }

    private LocalTime[] createSlotIfPossible(LocalTime startTime, int durationMinutes) {
        LocalTime endTime = startTime.plusMinutes(durationMinutes);

        if (endTime.isAfter(LocalTime.MAX)) {
            return null;
        }

        return new LocalTime[]{startTime, endTime};
    }

    public LocalDate findslot(LocalDate fromDate, int hours) {
        if (hours <= 0) {
            return null;
        }

        int durationMinutes = hours * 60;
        LocalDate currentDate = fromDate;

        for (int checkedDays = 0; checkedDays < 365; checkedDays++) {
            if (isWorkingDay(currentDate) && hasFreeWorkingSlot(currentDate, durationMinutes)) {
                return currentDate;
            }

            currentDate = currentDate.plusDays(1);
        }

        return null;
    }

    private boolean hasFreeWorkingSlot(LocalDate date, int durationMinutes) {
        LocalTime workStart = LocalTime.of(8, 0);
        LocalTime workEnd = LocalTime.of(17, 0);

        CalendarDay day = days.get(date);

        if (day == null || day.getEvents().isEmpty()) {
            return hasEnoughTime(workStart, workEnd, durationMinutes);
        }

        List<Event> events = day.getEventsSorted();
        LocalTime currentStart = workStart;

        for (Event event : events) {
            if (event.getEndTime().isBefore(workStart) || event.getStartTime().isAfter(workEnd)) {
                continue;
            }

            LocalTime eventStart = event.getStartTime();
            LocalTime eventEnd = event.getEndTime();

            if (eventStart.isBefore(workStart)) {
                eventStart = workStart;
            }

            if (eventEnd.isAfter(workEnd)) {
                eventEnd = workEnd;
            }

            if (hasEnoughTime(currentStart, eventStart, durationMinutes)) {
                return true;
            }

            if (eventEnd.isAfter(currentStart)) {
                currentStart = eventEnd;
            }
        }

        return hasEnoughTime(currentStart, workEnd, durationMinutes);
    }

    private boolean isWorkingDay(LocalDate date) {
        int day = date.getDayOfWeek().getValue();
        return day >= 1 && day <= 5;
    }

    public boolean isHolidayDay(LocalDate date) {
        CalendarDay day = days.get(date);
        if (day == null) {
            return false;
        }
        return day.isHoliday();
    }

    public LocalDate findslotwith(LocalDate fromDate, int hours, Calendar otherCalendar) {
        if (hours <= 0 || otherCalendar == null) {
            return null;
        }

        int durationMinutes = hours * 60;
        LocalDate currentDate = fromDate;

        for (int checkedDays = 0; checkedDays < 365; checkedDays++) {
            if (isWorkingDay(currentDate)) {
                boolean freeInThisCalendar = hasFreeWorkingSlot(currentDate, durationMinutes);
                boolean freeInOtherCalendar = otherCalendar.hasFreeWorkingSlot(currentDate, durationMinutes);

                if (freeInThisCalendar && freeInOtherCalendar) {
                    return currentDate;
                }
            }
            currentDate = currentDate.plusDays(1);
        }
        return null;
    }

    public void merge(Calendar other, ConflictResolver resolver) throws InvalidTimeException, HolidayException, EventOverlapException, EventNotFoundException {
        for (Map.Entry<LocalDate, CalendarDay> entry : other.days.entrySet()) {
            LocalDate date = entry.getKey();
            CalendarDay otherDay = entry.getValue();

            for (Event incoming : otherDay.getEvents()) {
                Event conflictingEvent = findOverlappingEvent(date, incoming);

                if (conflictingEvent == null) {
                    this.book(date, incoming.getStartTime(), incoming.getEndTime(),
                            incoming.getName(), incoming.getNote());
                }
                else {
                    boolean keepIncoming = resolver.resolveConflict(conflictingEvent, incoming);

                    if (keepIncoming) {
                        this.unbook(date, conflictingEvent.getStartTime(), conflictingEvent.getEndTime());
                        this.book(date, incoming.getStartTime(), incoming.getEndTime(),
                                incoming.getName(), incoming.getNote());
                    }
                }
            }
        }
    }

    private Event findOverlappingEvent(LocalDate date, Event incomingEvent) {
        CalendarDay day = days.get(date);

        if (day == null) {
            return null;
        }

        for (Event currentEvent : day.getEvents()) {
            if (currentEvent.isOverlapping(incomingEvent)) {
                return currentEvent;
            }
        }

        return null;
    }

    public Map<LocalDate, List<Event>> getAllEvents() {
        Map<LocalDate, List<Event>> allEvents = new HashMap<>();
        for (Map.Entry<LocalDate, CalendarDay> entry : days.entrySet()) {
            List<Event> events = entry.getValue().getEvents();
            if (!events.isEmpty()) {
                allEvents.put(entry.getKey(), events);
            }
        }
        return allEvents;
    }

    public List<LocalDate> getHolidays() {
        List<LocalDate> holidays = new ArrayList<>();
        for (Map.Entry<LocalDate, CalendarDay> entry : days.entrySet()) {
            if (entry.getValue().isHoliday()) {
                holidays.add(entry.getKey());
            }
        }
        return holidays;
    }

    public CalendarDay getDay(LocalDate date) {
        return days.get(date);
    }


}