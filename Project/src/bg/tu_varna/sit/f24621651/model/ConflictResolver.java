package bg.tu_varna.sit.f24621651.model;

public interface ConflictResolver {
    boolean resolveConflict(Event currentEvent, Event incomingEvent);
}