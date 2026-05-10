package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.cli.CommandInterpreter;

/**
 * Class for stopping the application.
 */
public class ExitCommand implements Command {

    private final CommandInterpreter interpreter;

    /**
     * Constructs an {@code ExitCommand} with the given interpreter.
     *
     * @param interpreter the interpreter whose loop will be stopped
     */
    public ExitCommand(CommandInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    /**
     * Executes the exit command.
     *
     * @param args the command arguments (none required here)
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Exiting the program...");
        interpreter.stop();
    }
}
