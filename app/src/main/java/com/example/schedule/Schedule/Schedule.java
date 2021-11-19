package com.example.schedule.Schedule;

public class Schedule {
    String timeBegining;
    String timeEnd;
    String subject;
    String type;
    String format;
    String lecturer;
    String pathName;

    public Schedule(String timeBegining, String timeEnd, String subject, String type, String format, String lecturer, String pathName) {
        this.timeBegining = timeBegining;
        this.timeEnd = timeEnd;
        this.subject = subject;
        this.type = type;
        this.format = format;
        this.lecturer = lecturer;
        this.pathName = pathName;
    }

    public Schedule() {
    }

    public String getTimeBegining() {
        return timeBegining;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getPathName() { return pathName; }
}
