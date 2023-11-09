package com.att.atten.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.att.atten.Entity.adminEn;

@Service
@EnableJpaRepositories
public interface AdminDB extends JpaRepository<adminEn, Integer>{
    
    @Query("SELECT a FROM adminEn a WHERE a.username = :username")
    public adminEn getAdmin(@Param("username") String username);
}
