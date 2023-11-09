package com.att.atten.Database;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.att.atten.Entity.Classes;
import com.att.atten.Entity.adminEn;

@Service
public interface ClassesDB extends JpaRepository<Classes, UUID>{
    
    @Query("SELECT c FROM Classes c WHERE c.adminEn = :id")
    public List<Classes> getClasses(@Param("id") adminEn id);


    public Classes findByClassName(String className);
}
