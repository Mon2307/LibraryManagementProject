package com.controller;

import com.model.Dues;
import com.model.StudentDetails;
import com.model.UserAndBook;
import com.service.ExcelService;
import com.service.ShowDuesService;
import com.service.StudentDetailsService;
import com.service.UserAndBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RestController

@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/userandbook")
public class UserAndBookController {
    @Autowired
    UserAndBookService userAndBookService;

    @Autowired
    ShowDuesService showDuesService;

    @Autowired
    ExcelService excelService;
    private static final Logger logger = LoggerFactory.getLogger(UserAndBookController.class);

    @GetMapping
    public List<UserAndBook> getUserAndBookDetails() {
        logger.info("User and book List");
        return userAndBookService.getUserAndBookDetails();
    }

    @GetMapping("totaldues")
    public BigDecimal gettotaldues() {
        logger.info("Student List");
        return userAndBookService.totaldues();
    }

    @GetMapping("studentdue/{rollno}")
    public List<Dues> returnstudentdue(@PathVariable("rollno") String rollno){
        return showDuesService.getDuesByRollNo(rollno);
    }
    @GetMapping("permonth/{year}")
    public Map<Integer,Integer> everymonthdue(@PathVariable("year") int year){
        return showDuesService.monthanddues(year);
    }

    @RequestMapping ("exceldownload/{y}")

   public ResponseEntity<Resource> download(@PathVariable("y") int y) throws IOException {
        String filename= "Monthlyreport.xls";

       ByteArrayInputStream monthlydue= excelService.getData(y);
        InputStreamResource file = new InputStreamResource(monthlydue);

        ResponseEntity<Resource> body = ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename= "+filename)
                 .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
        return body;
    }
}
