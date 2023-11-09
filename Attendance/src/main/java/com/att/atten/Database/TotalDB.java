package com.att.atten.Database;

import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.att.atten.Entity.Classes;
import com.att.atten.Entity.Total;
import com.att.atten.Entity.studentEn;



@Repository
public interface TotalDB extends JpaRepository<Total,Integer>{


    @Query("SELECT t.total FROM Total t WHERE t.studentEn = :id")
    public int getTotal(@Param("id") studentEn id); 

    @Query("SELECT t.present FROM Total t WHERE t.studentEn = :id")
    public int getPresent(@Param("id") studentEn id); 
   
    @Modifying
    @Transactional
    @Query("UPDATE Total t SET t.total = :newVal WHERE t.studentEn = :id")
    public void UpdateTotal(@Param("newVal") int newVal,@Param("id") studentEn id);

    @Modifying
    @Transactional
    @Query("UPDATE Total t SET t.present = :newVal WHERE t.studentEn = :id")
    public void UpdatePresent(@Param("newVal") int newVal,@Param("id") studentEn id);

    @Query("SELECT t FROM Total t WHERE studentEn = :id")
    public Total getByStudentid(@Param("id") studentEn id);
}