package com.att.atten.Credential;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.att.atten.Database.AdminDB;
import com.att.atten.Entity.adminEn;
import com.att.atten.adminController.Atten;

@Controller
@RequestMapping("/details")
public class Entry {

   

    

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminDB adminDB;
    
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/log-in")
    public String getData(){
        return "redirect:/home";
    }

    @GetMapping("/signup")
    public String SignPage(){
        return "adminSignup";
    }
    @PostMapping("/get-data")
    public String GetSignUpData(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("USER_CODE") String code
    ){
        Atten at = new Atten();
        adminEn adminEn = new adminEn();
        adminEn.setName(username);
        adminEn.setEmail(email);
        adminEn.setUsername(username);
        adminEn.setPassword(passwordEncoder.encode(password));
        adminEn.setCode(code);
        adminDB.save(adminEn);
        at.Folder(username);
        return "redirect:/home";
    }
}

