package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.EventNotFoundException;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;

public class UnbookCommand implements Command {

    private final CalendarService service;

    public UnbookCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 3) {
            System.out.println("Usage: unbook <date> <starttime> <endtime>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            LocalTime startTime = DateTimeParser.parseTime(args[1]);
            LocalTime endTime = DateTimeParser.parseTime(args[2]);
            service.getCalendar().unbook(date, startTime, endTime);
            System.out.println("Event removed successfully.");
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("Invalid time: " + e.getMessage());
        } catch (EventNotFoundException e) {
            System.out.println("Event not found: " + e.getMessage());
        }
    }
}
