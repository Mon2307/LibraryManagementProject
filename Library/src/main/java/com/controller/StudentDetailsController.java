package com.controller;

import com.model.StudentDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;


import com.service.StudentDetailsService;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/students")
public class StudentDetailsController {

    @Autowired
    StudentDetailsService studentDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(StudentDetailsController.class);

    @GetMapping
    public List<StudentDetails> getBookDetails() {
        logger.info("Student List");
        return studentDetailsService.getStudentDetails();
    }

    @GetMapping("totalstudents")
    public BigInteger gettotalstudents() {
        logger.info("Student List");
        return studentDetailsService.totalstudents();
    }

    @GetMapping("onestudent/{rollNo}")
    public  StudentDetails getStudent (@PathVariable("rollNo") String rollNo){
        return studentDetailsService.getStudentByRollno(rollNo);
    }
    @PostMapping("/insert")
    public StudentDetails create(@RequestBody StudentDetails studentDetails) {
        logger.info("create Student is invoked{}", studentDetails);
        return studentDetailsService.create(studentDetails);
    }

    @PutMapping("/updatestudent")
    public StudentDetails updateStudent(@RequestBody StudentDetails studentDetails){
        return studentDetailsService.updateStudent(studentDetails);
    }
    @GetMapping("/{rollNo}")
    public boolean ifstudentexists(@PathVariable("rollNo") String rollNo){

        return studentDetailsService.studentExists(rollNo);
    }

    @GetMapping("delete/{rollNo}")
    public String deletestudent( @PathVariable("rollNo") String rollNo){
        return studentDetailsService.deleteStudentByrollNo(rollNo);
    }
}
