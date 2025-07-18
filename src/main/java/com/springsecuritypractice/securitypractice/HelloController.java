package com.springsecuritypractice.securitypractice;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class HelloController {

    private List<Student> students= new ArrayList<>(List.of(
        new Student(1, "Saliq", "3rd"),
        new Student(1, "Saliq", "3rd")
      ));

    @GetMapping("/")
    public String greeting(HttpServletRequest request){
        return "welcome "+request.getSession().getId();
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return students;
    }

    @PostMapping("/student")
    public String addStudent(@RequestBody Student student) {
        students.add(student);
        return "Student added successfully";
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }
    
    
    

}
