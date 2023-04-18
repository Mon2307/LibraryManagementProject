package com.service;

import com.model.BookDetails;
import com.model.BookIssue;
import com.model.StudentDetails;
import com.model.UserAndBook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class BookissueService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserAndBookService userAndBookService;

    @Autowired
    public BookDetailsService bookDetailsService;

    @Autowired
   public BookissueService bookissueService;
    public List<BookIssue> getBookissueDetails(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<UserAndBook> userAndBookList = session.createQuery("from UserAndBook", UserAndBook.class).list();
        int n=userAndBookList.size();

        List<BookIssue> bookIssueList =new ArrayList<BookIssue>(n);
        for(int i=0;i<n;i++){
            Date returnDate= userAndBookList.get(i).getReturnDate();
            if(returnDate==null){
            String rollno= bookissueService.returnStudentRollNo(userAndBookList.get(i).getStudentId());
            String Bookname=bookissueService.returnBookName(userAndBookList.get(i).getBookId());
            Date issuedate= userAndBookList.get(i).getIssueDate();
            String issueTime=userAndBookList.get(i).getIssueTime();
            bookIssueList.add(new BookIssue(rollno,Bookname,issuedate,issueTime));
            }
        }
        transaction.commit();
        session.close();
        return bookIssueList;
    }

    public BookIssue create(BookIssue bookIssue) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(bookIssue);
        transaction.commit();
        session.close();
        return bookIssue;
    }

    public int returnBookId(String bookName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from BookDetails where bookName =:book", BookDetails.class).setParameter("book",bookName);
        BookDetails bookDetails = (BookDetails) query.uniqueResult();
        bookDetails.getBookId();
        transaction.commit();
        session.close();
        System.out.println(bookDetails.getBookId());
        return bookDetails.getBookId();
    }
    public String returnStudentRollNo(int studentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from StudentDetails where  studentId =:id", StudentDetails.class).setParameter("id",studentId);
        StudentDetails studentDetails = (StudentDetails) query.uniqueResult();
        transaction.commit();
        session.close();
        return studentDetails.getRollNo();
    }
    public String returnBookName(int bookId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from BookDetails where  bookId =:id", BookDetails.class).setParameter("id",bookId);
        BookDetails bookDetails = (BookDetails) query.uniqueResult();
        transaction.commit();
        session.close();
        return bookDetails.getBookName();
    }



    public int returnStudentId(String rollNo) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from StudentDetails where  rollNo =:rollno", StudentDetails.class).setParameter("rollno",rollNo);
        StudentDetails studentDetails = (StudentDetails) query.uniqueResult();
        studentDetails.getStudentId();
        transaction.commit();
//
        session.close();
        return studentDetails.getStudentId();

        //        String s= "SELECT id FROM user_details  WHERE rollno = :rollNo";
//        SQLQuery query = session.createSQLQuery(s);
//        query.addEntity(StudentDetails.class);
//        query.setParameter("rollNo",rollNo);
//        userId=  Integer.parseInt((String) query.uniqueResult());
//        transaction.commit();
    }

    public HashMap<String, Integer>  getCategoryCount(){
        List<UserAndBook> userAndBookList= userAndBookService.getUserAndBookDetails();
        int i;
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Fiction",0);
        map.put("Horror",0);
        map.put("ScienceFiction",0);
        map.put("Crime",0);
        map.put("Education",0);
        map.put("Sports",0);

        for (i = 0; i < userAndBookList.size(); i++)
        {
           int id= userAndBookList.get(i).getBookId();
            BookDetails bookDetails=bookDetailsService.getBookById(id);
            if(bookDetails.getCategoryId()==1){

                   map.put("Fiction",map.get("Fiction")+1);
            }
            else if(bookDetails.getCategoryId()==2){
                map.put("Horror",map.get("Horror")+1);
            }
           else if(bookDetails.getCategoryId()==3){
                map.put("ScienceFiction",map.get("ScienceFiction")+1);
            }
            else if(bookDetails.getCategoryId()==4){
                map.put("Crime",map.get("Crime")+1);
            }
           else if(bookDetails.getCategoryId()==5){
                map.put("Education",map.get("Education")+1);
            }
           else if(bookDetails.getCategoryId()==6){
                map.put("Sports",map.get("Sports")+1);
            }
        }
        return map;
    }



}
