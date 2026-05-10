package bg.tu_varna.sit.f24621651.cli;

/**
 * Entry point of the calendar application.
 */
public class Main {

    /**
     * Starts the command interpreter.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        CommandInterpreter interpreter = new CommandInterpreter();
        interpreter.run();
    }
}
