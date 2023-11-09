package com.att.atten.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Attendence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false)
    String date;

    String teacherdate;

    String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    studentEn studentEn;
    @Column(nullable = false)
    int status;

    @ManyToOne
    @JoinColumn(nullable = false)
    Classes classes;

    public String getTeacherdate() {
        return teacherdate;
    }

    public void setTeacherdate(String teacherdate) {
        this.teacherdate = teacherdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public studentEn getStudentEn() {
        return studentEn;
    }

    public void setStudentEn(studentEn studentEn) {
        this.studentEn = studentEn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Attendence(int id, String date, com.att.atten.Entity.studentEn studentEn, int status) {
        this.id = id;
        this.date = date;
        this.studentEn = studentEn;
        this.status = status;
    }

    public Attendence() {
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
