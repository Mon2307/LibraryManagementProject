package com.service;

import com.model.BookIssue;
import com.model.ReturnBook;
import com.model.StudentDetails;
import com.model.UserAndBook;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Service
public interface UserAndBookService {


     void insertUserId(int id);

     void insertBookId(int id);

     boolean isBookAvailable(boolean x);


       int totalDues(UserAndBook userAndBook);
     String create(BookIssue bookIssue);

     UserAndBook create(ReturnBook returnBook);

     List<UserAndBook> getUserAndBookDetails();


     int totalDues(ReturnBook returnBook);
     List<Integer> alldues(ReturnBook returnBook);

     BigDecimal totaldues();
}
