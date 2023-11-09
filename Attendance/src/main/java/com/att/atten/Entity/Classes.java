package com.att.atten.Entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


@Entity
public class Classes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    UUID id;

    @Column(nullable = false)
    String className;
    @Column(name = "Semester",nullable = false)
    String sem;

    
    @Column(nullable = false)
    String date;


    @ManyToOne
    @JoinColumn(nullable = false)
    adminEn adminEn;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    List<Total> totals;

    

    

    public List<Attendence> getAttendence() {
        return attendence;
    }

    

    public void setAttendence(List<Attendence> attendence) {
        this.attendence = attendence;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "classes",orphanRemoval = true)
    List<Attendence> attendence;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "classes",orphanRemoval = true)
    List<studentEn> studentEn;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUID.randomUUID();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public adminEn getAdminEn() {
        return adminEn;
    }

    public void setAdminEn(adminEn adminEn) {
        this.adminEn = adminEn;
    }

    public List<studentEn> getStudentEn() {
        return studentEn;
    }

    public void setStudentEn(List<studentEn> studentEn) {
        this.studentEn = studentEn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public List<Total> getTotals() {
        return totals;
    }



    public void setTotals(List<Total> totals) {
        this.totals = totals;
    }


    


    
}
