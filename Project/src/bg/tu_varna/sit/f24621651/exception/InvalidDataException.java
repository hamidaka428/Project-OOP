package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when user-supplied data is invalid.
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public InvalidDataException(String message) {
        super(message);
    }
}
