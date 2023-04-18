package com.controller;

import com.model.BookIssue;
import com.model.StudentDetails;
import com.model.UserAndBook;
import com.service.BookissueService;
import com.service.ShowDuesService;
import com.service.UserAndBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/bookissue")
public class BookissueController {
    @Autowired
    BookissueService bookissueService;

    @Autowired
    UserAndBookService userAndBookService;

    @Autowired
    ShowDuesService showDuesService;
    private static final Logger logger = LoggerFactory.getLogger(BookissueController.class);

    @GetMapping("/{bookName}")
    public int returnBookId(@PathVariable("bookName") String bookName){
        //logger.info("getEmployeeById is invoked with Employee Id : {}",id);
        return bookissueService.returnBookId(bookName);
    }

    @GetMapping("/bookissued")
    public List<BookIssue> getBookDetails() {
        logger.info("Student List");
        return bookissueService.getBookissueDetails();
    }

    @PostMapping("/insert")
    public String create(@RequestBody BookIssue bookIssue) {
        logger.info("create Book is invoked{}",bookissueService);
        return userAndBookService.create(bookIssue);
    }

    @GetMapping
    public HashMap<String,Integer> getCategoryCount(){
        return bookissueService.getCategoryCount();
    }

    @GetMapping("monthlybook/{y}")
    public Map<Integer,String> getmonthdata(@PathVariable("y") int y){
        return showDuesService.monthbookdata(y);
    }

}
