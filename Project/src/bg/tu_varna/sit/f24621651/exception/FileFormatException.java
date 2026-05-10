package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a calendar file does not match the expected format.
 */
public class FileFormatException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public FileFormatException(String message) {
        super(message);
    }
}
