package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for printing how busy each day of the week is in a given range.
 */
public class BusydaysCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code BusydaysCommand} with the given service.
     *
     * @param service the calendar service to read from
     */
    public BusydaysCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the busydays command.
     *
     * @param args the command arguments (from date, to date)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: busydays <from> <to>");
            return;
        }

        try {
            LocalDate from = DateTimeParser.parseDate(args[0]);
            LocalDate to = DateTimeParser.parseDate(args[1]);
            List<String> busyDays = service.getCalendar().busydays(from, to);

            if (busyDays.isEmpty()) {
                System.out.println("No busy days in this range.");
                return;
            }

            System.out.println("Busy days (sorted by hours):");
            for (String dayInfo : busyDays) {
                System.out.println(dayInfo);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }
}
