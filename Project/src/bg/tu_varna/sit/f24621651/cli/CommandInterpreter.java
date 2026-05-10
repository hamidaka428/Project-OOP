package bg.tu_varna.sit.f24621651.cli;

import bg.tu_varna.sit.f24621651.cli.commands.AgendaCommand;
import bg.tu_varna.sit.f24621651.cli.commands.BookCommand;
import bg.tu_varna.sit.f24621651.cli.commands.BusydaysCommand;
import bg.tu_varna.sit.f24621651.cli.commands.ChangeCommand;
import bg.tu_varna.sit.f24621651.cli.commands.CloseCommand;
import bg.tu_varna.sit.f24621651.cli.commands.ExitCommand;
import bg.tu_varna.sit.f24621651.cli.commands.FindCommand;
import bg.tu_varna.sit.f24621651.cli.commands.FindslotCommand;
import bg.tu_varna.sit.f24621651.cli.commands.FindslotwithCommand;
import bg.tu_varna.sit.f24621651.cli.commands.HelpCommand;
import bg.tu_varna.sit.f24621651.cli.commands.HolidayCommand;
import bg.tu_varna.sit.f24621651.cli.commands.MergeCommand;
import bg.tu_varna.sit.f24621651.cli.commands.OpenCommand;
import bg.tu_varna.sit.f24621651.cli.commands.SaveAsCommand;
import bg.tu_varna.sit.f24621651.cli.commands.SaveCommand;
import bg.tu_varna.sit.f24621651.cli.commands.UnbookCommand;
import bg.tu_varna.sit.f24621651.core.CalendarService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Reads commands from the standard input and dispatches them to the
 * appropriate {@link Command} implementation.
 */
public class CommandInterpreter {

    private final Map<String, Command> commands;
    private final CalendarService service;
    private boolean running;

    /**
     * Creates a new interpreter and registers all supported commands.
     */
    public CommandInterpreter() {
        this.commands = new HashMap<>();
        this.service = new CalendarService();
        this.running = true;
        registerCommands();
    }

    /**
     * Adds all command implementations to the registry.
     */
    private void registerCommands() {
        commands.put("open", new OpenCommand(service));
        commands.put("close", new CloseCommand(service));
        commands.put("save", new SaveCommand(service));
        commands.put("saveas", new SaveAsCommand(service));
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand(this));

        commands.put("book", new BookCommand(service));
        commands.put("unbook", new UnbookCommand(service));
        commands.put("agenda", new AgendaCommand(service));
        commands.put("change", new ChangeCommand(service));
        commands.put("find", new FindCommand(service));
        commands.put("holiday", new HolidayCommand(service));
        commands.put("busydays", new BusydaysCommand(service));
        commands.put("findslot", new FindslotCommand(service));
        commands.put("findslotwith", new FindslotwithCommand(service));
        commands.put("merge", new MergeCommand(service));
    }

    /**
     * Stops the input loop on the next iteration.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Runs the read-eval-print loop until {@link #stop()} is called.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Calendar application started. Type 'help' for available commands.");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = parseInput(input);
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
                System.out.println("Unknown command: " + commandName + ". Type 'help' to see available commands.");
                continue;
            }

            command.execute(args);
        }

        scanner.close();
    }

    /**
     * Splits the input line into tokens, keeping double-quoted parts together.
     *
     * @param input the raw input line
     * @return an array of tokens
     */
    private String[] parseInput(String input) {
        List<String> tokens = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '"') {
                insideQuotes = !insideQuotes;
            } else if (c == ' ' && !insideQuotes) {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            tokens.add(current.toString());
        }

        return tokens.toArray(new String[0]);
    }
}
