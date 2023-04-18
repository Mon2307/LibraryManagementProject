package com.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

//@Entity
//@Table(name="userandbook")

public class BookIssue {


//    private int userAndBookId;


    private String studentRollNo;


    private String bookName;

    private Date issueDate;

     private String issueTime;
//    public int getUserAndBookId() {
//        return userAndBookId;
//    }
//
//    public void setUserAndBookId(int userAndBookId) {
//        this.userAndBookId = userAndBookId;
//    }

    public BookIssue(String studentRollNo,String bookName,Date issueDate,String issueTime){
        this.studentRollNo=studentRollNo;
        this.issueDate= issueDate;
        this.bookName= bookName;
        this.issueTime=issueTime;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getStudentRollNo() {
        return studentRollNo;
    }

    public void setStudentRollNo(String studentRollNo) {
        this.studentRollNo = studentRollNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "BookIssue{" +
                "studentRollNo='" + studentRollNo + '\'' +
                ", bookName='" + bookName + '\'' +
                ", issueDate=" + issueDate +
                ", issueTime='" + issueTime + '\'' +
                '}';
    }
}
