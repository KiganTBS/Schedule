package com.example.schedule.Session;

public class Session {
    String dateExam;
    String timeExam;
    String subject;
    String format;
    String pathName;

    public Session(
            String dateExam,
            String timeExam,
            String professorExam,
            String format,
            String pathName)
    {
        this.dateExam = dateExam;
        this.timeExam = timeExam;
        this.subject = professorExam;
        this.format = format;
        this.pathName = pathName;
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

    public String getPathName() {
        return pathName;
    }
}
