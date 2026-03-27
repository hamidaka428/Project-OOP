package bg.tu_varna.sit.f24621651.exception;

public class InvalidTimeException extends RuntimeException {

    public InvalidTimeException(String message) {
        super(message);
    }

    public InvalidTimeException(String message, Throwable cause) {
        super(message, cause);
    }
}