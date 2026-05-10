package bg.tu_varna.sit.f24621651.exception;

/**
 * Thrown when an event is being booked on a date marked as a holiday.
 */
public class HolidayException extends RuntimeException {

    /**
     * Constructs the exception with a message.
     *
     * @param message the detail message
     */
    public HolidayException(String message) {
        super(message);
    }
}
