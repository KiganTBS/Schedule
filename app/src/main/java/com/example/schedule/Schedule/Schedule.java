package com.example.schedule.Schedule;

public class Schedule {
    String timeBeginningLesson;
    String timeEndLesson;
    String nameLesson;
    String typeLesson;
    String typeOfPresence;
    String nameProfessor;

    public Schedule(String timeBeginningLesson, String timeEndLesson, String nameLesson, String typeLesson, String typeOfPresence, String nameProfessor) {
        this.timeBeginningLesson = timeBeginningLesson;
        this.timeEndLesson = timeEndLesson;
        this.nameLesson = nameLesson;
        this.typeLesson = typeLesson;
        this.typeOfPresence = typeOfPresence;
        this.nameProfessor = nameProfessor;
    }

    public Schedule() {}

    public String getTimeBeginningLesson() {
        return timeBeginningLesson;
    }

    public void setTimeBeginningLesson(String timeBeginningLesson) {
        this.timeBeginningLesson = timeBeginningLesson;
    }

    public String getTimeEndLesson() {
        return timeEndLesson;
    }

    public void setTimeEndLesson(String timeEndLesson) {
        this.timeEndLesson = timeEndLesson;
    }

    public String getNameLesson() {
        return nameLesson;
    }

    public void setNameLesson(String nameLesson) {
        this.nameLesson = nameLesson;
    }

    public String getTypeLesson() {
        return typeLesson;
    }

    public void setTypeLesson(String typeLesson) {
        this.typeLesson = typeLesson;
    }

    public String getTypeOfPresence() {
        return typeOfPresence;
    }

    public void setTypeOfPresence(String typeOfPresence) {
        this.typeOfPresence = typeOfPresence;
    }

    public String getNameProfessor() {
        return nameProfessor;
    }

    public void setNameProfessor(String nameProfessor) {
        this.nameProfessor = nameProfessor;
    }
}
