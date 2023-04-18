package com.service;

import com.model.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowDuesService {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    public BookissueService bookissueService;

    @Autowired
    public ExcelService excelService;

    @Autowired
    public BookDetailsService bookDetailsService;
    public List<Dues> getDuesByRollNo(String rollno){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int userid = bookissueService.returnStudentId(rollno);
        String s= "select * from user_book where user_id=:x";
        NativeQuery<UserAndBook> nativeQuery=  session.createNativeQuery(s,UserAndBook.class);
        nativeQuery.setParameter("x",userid);
        List<UserAndBook> userAndBookList = nativeQuery.list();
        int n= userAndBookList.size();
        List<Dues> duesList= new ArrayList<>(n);
        for(int i=0;i<n;i++){

            String roll= bookissueService.returnStudentRollNo(userid);
            String book= bookissueService.returnBookName(userAndBookList.get(i).getBookId());
            Date issuedate= userAndBookList.get(i).getIssueDate();
            Date returndate= userAndBookList.get(i).getReturnDate();
            int dues= userAndBookList.get(i).getDues();
            duesList.add(new Dues(roll,book,dues,issuedate,returndate));

        }

        return duesList;
    }

   public List<ExcelDownload> sendMonthData(int y){
       Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();

       Map<Integer,String> monthandbook =  monthbookdata(y);
       Map<Integer,Integer> monthAndDues =  monthanddues(y);



       List<ExcelDownload> excelDownloadList= new ArrayList<ExcelDownload>(12);
       for(int j=1;j<13;j++){
           Month month = Month.of(j);
           int dues= monthAndDues.get(j);
           String bookname=monthandbook.get(j);
           excelDownloadList.add(new ExcelDownload(month,dues,bookname));

       }

       transaction.commit();
       session.close();

      return excelDownloadList;


 }

  public Map<Integer, Integer> monthanddues(int y) {
      Map<Integer, Integer> permonthdue = new HashMap<>();
      for (int i = 1; i < 13; i++) {
          try {
              Session session = sessionFactory.openSession();
              Transaction transaction = session.beginTransaction();
              String s = "select sum(dues) from user_book where month(issue_date) =:x and year(issue_date)= :n";
              NativeQuery<BigDecimal> nativeQuery = session.createNativeQuery(s);
              nativeQuery.setParameter("x", i);
             nativeQuery.setParameter("n", y);

              BigDecimal dues = nativeQuery.getSingleResult();
              permonthdue.put(i, dues.intValue());
              transaction.commit();
              session.close();
          } catch (Exception e) {
              permonthdue.put(i, 0);
          }



      }
      return permonthdue;
  }
 public Map<Integer,String> monthbookdata(int y){
     Map<Integer, List<Integer>> map = new HashMap<>();

     for(int i=1;i<13;i++){
         Session session = sessionFactory.openSession();
         Transaction transaction = session.beginTransaction();
         String s= "SELECT book_id FROM user_book WHERE month(issue_date)=:month and year(issue_date) =:year GROUP BY book_id HAVING  count(book_id) = (SELECT count(book_id) as ap FROM user_book WHERE month(issue_date)=:month and year(issue_date)=:year GROUP BY book_id ORDER BY ap DESC LIMIT 1)";
         try{
             SQLQuery query = session.createSQLQuery(s);
             query.setParameter("month",i);
             query.setParameter("year", y);
             List<Integer> bookid = (List<Integer>) query.list();
             map.put(i,bookid);
             transaction.commit();
             session.close();
         }catch (Exception e){
             List<Integer> a= new ArrayList<>();
             a.add(0);
             map.put(i,a);
         }
     }

     Map<Integer,String> monthandbookname=new HashMap<>();
     for(int i=1;i<13;i++){
         String bookname="";
         try{
             for(int id :map.get(i)){
                 BookDetails bookDetails= bookDetailsService.getBookById(id);
                  bookname =bookname+","+bookDetails.getBookName() ;
             }

             monthandbookname.put(i, bookname.substring(1));
         }
         catch (Exception e){
             monthandbookname.put(i, "");
         }

     }
     return monthandbookname;
 }
    public ByteArrayInputStream writeExcel(List<ExcelDownload> excelList) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream out =new ByteArrayOutputStream();
        try{

            Sheet sheet = workbook.createSheet("MonthlyDues");
            Row row = sheet.createRow(0);
            sheet.setColumnWidth(2, 40 * 256);
            sheet.setColumnWidth(0, 12 * 256);
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            Font font = sheet.getWorkbook().createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 16);
            font.setColor(IndexedColors.BLUE.getIndex());
            cellStyle.setFont(font);

            Cell cellTitle = row.createCell(0);
            cellTitle.setCellStyle(cellStyle);
            cellTitle.setCellValue("Month");

            Cell cellAuthor = row.createCell(1);
            cellAuthor.setCellStyle(cellStyle);
            cellAuthor.setCellValue("Dues");

            Cell cellAuthor1 = row.createCell(2);
            cellAuthor1.setCellStyle(cellStyle);
            cellAuthor1.setCellValue("Most Issued Book");
            int rowCount = 0;

            for (ExcelDownload amonth : excelList) {
                Row row1 = sheet.createRow(++rowCount);
                if((amonth.getDues()!=0) || (amonth.getBookname()!="")){
                    writeMonthdues(amonth, row1);
                }
                else continue;

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());}
        catch (IOException e) {
            throw new RuntimeException(e);
        }
          finally {
            workbook.close();
            out.close();
        }
//        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
//            workbook.write(outputStream);
//        }
    }
    private void writeMonthdues(ExcelDownload amonth, Row row) {

        Cell cell = row.createCell(0);
        String a= String.valueOf(amonth.getMonth());
        cell.setCellValue(a);

        cell = row.createCell(1);
        cell.setCellValue(amonth.getDues());




            cell = row.createCell(2);
            cell.setCellValue(amonth.getBookname());



    }


}
