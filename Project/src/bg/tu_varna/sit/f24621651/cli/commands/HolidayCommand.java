package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;

/**
 * Class for marking a date as a non-working day.
 */
public class HolidayCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code HolidayCommand} with the given service.
     *
     * @param service the calendar service to update
     */
    public HolidayCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the holiday command.
     *
     * @param args the command arguments (the date)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: holiday <date>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            service.getCalendar().holiday(date);
            System.out.println("Holiday set for " + args[0]);
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }
}
