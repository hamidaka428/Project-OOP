package bg.tu_varna.sit.f24621651.core;

import bg.tu_varna.sit.f24621651.exception.*;
import bg.tu_varna.sit.f24621651.exception.NumberFormatException;
import bg.tu_varna.sit.f24621651.io.FileManager;
import bg.tu_varna.sit.f24621651.model.Calendar;
import bg.tu_varna.sit.f24621651.model.ConflictResolver;
import bg.tu_varna.sit.f24621651.model.Event;
import bg.tu_varna.sit.f24621651.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class CalendarService {

    private Calendar calendar;
    private String currentFile;
    private boolean fileOpened;
    private FileManager fileManager;

    public CalendarService() {
        this.calendar = new Calendar();
        this.fileOpened = false;
        this.fileManager = new FileManager();
        this.currentFile = null;
    }

    public void open(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: open <filename>");
            return;
        }

        if (fileOpened) {
            System.out.println("First close the current file.");
            return;
        }

        String filename = args[0];

        try{
            calendar = fileManager.load(filename);
            currentFile = filename;
            fileOpened = true;
            System.out.println("Successfully opened " + filename);
        } catch (Exception e) {
            calendar = new Calendar();
            currentFile = filename;
            fileOpened = true;
            System.out.println("File not found, created new calendar: " + filename);
        }
    }

    public void close() {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        String filename = currentFile;
        calendar = new Calendar();
        currentFile = null;
        fileOpened = false;
        System.out.println("Successfully closed " + filename);
    }

    public void save() {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        try {
            fileManager.save(calendar, currentFile);
            System.out.println("Successfully saved " + currentFile);
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void saveas(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: save as  <filename>");
            return;
        }

        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        try {
            fileManager.save(calendar, args[0]);
            System.out.println("Successfully saved " + args[0]);
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void book(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 5) {
            System.out.println("Usage: book <date> <starttime> <endtime> <name> <note>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            LocalTime startTime = DateTimeParser.parseTime(args[1]);
            LocalTime endTime = DateTimeParser.parseTime(args[2]);
            String name = args[3];

            StringBuilder noteBuilder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                if (i > 4) {
                    noteBuilder.append(" ");
                }
                noteBuilder.append(args[i]);
            }
            String note = noteBuilder.toString();

            calendar.book(date, startTime, endTime, name, note);
            System.out.println("Event booked successfully.");
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("Invalid time: " + e.getMessage());
        } catch (HolidayException e) {
            System.out.println("Cannot book on holiday: " + e.getMessage());
        } catch (EventOverlapException e) {
            System.out.println("Event overlaps with existing one: " + e.getMessage());
        }
    }

    public void unbook(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 3) {
            System.out.println("Usage: unbook <date> <starttime> <endtime>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            LocalTime startTime = DateTimeParser.parseTime(args[1]);
            LocalTime endTime = DateTimeParser.parseTime(args[2]);
            calendar.unbook(date, startTime, endTime);
            System.out.println("Event removed successfully.");
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("Invalid time: " + e.getMessage());
        } catch (EventNotFoundException e) {
            System.out.println("Event not found: " + e.getMessage());
        }
    }

    public void agenda(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: agenda <date>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            List<Event> events = calendar.agenda(date);

            if (events.isEmpty()) {
                System.out.println("No events on this day.");
                return;
            }

            for (Event e : events) {
                System.out.println(e);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }

    public void change(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 4) {
            System.out.println("Usage: change <date> starttime> <option> <value>");
            return;
        }

        try {
            LocalDate date = DateTimeParser.parseDate(args[0]);
            LocalTime startTime = DateTimeParser.parseTime(args[1]);
            String option = args[2];
            String newValue = args[3];
            calendar.change(date, startTime, option, newValue);
            System.out.println("Event updated successfully.");
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("Invalid time: " + e.getMessage());
        } catch (EventNotFoundException e) {
            System.out.println("Event not found: " + e.getMessage());
        } catch (EventOverlapException e) {
            System.out.println("Event overlaps with existing one: " + e.getMessage());
        } catch (HolidayException e) {
            System.out.println("Cannot change on holiday: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid option: " + e.getMessage());
        }
    }

    public void find(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: find <string>");
            return;
        }

        List<Event> results = calendar.find(args[0]);

        if (results.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        for (Event e : results) {
            System.out.println(e);
        }
    }

    public void holiday(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: holiday <date>");
            return;
        }

        try{
            LocalDate date = DateTimeParser.parseDate(args[0]);
            calendar.holiday(date);
            System.out.println("Holiday set for " + args[0]);
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }

    public void busydays(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: busydays <from> <to>");
            return;
        }

        try {
            LocalDate from = DateTimeParser.parseDate(args[0]);
            LocalDate to = DateTimeParser.parseDate(args[1]);
            List<String> busyDays = calendar.busydays(from, to);

            if (busyDays.isEmpty()) {
                System.out.println("No busy days in this range.");
                return;
            }

            System.out.println("Busy days (sorted by hours):");
            for (String dayInfo : busyDays) {
                System.out.println(dayInfo);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        }
    }

    public void findslot(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: findslot <fromdate> <hours>");
            return;
        }

        try {
            LocalDate fromdate = DateTimeParser.parseDate(args[0]);
            int hours = Integer.parseInt(args[1]);
            LocalDate result = calendar.findslot(fromdate, hours);

            if (result == null) {
                System.out.println("No free slot found.");
            } else {
                System.out.println("Free slot found at " + result);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Hours must be a number.");
        }
    }

    public void findslotwith(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 3) {
            System.out.println("Usage: findslotwith <fromdate> <hours> <otherfile>");
            return;
        }

        try{
            LocalDate from = DateTimeParser.parseDate(args[0]);
            int hours = Integer.parseInt(args[1]);
            Calendar other = fileManager.load(args[2]);
            LocalDate result = calendar.findslotwith(from, hours, other);

            if (result == null) {
                System.out.println("No common free slot found.");
            } else {
                System.out.println("Common free slot found on: " + result);
            }
        } catch (InvalidDateException e) {
            System.out.println("Invalid date: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Hours must be a number.");
        } catch (Exception e) {
            System.out.println("Error loading other calendar: " + e.getMessage());
        }
    }

    public void merge(String[] args) {
        if (!fileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (args.length < 1) {
            System.out.println("Usage: merge <otherfile>");
            return;
        }

        try{
            Calendar other = fileManager.load(args[0]);
            Scanner scanner = new Scanner(System.in);

            ConflictResolver resolver = new ConflictResolver() {
                @Override
                public boolean resolveConflict(Event currentEvent, Event incomingEvent) {
                    System.out.println("Conflict found:");
                    System.out.println("  Current:  " + currentEvent);
                    System.out.println("  Incoming:  " + incomingEvent);
                    System.out.println("Keep incoming event? (yes/no):");
                    String answer = scanner.nextLine();
                    return answer.equalsIgnoreCase("yes");
                }
            };

            calendar.merge(other, resolver);
            System.out.println("Calendar merged successfully.");
        } catch (Exception e) {
            System.out.println("Error during merge: " + e.getMessage());
        }
    }
}
