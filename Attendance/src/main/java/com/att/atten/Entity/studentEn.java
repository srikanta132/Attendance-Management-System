package com.att.atten.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class studentEn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String dep;

    String sem;

    String roll;

    String contact_01;

    String contact_02;

    String father_name;

    String mother_name;

    String parents_contact;

    String address_01;

    String address_02;

    String email;

    @ManyToOne
    Classes classes;

    @OneToMany
    List<Total> totals;

    

    
    
    
    public String getContact_01() {
        return contact_01;
    }
    public void setContact_01(String contact_01) {
        this.contact_01 = contact_01;
    }
    public String getContact_02() {
        return contact_02;
    }
    public void setContact_02(String contact_02) {
        this.contact_02 = contact_02;
    }
    public String getFather_name() {
        return father_name;
    }
    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }
    public String getMother_name() {
        return mother_name;
    }
    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }
    public String getParents_contact() {
        return parents_contact;
    }
    public void setParents_contact(String parents_contact) {
        this.parents_contact = parents_contact;
    }
    public String getAddress_01() {
        return address_01;
    }
    public void setAddress_01(String address_01) {
        this.address_01 = address_01;
    }
    public String getAddress_02() {
        return address_02;
    }
    public void setAddress_02(String address_02) {
        this.address_02 = address_02;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDep() {
        return dep;
    }
    public void setDep(String dep) {
        this.dep = dep;
    }
    public studentEn(int id, String name, String dep) {
        this.id = id;
        this.name = name;
        this.dep = dep;
    }
    public studentEn() {
    }
    public Classes getClasses() {
        return classes;
    }
    public void setClasses(Classes classes) {
        this.classes = classes;
    }
    public String getRoll() {
        return roll;
    }
    public void setRoll(String roll) {
        this.roll = roll;
    }
    public List<Total> getTotals() {
        return totals;
    }
    public void setTotals(List<Total> totals) {
        this.totals = totals;
    }
    public String getSem() {
        return sem;
    }
    public void setSem(String sem) {
        this.sem = sem;
    }
}
