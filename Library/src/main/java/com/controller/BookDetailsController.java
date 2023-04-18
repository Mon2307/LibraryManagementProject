package com.controller;

import com.model.BookDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;


import com.service.BookDetailsService;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/books")
public class BookDetailsController {

    @Autowired
    BookDetailsService bookDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(BookDetailsController.class);

    @GetMapping
    public List<BookDetails> getBookDetails() {
        logger.info("Book List");
        return bookDetailsService.getBookDetails();
    }

    @PostMapping("/insert")
    public BookDetails createBook(@RequestBody BookDetails bookDetails) {
        logger.info("create Book is invoked{}", bookDetails);
        return bookDetailsService.create(bookDetails);
    }
    @GetMapping("totalbooks")
    public BigInteger gettotalbooks() {
        logger.info("Student List");
        return bookDetailsService.totalbooks();
    }
    @GetMapping("/{id}")
    public BookDetails getBookById(@PathVariable("id") int id){
        logger.info("getBookById is invoked with Book Id : {}",id);
        return bookDetailsService.getBookById(id);
    }

     @GetMapping("matchbooks/{bookname}")
    public List<String> getBooklist(@PathVariable("bookname") String bookname){
        return bookDetailsService.allBooks(bookname);
     }

     @GetMapping("ifbook/{bookid}")
     public boolean ifstudentexists(@PathVariable("bookid") int  bookid){

         return bookDetailsService.bookExists(bookid);
     }

}
