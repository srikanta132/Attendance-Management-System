package com.att.atten.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Total {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    int total;

    int present;

    @ManyToOne
    Classes classes;

    @ManyToOne
    studentEn studentEn;

    @ManyToOne
    adminEn adminEn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public studentEn getStudentEn() {
        return studentEn;
    }

    public void setStudentEn(studentEn studentEn) {
        this.studentEn = studentEn;
    }

    public adminEn getAdminEn() {
        return adminEn;
    }

    public void setAdminEn(adminEn adminEn) {
        this.adminEn = adminEn;
    }

    
    
}
