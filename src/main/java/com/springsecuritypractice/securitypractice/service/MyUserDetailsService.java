package com.springsecuritypractice.securitypractice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecuritypractice.securitypractice.model.MyUserDetails;
import com.springsecuritypractice.securitypractice.model.Users;
import com.springsecuritypractice.securitypractice.repository.UserRepositoy;

@Service
public class MyUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepositoy repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=repo.findByUserName(username);
        if (user==null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        
        return new MyUserDetails(user);
    }

}
