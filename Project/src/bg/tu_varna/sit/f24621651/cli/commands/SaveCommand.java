package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;

/**
 * Class for saving the calendar to the currently opened file.
 */
public class SaveCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code SaveCommand} with the given service.
     *
     * @param service the calendar service to read from
     */
    public SaveCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the save command.
     *
     * @param args the command arguments (none required here)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        try {
            service.getFileManager().save(service.getCalendar(), service.getCurrentFile());
            System.out.println("Successfully saved " + service.getCurrentFile());
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
