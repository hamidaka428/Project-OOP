package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.exception.InvalidDateException;
import bg.tu_varna.sit.f24621651.model.Calendar;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;

public class FindslotwithCommand implements Command {

    private final CalendarService service;

    public FindslotwithCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 3) {
            System.out.println("Usage: findslotwith <fromdate> <hours> <otherfile>");
            return;
        }

        try {
            LocalDate from = DateTimeParser.parseDate(args[0]);
            int hours = Integer.parseInt(args[1]);
            Calendar other = service.getFileManager().load(args[2]);
            LocalDate result = service.getCalendar().findslotwith(from, hours, other);

            if (result == null) {
                System.out.println("No common free slot found.");
            } else {
                System.out.println("Common free slot found on: " + result);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Hours must be a number.");
        } catch (Exception e) {
            System.out.println("Error loading other calendar: " + e.getMessage());
        }
    }
}
