package com.service;

import com.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

@Service
public class UserAndBookServiceimpl implements UserAndBookService{

    @Autowired
    private SessionFactory sessionFactory;



    private BookIssue bookIssue;

    @Autowired
     public BookissueService bookissueService;


    public String create(BookIssue bookIssue) {


        UserAndBook userAndBook = new UserAndBook();

        int bookId = bookissueService.returnBookId(bookIssue.getBookName());
        int userId= bookissueService.returnStudentId(bookIssue.getStudentRollNo());
        String rollNo= bookissueService.returnStudentRollNo( userId);
        System.out.println(bookId);
        System.out.println(userId);
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            String s = "select * from user_book where user_id =:userid and book_id=:bookid";
            NativeQuery<UserAndBook> nativeQuery = session.createNativeQuery(s, UserAndBook.class);
            nativeQuery.setParameter("userid", userId);
            nativeQuery.setParameter("bookid", bookId);
            UserAndBook userAndbook = nativeQuery.getSingleResult();

            if((userAndbook.getReturnDate()!=null)){
                userAndBook.setBookId(bookId);
                userAndBook.setStudentId(userId);
                userAndBook.setIssueDate(bookIssue.getIssueDate());
                userAndbook.setIssueTime(bookIssue.getIssueTime());
                String time=bookIssue.getIssueTime();
                session.save(userAndBook);
                String q="select * from book_detail where id=:bookid";
                NativeQuery<BookDetails> nativeQuery2=session.createNativeQuery(q,BookDetails.class);
                nativeQuery2.setParameter("bookid",bookId);
                BookDetails bookDetails = nativeQuery2.getSingleResult();
                bookDetails.setTotal_copies(bookDetails.getTotal_copies()-1);
                String bookname=bookDetails.getBookName();
                String k="select * from user_detail where rollno=:no";
                NativeQuery<StudentDetails> nativeQuery3=session.createNativeQuery(k,StudentDetails.class);
                nativeQuery3.setParameter("no",rollNo);
               StudentDetails studentDetails = nativeQuery3.getSingleResult();
               String mailid = studentDetails.getMailId();
               String message="The book has been issued with following Details: " +'\n' + "Book Name: "+bookname+ '\n' +"Roll no: " + rollNo + '\n' +"Issue Time: " +time;
               String subject="Book Issued";
               String from="monika.hamand@wavemaker.com";
                 SendMailService sendmailService = new SendMailService();
                 sendmailService.sendEmail(message,subject,mailid,from);
                transaction.commit();
                session.close();
                return "Book Issued";
            }


      }
       catch(Exception e){
           Session session = sessionFactory.openSession();
           Transaction transaction = session.beginTransaction();
            userAndBook.setBookId(bookId);
            userAndBook.setStudentId(userId);
            userAndBook.setIssueDate(bookIssue.getIssueDate());
            userAndBook.setIssueTime(bookIssue.getIssueTime());
           String time=bookIssue.getIssueTime();
            session.save(userAndBook);
            String q="select * from book_detail where id=:bookid";
            NativeQuery<BookDetails> nativeQuery2=session.createNativeQuery(q,BookDetails.class);
            nativeQuery2.setParameter("bookid",bookId);
            BookDetails bookDetails = nativeQuery2.getSingleResult();
            String bookname = bookDetails.getBookName();
            bookDetails.setTotal_copies(bookDetails.getTotal_copies()-1);
           String k="select * from user_detail where rollno=:no";
           NativeQuery<StudentDetails> nativeQuery3=session.createNativeQuery(k,StudentDetails.class);

           nativeQuery3.setParameter("no",rollNo);
           StudentDetails studentDetails = nativeQuery3.getSingleResult();
           String mailid = studentDetails.getMailId();

           String message="The book has been issued with following Details: " +'\n' + "Book Name: "+bookname+ '\n' +"Roll no: " + rollNo + '\n' +"Issue Time: " +time;
           String subject="Book Issued";
           String from="monika.hamand@wavemaker.com";
           SendMailService sendmailService = new SendMailService();
           sendmailService.sendEmail(message,subject,mailid,from);
            transaction.commit();
            session.close();
            return "Book Issued";
        }


//            transaction.commit();
//           session.close();
//            System.out.println(userAndBook);
            return "Book Already issued";



    }


    public UserAndBook create(ReturnBook returnBook) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        int bookId = bookissueService.returnBookId(returnBook.getBookName());
        int userId= bookissueService.returnStudentId(returnBook.getStudentRollNo());
        String s="select * from user_book where user_id =:userid and book_id=:bookid and return_date is :x ";

        NativeQuery<UserAndBook> nativeQuery=session.createNativeQuery(s,UserAndBook.class);
