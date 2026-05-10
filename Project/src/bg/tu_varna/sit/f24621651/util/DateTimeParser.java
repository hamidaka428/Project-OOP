package bg.tu_varna.sit.f24621651.util;

import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing and formatting dates and times.
 * Dates use the format {@code yyyy-MM-dd}, times use {@code HH:mm}.
 */
public final class DateTimeParser {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Private constructor to prevent instantiation.
     */
    private DateTimeParser() {
        throw new IllegalArgumentException("Utility class");
    }

    /**
     * Parses a date string in the format {@code yyyy-MM-dd}.
     *
     * @param input the input string
     * @return the parsed date
     * @throws InvalidDateException if the input is null, empty or has the wrong format
     */
    public static LocalDate parseDate(String input) {
        if (input == null || input.isBlank()) {
            throw new InvalidDateException("Date cannot be null or empty.");
        }
        try {
            return LocalDate.parse(input.strip(), DATE_FORMAT);
        }
        catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date format. Expected yyyy-MM-dd.", e);
        }
    }

    /**
     * Parses a time string in the format {@code HH:mm}.
     *
     * @param input the input string
     * @return the parsed time
     * @throws InvalidTimeException if the input is null, empty or has the wrong format
     */
    public static LocalTime parseTime(String input) {
        if (input == null || input.isBlank()) {
            throw new InvalidTimeException("Time cannot be null or empty.");
        }
        try {
            return LocalTime.parse(input.strip(), TIME_FORMAT);
        }
        catch (DateTimeParseException e) {
            throw new InvalidTimeException("Invalid time format. Expected HH:mm.", e);
        }
    }

    /**
     * Formats a date as {@code yyyy-MM-dd}.
     *
     * @param date the date to format
     * @return the formatted string
     * @throws InvalidDateException if the date is null
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            throw new InvalidDateException("Date cannot be null.");
        }
        return DATE_FORMAT.format(date);
    }

    /**
     * Formats a time as {@code HH:mm}.
     *
     * @param time the time to format
     * @return the formatted string
     * @throws InvalidTimeException if the time is null
     */
    public static String formatTime(LocalTime time) {
        if (time == null) {
            throw new InvalidTimeException("Time cannot be null.");
        }
        return TIME_FORMAT.format(time);
    }
}
