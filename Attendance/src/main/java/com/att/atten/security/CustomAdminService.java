package com.att.atten.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.att.atten.Database.AdminDB;
import com.att.atten.Entity.adminEn;



@Service
public class CustomAdminService implements UserDetailsService{

    @Autowired
    public AdminDB ad;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        adminEn adminEn = ad.getAdmin(username);
        if(adminEn != null){
            return new CustomAdminDetail(adminEn);
        }
        throw new UsernameNotFoundException("user not found");
    }
    
}
