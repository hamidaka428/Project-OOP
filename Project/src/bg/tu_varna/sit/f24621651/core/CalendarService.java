package bg.tu_varna.sit.f24621651.core;

import bg.tu_varna.sit.f24621651.io.FileManager;
import bg.tu_varna.sit.f24621651.model.Calendar;

/**
 * Holds the current calendar, the path of the opened file and the file
 * manager. Shared by all commands.
 */
public class CalendarService {

    private Calendar calendar;
    private String currentFile;
    private boolean fileOpened;
    private final FileManager fileManager;

    /**
     * Constructs a new service with an empty calendar and no opened file.
     */
    public CalendarService() {
        this.calendar = new Calendar();
        this.fileOpened = false;
        this.fileManager = new FileManager();
        this.currentFile = null;
    }

    /**
     * Returns the current in-memory calendar.
     *
     * @return the calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * Replaces the current calendar.
     *
     * @param calendar the new calendar
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Returns the path of the currently opened file.
     *
     * @return the file path, or {@code null} if no file is opened
     */
    public String getCurrentFile() {
        return currentFile;
    }

    /**
     * Sets the path of the currently opened file.
     *
     * @param currentFile the new file path
     */
    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    /**
     * Returns whether a file is currently opened.
     *
     * @return {@code true} if a file is opened
     */
    public boolean isFileOpened() {
        return fileOpened;
    }

    /**
     * Sets the file-opened flag.
     *
     * @param fileOpened the new flag value
     */
    public void setFileOpened(boolean fileOpened) {
        this.fileOpened = fileOpened;
    }

    /**
     * Returns the file manager used for loading and saving.
     *
     * @return the file manager
     */
    public FileManager getFileManager() {
        return fileManager;
    }
}
