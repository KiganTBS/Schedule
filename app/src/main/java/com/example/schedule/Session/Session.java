package com.example.schedule.Session;

public class Session {
    String dateExam;
    String timeExam;
    String professorExam;

    public Session(String dateExam, String timeExam, String professorExam) {
        this.dateExam = dateExam;
        this.timeExam = timeExam;
        this.professorExam = professorExam;
    }

    public Session() {
    }

    public String getDateExam() {
        return dateExam;
    }

    public void setDateExam(String dateExam) {
        this.dateExam = dateExam;
    }

    public String getTimeExam() {
        return timeExam;
    }

    public void setTimeExam(String timeExam) {
        this.timeExam = timeExam;
    }

    public String getProfessorExam() {
        return professorExam;
    }

    public void setProfessorExam(String professorExam) {
        this.professorExam = professorExam;
    }
}
