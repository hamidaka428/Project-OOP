package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;
import bg.tu_varna.sit.f24621651.cli.CommandInterpreter;

public class ExitCommand implements Command {

    private final CommandInterpreter interpreter;

    public ExitCommand(CommandInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Exiting the program...");
        interpreter.stop();
    }
}
