package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.model.Event;

import java.util.List;

public class FindCommand implements Command {

    private final CalendarService service;

    public FindCommand(CalendarService service) {
        this.service = service;
    }

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
