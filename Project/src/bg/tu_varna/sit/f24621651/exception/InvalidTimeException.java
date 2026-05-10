package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a time is invalid or has the wrong format.
 */
public class InvalidTimeException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public InvalidTimeException(String message) {
        super(message);
    }

    /**
     * Constructs the exception with a message and a cause.
     *
     * @param message the detail message
     * @param cause   the underlying cause
     */
    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
