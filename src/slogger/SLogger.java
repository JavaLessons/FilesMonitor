package slogger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SLogger {
    private String logClassName;
    private SLoggerPriority priority;

    /**
     * SLogger constructor
     * @param className
     */
    public SLogger(String className, SLoggerPriority priority) {
        logClassName = className;
        this.priority = priority;
    }

    public void logDebug(String debugInfoText) {
        if (SLoggerPriority.SLoggerPriorityDebug.isPriorityEqualOrHigher(priority)) {
            System.out.println(this.getMessageBasicString() + " Debug: " + debugInfoText);
        }
    }

    public void logError(String errorText) {
        if (SLoggerPriority.SLoggerPriorityError.isPriorityEqualOrHigher(priority)) {
            System.out.println(this.getMessageBasicString() + " Error: " + errorText);
        }
    }


    public void logInfo(String logText) {
        if (SLoggerPriority.SLoggerPriorityInfo.isPriorityEqualOrHigher(priority)) {
            System.out.println(this.getMessageBasicString() + " Information: " + logText);
        }
    }

    //Private methods
    private String getMessageBasicString() {
        return this.getCurrentTime() + " " + logClassName;
    }

    private String getCurrentTime() {
        Date currentDate = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate);
    }
}
