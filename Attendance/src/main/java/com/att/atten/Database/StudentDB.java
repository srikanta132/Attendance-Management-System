package com.att.atten.Database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.att.atten.Entity.Classes;
import com.att.atten.Entity.studentEn;

@Service
public interface StudentDB extends JpaRepository<studentEn, Integer>{
    
    @Query("SELECT s FROM studentEn s WHERE s.classes = :id")
    public List<studentEn> GetByIdList(@Param("id") Classes id);

    @Query("SELECT s FROM studentEn s WHERE s.id = :roll")
    public studentEn getS(@Param("roll") int roll);
}
