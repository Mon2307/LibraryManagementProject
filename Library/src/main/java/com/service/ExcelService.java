package com.service;

import com.model.ExcelDownload;
import com.model.StudentDetails;
import com.model.UserAndBook;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private SessionFactory sessionFactory;
@Autowired
private ShowDuesService showDuesService;

          public ByteArrayInputStream getData(int y) throws IOException {
             List<ExcelDownload> excelDownloadList= showDuesService.sendMonthData(y);
             ByteArrayInputStream byteArrayInputStream=showDuesService.writeExcel(excelDownloadList);
             return byteArrayInputStream;
        }




}


