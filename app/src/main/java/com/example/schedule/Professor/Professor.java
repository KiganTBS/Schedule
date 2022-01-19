package com.example.schedule.Professor;

public class Professor {
    String name;
    String subject;
    String subjType;
    String pathName;

    public Professor(String subject, String name, String subjType,
                     String pathName) {
        this.name = name;
        this.subject = subject;
        this.subjType = subjType;
        this.pathName = pathName;
    }
    public Professor() {}

    public String getName() {
        return name;
    }
    public String getSubject() {
        return subject;
    }
    public String getSubjType() {return subjType;}

    public String getPathName() {return pathName;}
}
