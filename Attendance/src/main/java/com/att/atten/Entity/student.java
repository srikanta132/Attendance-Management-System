package com.att.atten.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String dep;

    String roll;

    String sem;

    String contact_01;

    String contact_02;

    String father_name;

    String mother_name;

    String parents_contact;

    String address_01;

    String address_02;

    String email;

    

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

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

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

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    
}
