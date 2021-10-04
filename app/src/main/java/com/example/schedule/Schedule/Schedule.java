package com.example.schedule.Schedule;

public class Schedule {
    String timeBegining;
    String timeEnd;
    String subject;
    String type;
    String format;
    String lecturer;

    public Schedule(String timeBegining, String timeEnd, String subject, String type, String format, String lecturer) {
        this.timeBegining = timeBegining;
        this.timeEnd = timeEnd;
        this.subject = subject;
        this.type = type;
        this.format = format;
        this.lecturer = lecturer;
    }

    public Schedule() {}

    public String getTimeBegining() {
        return timeBegining;
    }

    public void setTimeBegining(String timeBegining) {
        this.timeBegining = timeBegining;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }
}
