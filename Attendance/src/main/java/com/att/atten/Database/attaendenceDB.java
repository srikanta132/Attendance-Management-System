package com.att.atten.Database;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.att.atten.Entity.Attendence;
import com.att.atten.Entity.Classes;
import com.att.atten.Entity.studentEn;
import org.springframework.security.core.parameters.P;

public interface attaendenceDB extends JpaRepository<Attendence, Integer>{

    // public List<Attendence> findByDate(LocalDate date);

    @Query("SELECT a FROM Attendence a WHERE a.classes= :id AND a.teacherdate= :date AND a.studentEn= :sid")
    public Attendence GetBy(@Param("id") Classes id,@Param("date") String date, @Param("sid") studentEn sid);

    @Query("SELECT a FROM Attendence a WHERE a.classes= :id AND a.teacherdate = :date AND a.status = 1")
    public List<Attendence> GetByClass(@Param("id") Classes id,@Param("date") String date);

}
