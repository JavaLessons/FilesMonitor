package slogger;

public enum SLoggerPriority {
    SLoggerPriorityDebug(0),
    SLoggerPriorityInfo(1),
    SLoggerPriorityError(2);

    private int levelOfPriority;

    SLoggerPriority(int level) {
        this.levelOfPriority = level;
    }

    public int getLevelOfPriority() {
        return levelOfPriority;
    }

    public boolean isPriorityEqualOrHigher(SLoggerPriority testPriority) {
        return (levelOfPriority >= testPriority.getLevelOfPriority());
    }
};