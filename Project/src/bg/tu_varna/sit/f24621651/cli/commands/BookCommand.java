package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.EventOverlapException;
import bg.tu_varna.sit.f24621651.exception.HolidayException;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class for booking a new event in the calendar.
 */
public class BookCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code BookCommand} with the given service.
     *
     * @param service the calendar service to update
     */
    public BookCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the book command.
     *
     * @param args the command arguments (date, start time, end time, name, note)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 5) {
            System.out.println("Usage: book <date> <starttime> <endtime> <name> <note>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            LocalTime startTime = DateTimeParser.parseTime(args[1]);
            LocalTime endTime = DateTimeParser.parseTime(args[2]);
            String name = args[3];

            StringBuilder noteBuilder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                if (i > 4) {
                    noteBuilder.append(" ");
                }
                noteBuilder.append(args[i]);
            }
            String note = noteBuilder.toString();

            service.getCalendar().book(date, startTime, endTime, name, note);
            System.out.println("Event booked successfully.");
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("Invalid time: " + e.getMessage());
        } catch (HolidayException e) {
            System.out.println("Cannot book on holiday: " + e.getMessage());
        } catch (EventOverlapException e) {
            System.out.println("Event overlaps with existing one: " + e.getMessage());
        }
    }
}
