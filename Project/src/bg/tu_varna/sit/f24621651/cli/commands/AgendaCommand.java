package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.model.Event;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for printing all events for a given day in chronological order.
 */
public class AgendaCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs an {@code AgendaCommand} with the given service.
     *
     * @param service the calendar service to read from
     */
    public AgendaCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the agenda command.
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
            System.out.println("Usage: agenda <date>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            List<Event> events = service.getCalendar().agenda(date);

            if (events.isEmpty()) {
                System.out.println("No events on this day.");
                return;
            }

            for (Event e : events) {
                System.out.println(e);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }
}
