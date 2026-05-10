package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a numeric argument cannot be parsed as a number.
 */
public class NumberFormatException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public NumberFormatException(String message) {
        super(message);
    }
}
