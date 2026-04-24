package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.model.Calendar;

public class CloseCommand implements Command {

    private final CalendarService service;

    public CloseCommand(CalendarService service) {
        this.service = service;
    }

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
