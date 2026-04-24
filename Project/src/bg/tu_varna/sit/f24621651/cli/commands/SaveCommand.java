package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;

public class SaveCommand implements Command {

    private final CalendarService service;

    public SaveCommand(CalendarService service) {
        this.service = service;
    }

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
