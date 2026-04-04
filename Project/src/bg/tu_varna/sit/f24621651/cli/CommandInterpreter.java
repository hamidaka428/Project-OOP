package bg.tu_varna.sit.f24621651.cli;

import bg.tu_varna.sit.f24621651.core.CalendarService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandInterpreter {

    private CalendarService calendarService;
    private Map<String, Command> commands;
    private boolean running;

    public CommandInterpreter() {
        this.calendarService = new CalendarService();
        this.commands = new HashMap<>();
        this.running = true;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("open", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.open(args);
            }
        });


        commands.put("close", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.close();
            }
        });

        commands.put("save", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.save();
            }
        });

        commands.put("saveas", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.saveas(args);
            }
        });

        commands.put("book", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.book(args);
            }
        });

        commands.put("unbook", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.unbook(args);
            }
        });

        commands.put("agenda", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.agenda(args);
            }
        });

        commands.put("change", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.change(args);
            }
        });

        commands.put("find", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.find(args);
            }
        });

        commands.put("holiday", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.holiday(args);
            }
        });

        commands.put("busydays", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.busydays(args);
            }
        });

        commands.put("findslot", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.findslot(args);
            }
        });

        commands.put("findslotwith", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.findslotwith(args);
            }
        });

        commands.put("merge", new Command() {
            @Override
            public void execute(String[] args) {
                calendarService.merge(args);
            }
        });

        commands.put("help", new Command() {
            @Override
            public void execute(String[] args) {
                printHelp();
            }
        });

        commands.put("exit", new Command() {
            @Override
            public void execute(String[] args) {
               exit();
            }
        });
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Calendar application started. Type 'help' for available commands.");

        while (running) {
            System.out.println("> ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split("\\s+");
            String commandName = parts[0].toLowerCase();

            String[] args = new String[parts.length - 1];
            for (int i = 1; i < parts.length; i++) {
                args[i - 1] = parts[i];
            }

            if (commandName.equals("save") && args.length > 0 && args[0].equalsIgnoreCase("as")) {
                commandName = "saveas";
                String[] newArgs = new String[args.length - 1];
                for (int i = 1; i < args.length; i++) {
                    newArgs[i - 1] = args[i];
                }
                args = newArgs;
            }

            Command command = commands.get(commandName);
            if (command == null) {
                System.out.println("Unknown command: " + commandName + ". Type 'help' to see available commands,");
                continue;
            }

            try {
                command.execute(args);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void printHelp() {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>           opens <file>");
        System.out.println("close                 closes currently opened file");
        System.out.println("save                  saves the curently open file");
        System.out.println("save as <file>        saves the curently open file in <file>");
        System.out.println("help                  prints this information");
        System.out.println("exit                  exits the program");
        System.out.println("book <date> <start> <end> <name> <note> books an event");
        System.out.println("unbook <date> <start> <end> removes an event");
        System.out.println("agenda <date>         lists events for a day");
        System.out.println("change <date> <start> <option> <value> changes event property");
        System.out.println("find <string>         finds event by text");
        System.out.println("holiday <date>        marks a day as holiday");
        System.out.println("busydays <from> <to>  shows busiest days");
        System.out.println("findslot <fromdate> <hours>  finds a free slot");
        System.out.println("findslotwith <fromdate> <hours> <files>   finds common free slot");
        System.out.println("merge <file>         merges calendars");
    }

    private void exit() {
        System.out.println("Exiting the program...");
        running = false;
    }
}
