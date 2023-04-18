package com.model;

import java.sql.Date;

public class ReturnBook {

    private String studentRollNo;


    private String bookName;

    private Date returnDate;

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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "ReturnBook{" +
                "studentRollNo='" + studentRollNo + '\'' +
                ", bookName='" + bookName + '\'' +
                ", returnDate=" + returnDate +
                '}';
    }
}
