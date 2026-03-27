package bg.tu_varna.sit.f24621651.exception;

public class InvalidDateException extends RuntimeException {

    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }
}