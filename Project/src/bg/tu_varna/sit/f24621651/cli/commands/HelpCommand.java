package bg.tu_varna.sit.f24621651.cli.commands;

import bg.tu_varna.sit.f24621651.cli.Command;

/**
 * Class for printing the list of supported commands.
 */
public class HelpCommand implements Command {

    /**
     * Executes the help command.
     *
     * @param args the command arguments (none required here)
     */
    @Override
    public void execute(String[] args) {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>                              opens <file>");
        System.out.println("close                                    closes currently opened file");
        System.out.println("save                                     saves the currently open file");
        System.out.println("save as <file>                           saves the currently open file in <file>");
        System.out.println("help                                     prints this information");
        System.out.println("exit                                     exits the program");
        System.out.println("book <date> <start> <end> <n> <note>     books an event");
        System.out.println("unbook <date> <start> <end>              removes an event");
        System.out.println("agenda <date>                            lists events for a day");
        System.out.println("change <date> <start> <option> <value>   changes an event property");
        System.out.println("find <string>                            finds events by text");
        System.out.println("holiday <date>                           marks a day as holiday");
        System.out.println("busydays <from> <to>                     shows busy days of the week");
        System.out.println("findslot <fromdate> <hours>              finds a free slot");
        System.out.println("findslotwith <fromdate> <hours> <file>   finds common free slot");
        System.out.println("merge <file>                             merges another calendar into the current one");
    }
}
