package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a new or modified event overlaps an existing one.
 */
public class EventOverlapException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public EventOverlapException(String message) {
        super(message);
    }
}
