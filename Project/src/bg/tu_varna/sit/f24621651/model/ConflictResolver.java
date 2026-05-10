package bg.tu_varna.sit.f24621651.model;

/**
 * Strategy for resolving conflicts when merging two calendars.
 */
public interface ConflictResolver {

    /**
     * Decides whether the incoming event should replace the current one.
     *
     * @param currentEvent  the event already in the calendar
     * @param incomingEvent the conflicting event being merged in
     * @return {@code true} to keep the incoming event, {@code false} to keep the current one
     */
    boolean resolveConflict(Event currentEvent, Event incomingEvent);
}
