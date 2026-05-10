package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.model.Calendar;
import bg.tu_varna.sit.f24621651.model.ConflictResolver;
import bg.tu_varna.sit.f24621651.model.Event;

import java.util.Scanner;

/**
 * Class for merging another calendar into the current one.
 * Asks the user how to resolve overlapping events.
 */
public class MergeCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code MergeCommand} with the given service.
     *
     * @param service the calendar service to update
     */
    public MergeCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the merge command.
     *
     * @param args the command arguments (the file to merge in)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: merge <otherfile>");
            return;
        }

        try {
            Calendar other = service.getFileManager().load(args[0]);
            final Scanner scanner = new Scanner(System.in);

            ConflictResolver resolver = new ConflictResolver() {
                @Override
                public boolean resolveConflict(Event currentEvent, Event incomingEvent) {
                    System.out.println("Conflict found:");
                    System.out.println("  Current:  " + currentEvent);
                    System.out.println("  Incoming: " + incomingEvent);
                    System.out.println("Keep incoming event? (yes/no):");
                    String answer = scanner.nextLine();
                    return answer.equalsIgnoreCase("yes");
                }
            };

            service.getCalendar().merge(other, resolver);
            System.out.println("Calendar merged successfully.");
        } catch (Exception e) {
            System.out.println("Error during merge: " + e.getMessage());
        }
    }
}
