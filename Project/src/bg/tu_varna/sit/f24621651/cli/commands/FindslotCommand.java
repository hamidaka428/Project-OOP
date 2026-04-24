package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;

public class FindslotCommand implements Command {

    private final CalendarService service;

    public FindslotCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: findslot <fromdate> <hours>");
            return;
        }

        try {
            LocalDate fromDate = DateTimeParser.parseDate(args[0]);
            int hours = Integer.parseInt(args[1]);
            LocalDate result = service.getCalendar().findslot(fromDate, hours);

            if (result == null) {
                System.out.println("No free slot found.");
            } else {
                System.out.println("Free slot found at " + result);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Hours must be a number.");
        }
    }
}
