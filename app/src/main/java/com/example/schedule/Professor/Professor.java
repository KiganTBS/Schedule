package com.example.schedule.Professor;

public class Professor {
    String name;
    String subject;
    String subjType;

    public Professor(String subject, String name, String subjType) {
        this.name = name;
        this.subject = subject;
        this.subjType = subjType;
    }

    public Professor() {
    }


    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubjType() { return subjType; }

}
