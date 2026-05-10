package bg.tu_varna.sit.f24621651.cli;

/**
 * Common interface for all commands in the calendar CLI.
 */
public interface Command {

    /**
     * Executes the command with the given arguments.
     *
     * @param args the command arguments
     */
    void execute(String[] args);
}
