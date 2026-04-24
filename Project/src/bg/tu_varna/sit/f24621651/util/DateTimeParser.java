package bg.tu_varna.sit.f24621651.util;

import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeParser {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeParser() {
        throw new IllegalArgumentException("Utility class");
    }

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

    public static String formatDate(LocalDate date) {
        if (date == null) {
            throw new InvalidDateException("Date cannot be null.");
        }
        return DATE_FORMAT.format(date);
    }

    public static String formatTime(LocalTime time) {
        if (time == null) {
            throw new InvalidTimeException("Time cannot be null.");
        }
        return TIME_FORMAT.format(time);
    }
}