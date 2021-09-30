package com.example.schedule.Professor;

public class Professor {
    String nameProfessor;
    String nameSubject;
    String typeOfSubject;

    public Professor(String nameSubject,String nameProfessor,  String typeOfSubject) {
        this.nameProfessor = nameProfessor;
        this.nameSubject = nameSubject;
        this.typeOfSubject = typeOfSubject;
    }

    public Professor() {
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

    public String getTypeOfSubject() { return typeOfSubject; }

    public void setTypeOfSubject(String typeOfSubject) { this.typeOfSubject = typeOfSubject; }
}
