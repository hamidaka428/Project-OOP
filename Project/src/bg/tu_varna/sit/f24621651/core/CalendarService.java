package bg.tu_varna.sit.f24621651.core;

import bg.tu_varna.sit.f24621651.io.FileManager;
import bg.tu_varna.sit.f24621651.model.Calendar;

public class CalendarService {

    private Calendar calendar;
    private String currentFile;
    private boolean fileOpened;
    private final FileManager fileManager;

    public CalendarService() {
        this.calendar = new Calendar();
        this.fileOpened = false;
        this.fileManager = new FileManager();
        this.currentFile = null;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(String currentFile) {
        this.currentFile = currentFile;
    }

    public boolean isFileOpened() {
        return fileOpened;
    }

    public void setFileOpened(boolean fileOpened) {
        this.fileOpened = fileOpened;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
