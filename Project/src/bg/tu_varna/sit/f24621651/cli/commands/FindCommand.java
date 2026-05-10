package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.model.Event;

import java.util.List;

/**
 * Class for searching events whose name or note contains a given string.
 */
public class FindCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code FindCommand} with the given service.
     *
     * @param service the calendar service to read from
     */
    public FindCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the find command.
     *
     * @param args the command arguments (the search string)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: find <string>");
            return;
        }

        List<Event> results = service.getCalendar().find(args[0]);

        if (results.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        for (Event e : results) {
            System.out.println(e);
        }
    }
}
