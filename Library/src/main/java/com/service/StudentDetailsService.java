package com.service;

import com.model.StudentDetails;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
@Service
public interface StudentDetailsService {

    String getStudentByName();
    List<StudentDetails> getStudentDetails();

    StudentDetails create(StudentDetails studentDetails);


    StudentDetails getStudentByRollno(String rollno);

    String deleteStudentByrollNo(String rollno);

     Boolean studentExists(String rollno);
   BigInteger totalstudents();

    StudentDetails updateStudent(StudentDetails studentDetails);

}
