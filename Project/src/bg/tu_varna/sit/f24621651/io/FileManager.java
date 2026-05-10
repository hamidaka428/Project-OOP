package bg.tu_varna.sit.f24621651.io;

import bg.tu_varna.sit.f24621651.exception.FileFormatException;
import bg.tu_varna.sit.f24621651.model.Calendar;
import bg.tu_varna.sit.f24621651.model.Event;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * Reads and writes a {@link Calendar} from/to a simple XML-like text file.
 */
public class FileManager {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Loads a calendar from the given file.
     *
     * @param filename the file path
     * @return the parsed calendar
     * @throws IOException         if the file does not exist or cannot be read
     * @throws FileFormatException if the file content is malformed
     */
    public Calendar load(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IOException("File not found: " + filename);
        }

        Calendar calendar = new Calendar();
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("<event>")) {
                readEvent(reader, calendar);
            } else if (line.startsWith("<holiday>")) {
                readHoliday(line, calendar);
            }
        }

        reader.close();
        return calendar;
    }

    /**
     * Reads the body of one {@code <event>} block and adds the event to the
     * calendar.
     *
     * @param reader   the reader, positioned just after the opening tag
     * @param calendar the calendar to append to
     * @throws IOException         on I/O failure
     * @throws FileFormatException on missing lines or malformed values
     */
    private void readEvent(BufferedReader reader, Calendar calendar) throws IOException {
        String dateLine = reader.readLine();
        String startLine = reader.readLine();
        String endLine = reader.readLine();
        String nameLine = reader.readLine();
        String noteLine = reader.readLine();
        String closingLine = reader.readLine();

        if (dateLine == null || startLine == null || endLine == null
                || nameLine == null || noteLine == null) {
            throw new FileFormatException("Incomplete event data in file.");
        }

        try {
            LocalDate date = LocalDate.parse(getTagValue(dateLine, "date"), DATE_FORMAT);
            LocalTime startTime = LocalTime.parse(getTagValue(startLine, "starttime"), TIME_FORMAT);
            LocalTime endTime = LocalTime.parse(getTagValue(endLine, "endtime"), TIME_FORMAT);
            String name = getTagValue(nameLine, "n");
            String note = getTagValue(noteLine, "note");

            calendar.book(date, startTime, endTime, name, note);
        } catch (DateTimeParseException e) {
            throw new FileFormatException("Invalid date/time format in file: " + e.getMessage());
        } catch (FileFormatException e) {
            throw e;
        } catch (Exception e) {
            throw new FileFormatException("Error reading event: " + e.getMessage());
        }
    }

    /**
     * Parses a single-line {@code <holiday>} marker.
     *
     * @param line     the line containing the tag
     * @param calendar the calendar to mark
     */
    private void readHoliday(String line, Calendar calendar) {
        try {
            String dateStr = getTagValue(line, "holiday");
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMAT);
            calendar.holiday(date);
        } catch (DateTimeParseException e) {
            throw new FileFormatException("Invalid holiday date in file.");
        }
    }

    /**
     * Extracts the text between {@code <tag>} and {@code </tag>} from a line.
     *
     * @param line the input line
     * @param tag  the tag name (without angle brackets)
     * @return the trimmed value between the tags
     * @throws FileFormatException if the tag is missing
     */
    private String getTagValue(String line, String tag) {
        line = line.trim();
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";

        int startIndex = line.indexOf(openTag);
        int endIndex = line.indexOf(closeTag);

        if (startIndex == -1 || endIndex == -1) {
            throw new FileFormatException("Missing tag: " + tag);
        }

        return line.substring(startIndex + openTag.length(), endIndex).trim();
    }

    /**
     * Writes the calendar to the given file, overwriting any existing content.
     *
     * @param calendar the calendar to save
     * @param filename the destination file path
     * @throws IOException on I/O failure
     */
    public void save(Calendar calendar, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        writer.write("<calendar>");
        writer.newLine();

        Map<LocalDate, List<Event>> allEvents = calendar.getAllEvents();

        for (Map.Entry<LocalDate, List<Event>> entry : allEvents.entrySet()) {
            for (Event event : entry.getValue()) {
                writer.write("  <event>");
                writer.newLine();
                writer.write("    <date>" + event.getDate().format(DATE_FORMAT) + "</date>");
                writer.newLine();
                writer.write("    <starttime>" + event.getStartTime().format(TIME_FORMAT) + "</starttime>");
                writer.newLine();
                writer.write("    <endtime>" + event.getEndTime().format(TIME_FORMAT) + "</endtime>");
                writer.newLine();
                writer.write("    <name>" + event.getName() + "</name>");
                writer.newLine();
                writer.write("    <note>" + event.getNote() + "</note>");
                writer.newLine();
                writer.write("  </event>");
                writer.newLine();
            }
        }

        List<LocalDate> holidays = calendar.getHolidays();
        for (LocalDate holiday : holidays) {
            writer.write("  <holiday>" + holiday.format(DATE_FORMAT) + "</holiday>");
            writer.newLine();
        }

        writer.write("</calendar>");
        writer.newLine();
        writer.close();
    }
}
