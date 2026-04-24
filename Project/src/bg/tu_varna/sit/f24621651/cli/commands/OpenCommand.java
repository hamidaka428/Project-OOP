package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.core.CalendarService;
import bg.tu_varna.sit.f24621651.model.Calendar;

public class OpenCommand implements Command {

    private final CalendarService service;

    public OpenCommand(CalendarService service) {
        this.service = service;
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: open <filename>");
            return;
        }

        if (service.isFileOpened()) {
            System.out.println("First close the current file.");
            return;
        }

        String filename = args[0];

        try {
            Calendar loaded = service.getFileManager().load(filename);
            service.setCalendar(loaded);
            service.setCurrentFile(filename);
            service.setFileOpened(true);
            System.out.println("Successfully opened " + filename);
        } catch (Exception e) {
            service.setCalendar(new Calendar());
            service.setCurrentFile(filename);
            service.setFileOpened(true);
            System.out.println("File not found, created new calendar: " + filename);
        }
    }
}
