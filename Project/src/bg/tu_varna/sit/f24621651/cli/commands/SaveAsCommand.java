package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;

public class SaveAsCommand implements Command {

    private final CalendarService service;

    public SaveAsCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: save as <filename>");
            return;
        }

        if (!service.isFileOpened()) {
            System.out.println("No file is currently opened.");
            return;
        }

        try {
            service.getFileManager().save(service.getCalendar(), args[0]);
            System.out.println("Successfully saved " + args[0]);
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
