package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a date is invalid or has the wrong format.
 */
public class InvalidDateException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public InvalidDateException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and a cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
