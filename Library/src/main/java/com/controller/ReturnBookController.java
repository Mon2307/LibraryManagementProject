package com.controller;

import com.model.ReturnBook;
import com.model.UserAndBook;
import com.service.BookissueService;
import com.service.UserAndBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/bookreturn")
public class ReturnBookController {

        @Autowired
        BookissueService bookissueService;

        @Autowired
        UserAndBookService userAndBookService;
        private static final Logger logger = LoggerFactory.getLogger(com.controller.ReturnBookController.class);

        @GetMapping("/{bookName}")
        public int returnBookId(@PathVariable("bookName") String bookName){
            //logger.info("getEmployeeById is invoked with Employee Id : {}",id);
            return bookissueService.returnBookId(bookName);
        }


        @PostMapping("/insert")
        public UserAndBook create(@RequestBody ReturnBook returnBook) {
            logger.info("create Book is invoked{}",bookissueService);
            return userAndBookService.create(returnBook);
        }

        @PostMapping("/getdues")
        public int getDues(@RequestBody ReturnBook returnBook){
            return userAndBookService.totalDues(returnBook);
        }

        @PostMapping("/alldues")
        public List<Integer> alldues(@RequestBody ReturnBook returnBook){ return  userAndBookService.alldues(returnBook);}



    }


