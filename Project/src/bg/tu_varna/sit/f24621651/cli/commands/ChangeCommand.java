package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.EventNotFoundException;
import bg.tu_varna.sit.f24621651.exception.EventOverlapException;
import bg.tu_varna.sit.f24621651.exception.HolidayException;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.exception.InvalidTimeException;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChangeCommand implements Command {

    private final CalendarService service;

    public ChangeCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 4) {
            System.out.println("Usage: change <date> <starttime> <option> <value>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            LocalTime startTime = DateTimeParser.parseTime(args[1]);
            String option = args[2];
            String newValue = args[3];
            service.getCalendar().change(date, startTime, option, newValue);
            System.out.println("Event updated successfully.");
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("Invalid time: " + e.getMessage());
        } catch (EventNotFoundException e) {
            System.out.println("Event not found: " + e.getMessage());
        } catch (EventOverlapException e) {
            System.out.println("Event overlaps with existing one: " + e.getMessage());
        } catch (HolidayException e) {
            System.out.println("Cannot change on holiday: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid option: " + e.getMessage());
        }
    }
}
