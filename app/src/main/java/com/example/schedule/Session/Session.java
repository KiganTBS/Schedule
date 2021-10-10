package com.example.schedule.Session;

public class Session {
    String dateExam;
    String timeExam;
    String subject;
    String format;

    public Session(String dateExam, String timeExam, String professorExam, String format) {
        this.dateExam = dateExam;
        this.timeExam = timeExam;
        this.subject = professorExam;
    }

    public Session() {
    }

    public String getDateExam() {
        return dateExam;
    }

    public String getTimeExam() {
        return timeExam;
    }

    public String getSubject() {
        return subject;
    }

    public String getFormat() {
        return format;
    }
}
