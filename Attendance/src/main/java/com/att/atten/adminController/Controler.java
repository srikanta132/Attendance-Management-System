package com.att.atten.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.att.atten.Database.StudentDB;
import com.att.atten.Database.attaendenceDB;

@Controller
public class Controler {

    @Autowired
    attaendenceDB attaendenceDB;

    @Autowired
    StudentDB studentDB;
    
    

    
}
