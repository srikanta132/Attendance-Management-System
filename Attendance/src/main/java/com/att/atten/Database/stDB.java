package com.att.atten.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.att.atten.Entity.student;

public interface stDB extends JpaRepository<student, Integer>{
    
    @Query("SELECT CASE WHEN COUNT(s.roll)>0 THEN true ELSE false END FROM student s WHERE s.roll = :roll")
    public boolean exexistsByRoll(@Param("roll") String roll);

    @Query("SELECT s FROM student s WHERE s.roll = :roll")
    public student find(@Param("roll") String roll);
    
}