package com.example.schedule.Professor;

public class Professor {
    String nameProfessor;
    String nameSubject;

    public Professor(String nameProfessor, String nameSubject) {
        this.nameProfessor = nameProfessor;
        this.nameSubject = nameSubject;
    }

    public String getNameProfessor() {
        return nameProfessor;
    }

    public void setNameProfessor(String nameProfessor) {
        this.nameProfessor = nameProfessor;
    }

    public String getNameSubject() {
        return nameSubject;
    }

    public void setNameSubject(String nameSubject) {
        this.nameSubject = nameSubject;
    }
}
