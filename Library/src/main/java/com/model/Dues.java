package com.model;

import java.sql.Date;

public class Dues {
    private String rollno;

    private String bookname;

    private int dues;

    private Date issueDate;

    private Date returnDate;

    public Dues (String rollno,String bookname,int dues,Date issueDate,Date returnDate){
        this.rollno=rollno;
        this.bookname=bookname;
        this.dues=dues;
        this.issueDate=issueDate;
        this.returnDate= returnDate;
    }
    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getDues() {
        return dues;
    }

    public void setDues(int dues) {
        this.dues = dues;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Dues{" +
                "rollno='" + rollno + '\'' +
                ", bookname='" + bookname + '\'' +
                ", dues=" + dues +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
