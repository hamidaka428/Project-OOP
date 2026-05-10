package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when an event cannot be found in the calendar.
 */
public class EventNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public EventNotFoundException(String message) {
        super(message);
    }
}
