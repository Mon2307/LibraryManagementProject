package com.model;

import java.time.Month;

public class ExcelDownload {

    Month month;
    int dues;
    String Bookname;
   public ExcelDownload(Month month, int dues,String Bookname){
       this.month=month;
       this.dues=dues;
       this.Bookname= Bookname;
   }

//    public ExcelDownload(Month month, int dues,String Bookname){
//        this.month=month;
//       this.dues=dues;
//   }
//    public Month getMonth() {
//        return month;
//    }
//
//    public void setMonth(Month month) {
//        this.month = month;
//    }


    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public int getDues() {
        return dues;
    }

    public void setDues(int dues) {
        this.dues = dues;
    }

    @Override
    public String toString() {
        return "ExcelDownload{" +
                "month=" + month +
                ", dues=" + dues +
                ", Bookname='" + Bookname + '\'' +
                '}';
    }
}
