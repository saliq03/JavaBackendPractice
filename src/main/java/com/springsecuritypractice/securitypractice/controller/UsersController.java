package com.springsecuritypractice.securitypractice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springsecuritypractice.securitypractice.model.Users;
import com.springsecuritypractice.securitypractice.repository.UserRepositoy;
import com.springsecuritypractice.securitypractice.service.JWTService;

import org.springframework.security.core.Authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UsersController {
    @Autowired
    UserRepositoy userRepositoy;
   @Autowired
   AuthenticationManager authManager;
   
   @Autowired
   JWTService jwtService;

    BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    @PostMapping("/signUp")
    public String signUpUser(@RequestBody Users user) {
        user.setPassword(encoder.encode(user.getPassword()));

        userRepositoy.save(user);
        
        return "User Sign Up Successfully";
    }

    @PostMapping("/signIn")
    public String signInUser(@RequestBody Users user) {
        Authentication authentication=authManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if(authentication.isAuthenticated()){
            
             return jwtService.generateToken(user.getUserName());
        }
        
        return "fail";
    }
    
    

}
