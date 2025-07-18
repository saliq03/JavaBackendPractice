package com.springsecuritypractice.securitypractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecuritypractice.securitypractice.model.Users;



@Repository
public interface UserRepositoy extends JpaRepository<Users,Integer>{

    Users findByUserName(String userName);

}
