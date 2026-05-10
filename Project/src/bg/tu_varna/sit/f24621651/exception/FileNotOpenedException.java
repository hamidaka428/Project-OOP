package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when a command requires an opened file but no file is opened.
 */
public class FileNotOpenedException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public FileNotOpenedException(String message) {
        super(message);
    }
}
