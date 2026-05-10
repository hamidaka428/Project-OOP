package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.model.Calendar;

/**
 * Class for closing the currently opened file and clears the calendar.
 */
public class CloseCommand implements Command {

    private final CalendarService service;

    /**
     * Constructs a {@code CloseCommand} with the given service.
     *
     * @param service the calendar service to reset
     */
    public CloseCommand(CalendarService service) {
        this.service = service;
    }

    /**
     * Executes the close command.
     *
     * @param args the command arguments (none required here)
     */
    @Override
    public void execute(String[] args) {
        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        String filename = service.getCurrentFile();
        service.setCalendar(new Calendar());
        service.setCurrentFile(null);
        service.setFileOpened(false);
        System.out.println("Successfully closed " + filename);
    }
}
