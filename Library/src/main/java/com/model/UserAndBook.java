package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="user_book")
public class UserAndBook {

    @Id
    @Column(name ="id")
    private int userAndBookId;

    @Column(name ="user_id")
    private int studentId;

    @Column(name ="book_id")
    private int bookId;



    @Column(name="dues")
    private int dues;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "return_date")
    private Date returnDate;

      @Column(name="issuetime")
      private String issueTime;

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public int getUserAndBookId() {
        return userAndBookId;
    }

    public void setUserAndBookId(int userAndBookId) {
        this.userAndBookId = userAndBookId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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
        return "UserAndBook{" +
                "userAndBookId=" + userAndBookId +
                ", studentId=" + studentId +
                ", bookId=" + bookId +
                ", dues=" + dues +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                ", issueTime='" + issueTime + '\'' +
                '}';
    }
}
