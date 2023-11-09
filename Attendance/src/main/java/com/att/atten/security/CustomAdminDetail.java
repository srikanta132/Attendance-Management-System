package com.att.atten.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.att.atten.Entity.adminEn;




public class CustomAdminDetail implements UserDetails{


    private adminEn adminEntity;

    public CustomAdminDetail(adminEn adminEn){
        this.adminEntity = adminEn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> authrity = new HashSet<>();
        authrity.add(new SimpleGrantedAuthority(this.adminEntity.getCode()));
        System.out.println("---------------------------------------------------------------");
        System.out.println(adminEntity.getCode());
        return authrity;
    }

    @Override
    public String getPassword() {
        return adminEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return adminEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }    
}
