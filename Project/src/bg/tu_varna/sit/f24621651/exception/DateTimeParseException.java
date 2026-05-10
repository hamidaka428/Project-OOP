package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a date or time string cannot be parsed.
 */
public class DateTimeParseException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public DateTimeParseException(String message) {
        super(message);
    }
}