//        Query query = session.createQuery(s, UserAndBook.class).setParameter("userid", userId ).setParameter("bookid",bookId);
        nativeQuery.setParameter("userid",userId);
        nativeQuery.setParameter("bookid",bookId);
        nativeQuery.setParameter("x", null);
        UserAndBook userAndbook = nativeQuery.getSingleResult();
        userAndbook.setReturnDate(returnBook.getReturnDate());

        session.update(userAndbook);
        String r="select * from book_detail where id=:bookid";
        NativeQuery<BookDetails> nativeQuery3=session.createNativeQuery(r,BookDetails.class);
        nativeQuery3.setParameter("bookid",bookId);
        BookDetails bookDetails=nativeQuery3.getSingleResult();
        bookDetails.setTotal_copies(bookDetails.getTotal_copies()+1);
        String bookname=bookDetails.getBookName();
        String rollNo= returnBook.getStudentRollNo();
        Date returndate= returnBook.getReturnDate();
        String message="The book has been returned with following Details: " +'\n' + "Book Name: "+bookname+ '\n' +"Roll no: " + rollNo + '\n'+"Return Date: "+returndate;
        String subject="Book Returned";
        String from="monika.hamand@wavemaker.com";

        String k="select * from user_detail where rollno=:no";
        NativeQuery<StudentDetails> nativeQuery4=session.createNativeQuery(k,StudentDetails.class);

        nativeQuery4.setParameter("no",rollNo);
        StudentDetails studentDetails = nativeQuery4.getSingleResult();
        String mailid = studentDetails.getMailId();
        SendMailService sendmailService = new SendMailService();
        sendmailService.sendEmail(message,subject,mailid,from);
         session.update(bookDetails);
        System.out.println(bookId);
        System.out.println(userId);
        transaction.commit();
        session.close();
        System.out.println(userAndbook);
        return userAndbook;
    }

    public List<UserAndBook> getUserAndBookDetails() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<UserAndBook> userAndBookList = session.createQuery("from UserAndBook", UserAndBook.class).list();
        transaction.commit();
        session.close();
        return userAndBookList;
    }



    public void insertUserId(int id) {

    }

    public void insertBookId(int id) {

    }

    public boolean isBookAvailable(boolean x) {
        return false;
    }

    @Override
    public int totalDues(UserAndBook userAndBook) {
        return 0;
    }


    @Override
    public int totalDues(ReturnBook returnBook) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        int bookId = bookissueService.returnBookId(returnBook.getBookName());
        int userId = bookissueService.returnStudentId(returnBook.getStudentRollNo());
        String s = "select * from user_book where user_id =:userid and book_id=:bookid";
        NativeQuery<UserAndBook> nativeQuery = session.createNativeQuery(s, UserAndBook.class);
        nativeQuery.setParameter("userid", userId);
        nativeQuery.setParameter("bookid", bookId);
        UserAndBook userAndbook = nativeQuery.getSingleResult();
        Date returndate = userAndbook.getReturnDate();
        Date issuedate =  userAndbook.getIssueDate();
        String firstdate= String.valueOf(issuedate);
        String seconddate= String.valueOf(returndate);
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy-MM-dd")
                .toFormatter(Locale.UK);
        LocalDate issued = LocalDate.parse(firstdate, formatter);
        LocalDate returned = LocalDate.parse(seconddate, formatter);
        LocalDate d1 = LocalDate.parse( issued.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate d2 = LocalDate.parse( returned.toString(), DateTimeFormatter.ISO_LOCAL_DATE);
        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        long diffDays = diff.toDays();

        int dues = 0;
        if (diffDays > 1) {
            dues = (int) (2 * (diffDays-1));
            userAndbook.setDues(dues);
        }
        userAndbook.setDues(dues);
        session.update(userAndbook);
        transaction.commit();
        session.close();
        System.out.println(userAndbook);
        return dues;
    }

    @Override
    public List<Integer> alldues(ReturnBook returnBook) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int userId = bookissueService.returnStudentId(returnBook.getStudentRollNo());

        String p = "SELECT SUM(dues) FROM user_book where user_id =:userid";
        NativeQuery<List<Integer>> nativeQuery4 = session.createNativeQuery(p);
        nativeQuery4.setParameter("userid", userId);
        List alldues= nativeQuery4.getResultList();
        return  alldues;
    }

    @Override
    public BigDecimal totaldues() {

            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            String s=" SELECT SUM(dues) FROM user_book";
            NativeQuery<BigDecimal> nativeQuery=session.createNativeQuery(s);
//        Query query = session.createQuery(s, UserAndBook.class).setParameter("userid", userId ).setParameter("bookid",bookId);
            BigDecimal totaldues = nativeQuery.getSingleResult();
            return totaldues;
        }
    }
//        String q = "SELECT DATEDIFF(day, :issuedate, :returndate) AS DateDiff";
//        NativeQuery<Integer> nativeQuery2 = session.createNativeQuery(q, String.valueOf(userAndbook));
//        nativeQuery2.setParameter("returndate",returndate);
//        nativeQuery2.setParameter("issuedate",issuedate);
//        int diffInDays =nativeQuery2.getSingleResult();



