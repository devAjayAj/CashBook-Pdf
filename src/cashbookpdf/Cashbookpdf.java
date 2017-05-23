/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import com.itextpdf.text.Chunk;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Cashbookpdf
{
   public static void main(String[] args) throws ClassNotFoundException, DocumentException, FileNotFoundException, SQLException
   {
        Document document = new Document(PageSize.LETTER, 0, 0, 80, 42);
        int s = 1;
        double debit = 0;
        double credit = 0;
        Class.forName("org.sqlite.JDBC");
        Connection con;
        con = DriverManager.getConnection("jdbc:sqlite:test.db");
        ResultSet res = null;
        PreparedStatement stat;
        String q = "Select * from data";
        stat = con.prepareStatement(q);
        res = stat.executeQuery();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld1.pdf"));
        HeaderFooterPageEvent event = new HeaderFooterPageEvent(s);
        writer.setPageEvent(event);
        document.open();
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Paragraph head = new Paragraph("Sri Mahaveer Bankers\n",boldFont);
        Paragraph h1 = new Paragraph("Prop:G.Ashok Kumar Jain\n" +
        "S/O H.Ghisulalji Jain\n" +
        "17,West Car street\n" +
        "Chidambaram-608001\n\n");
        Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        Paragraph h2 = new Paragraph("Cash Book\n",boldFont1);
        Paragraph h3 = new Paragraph("1-Mar-2015 to 31-Apr-2016\n\n");
        head.setAlignment(Element.ALIGN_CENTER);
        h1.setAlignment(Element.ALIGN_CENTER);
        h2.setAlignment(Element.ALIGN_CENTER);
        h3.setAlignment(Element.ALIGN_CENTER);
        document.add(head);
        document.add(h1);
        document.add(h2);
        document.add(h3);
        s = 0;
        HeaderFooterPageEvent event1 = new HeaderFooterPageEvent(s);
        writer.setPageEvent(event1);
        PdfPTable table = new PdfPTable(5);
        table.setWidths(new int[]{50,80,50,60,60});
        table.completeRow();
        table.setHeaderRows(2);
        PdfPCell c1 = new PdfPCell(new Phrase("Date"));
        c1.setBorderWidthLeft(0);
        c1.setBorderWidthRight(0);
        c1.setIndent(20);
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Particulars"));
        c2.setBorderWidthLeft(0);
        c2.setBorderWidthRight(0);
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Vch typ"));
        c3.setBorderWidthLeft(0);
        c3.setBorderWidthRight(0);
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Debit"));
        c4.setBorderWidthLeft(0);
        c4.setBorderWidthRight(0);
        c4.setIndent(25);
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Credit"));
        c5.setBorderWidthLeft(0);
        c5.setBorderWidthRight(0);
        c5.setIndent(25);
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c5);
        for(int i = 0; i < 5; i++){
            PdfPCell empty3 = new PdfPCell(new Phrase(" "));
            empty3.setBorder(0);
            table.addCell(empty3);
        }
        //table.setFooterRows(3);
        //table.setSkipLastFooter(true);
        /*for(int i = 0; i < 4; i++){
            PdfPCell empty3 = new PdfPCell(new Phrase(" "));
            empty3.setBorderWidthBottom(0);
            empty3.setBorderWidthLeft(0);
            empty3.setBorderWidthRight(0);
            table.addCell(empty3);
        }
        PdfPCell foot = new PdfPCell(new Phrase("...Continued"));
        foot.setBorderWidthLeft(0);
        foot.setBorderWidthRight(0);
        foot.setHorizontalAlignment(Element.ALIGN_LEFT);
        */
        int ddate = 2;
        while(res.next()){
            if((res.getString("type").equals("NA")) == false){
            //print date two times
            if(ddate != 0){
                PdfPCell cc1 = new PdfPCell(new Phrase(invertDate(res.getString("date"))));
                cc1.setBorder(0);
                table.addCell(cc1);
                ddate--;
            }
            else{
                PdfPCell empty = new PdfPCell(new Phrase(""));
                empty.setBorder(0);
                table.addCell(empty);
            }
            //if(res.getString("type").equals("NA")){
                //ddate = 2;
            //}
            //Printing particulars
            if(res.getString("name").equals("openbal")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Opening Balance"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else if(res.getString("name").equals("loan1") || res.getString("name").equals("loan3")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Loans"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else if(res.getString("name").equals("loan2")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Loan (GL)"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else if(res.getString("name").equals("drawings")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Drawings"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else if(res.getString("name").equals("closingbalance")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Closing Balance"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else if(res.getString("name").equals("interest1")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Interest"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else if(res.getString("name").equals("interest2")){
                PdfPCell cc2 = new PdfPCell(new Phrase("Interest (GL)"));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            else{
                PdfPCell cc2 = new PdfPCell(new Phrase(res.getString("name")));
                cc2.setBorder(0);
                table.addCell(cc2);
            }
            //printing vchtype
            if(res.getString("type").equals("D")){
                PdfPCell cc3 = new PdfPCell(new Phrase("Rcpt"));
                cc3.setBorder(0);
                cc3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cc3);
            }
            else if(res.getString("type").equals("C")){
                PdfPCell cc3 = new PdfPCell(new Phrase("Pymt"));
                cc3.setBorder(0);
                cc3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cc3);
            }
            else if(res.getString("name").equals("CUB")){
                PdfPCell cc3 = new PdfPCell(new Phrase("Ctra"));
                cc3.setBorder(0);
                cc3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cc3);
            }
            else if(res.getString("type").equals("NA")){
                PdfPCell cc3 = new PdfPCell(new Phrase(""));
                cc3.setBorder(0);
                cc3.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cc3);
            }
            //printing debit
            if(res.getString("type").equals("D")){
                PdfPCell cc4 = new PdfPCell(new Phrase(doubledecimal(res.getDouble("amount"))));
                cc4.setBorder(0);
                cc4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cc4);
                Double temp = res.getDouble("amount");
                debit += temp;
            }
            else{
                PdfPCell empty = new PdfPCell(new Phrase(""));
                empty.setBorder(0);
                table.addCell(empty);
            }
            //printing credit
            if(res.getString("type").equals("C")){
                PdfPCell cc5 = new PdfPCell(new Phrase(doubledecimal(res.getDouble("amount"))));
                cc5.setBorder(0);
                cc5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cc5);
                Double temp = res.getDouble("amount");
                credit += temp;
            }
            //printing closing balance
            else if(res.getString("type").equals("NA")){
                PdfPCell cc5 = new PdfPCell(new Phrase(doubledecimal(res.getDouble("amount"))));
                cc5.setBorderWidthLeft(0);
                cc5.setBorderWidthRight(0);
                table.addCell(cc5);
            }
            else{
                PdfPCell empty = new PdfPCell(new Phrase(""));
                empty.setBorder(0);
                table.addCell(empty);
            }
            }
            //adding credit,debit and disp closingbalance
            
            else{
                ddate = 2;
                for(int i = 0; i < 8; i++){
                    PdfPCell empty1 = new PdfPCell(new Phrase(" "));
                    empty1.setBorder(0);
                    table.addCell(empty1);
                }
                //print total debit
                PdfPCell dbt = new PdfPCell(new Phrase(doubledecimal(debit)));
                dbt.setBorderWidthLeft(0);
                dbt.setBorderWidthRight(0);
                dbt.setBorderWidthBottom(0);
                dbt.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(dbt);
                //print total credit
                PdfPCell cdt = new PdfPCell(new Phrase(doubledecimal(credit)));
                cdt.setBorderWidthLeft(0);
                cdt.setBorderWidthRight(0);
                cdt.setBorderWidthBottom(0);
                cdt.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cdt);
                //Print empty cell for date
                PdfPCell empty = new PdfPCell(new Phrase(""));
                empty.setBorder(0);
                table.addCell(empty);
                if(res.getString("name").equals("closingbalance")){
                    Paragraph p = new Paragraph("Closing Balance");
                    PdfPCell cc2 = new PdfPCell(p);
                    cc2.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cc2.setBorder(0);
                    table.addCell(cc2);
                }
                PdfPCell empty1 = new PdfPCell(new Phrase(""));
                empty1.setBorder(0);
                table.addCell(empty1);
                PdfPCell empty2 = new PdfPCell(new Phrase(""));
                empty2.setBorderWidthLeft(0);
                empty2.setBorderWidthRight(0);
                empty2.setBorderWidthTop(0);
                table.addCell(empty2);
                if(res.getString("type").equals("NA")){
                    PdfPCell cc5 = new PdfPCell(new Phrase(doubledecimal(res.getDouble("amount"))));
                    cc5.setBorderWidthLeft(0);
                    cc5.setBorderWidthRight(0);
                    cc5.setBorderWidthTop(0);
                    cc5.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(cc5);
                }
                for(int i = 0; i < 3; i++){
                    PdfPCell empty3 = new PdfPCell(new Phrase(""));
                    empty3.setBorder(0);
                    table.addCell(empty3);
                }
                for(int i = 0; i < 2; i++){
                    PdfPCell dbt1 = new PdfPCell(new Phrase(doubledecimal(debit)));
                    dbt1.setBorderWidthLeft(0);
                    dbt1.setBorderWidthRight(0);
                    dbt1.setBorderWidthTop(0);
                    dbt1.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(dbt1);
                }
                debit = 0;
                credit = 0;
                for(int i = 0; i < 5; i++){
                    PdfPCell empty3 = new PdfPCell(new Phrase(" "));
                    empty3.setBorder(0);
                    table.addCell(empty3);
                }
            }
        }
        document.add(table);
        document.close();
        writer.close();
      
    }
    
   private static String doubledecimal(Double doublenum) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        return df.format(doublenum);
    }
    private static String invertDate(String oldDate){
        String splits[] = oldDate.split("-");
        return splits[2]+ "-" +splits[1]+ "-" +splits[0];
    }
}
class HeaderFooterPageEvent extends PdfPageEventHelper {
    int s;
    public HeaderFooterPageEvent(int s1){
        s = s1;
    }
    public void onStartPage(PdfWriter writer, Document document) {
        Font boldFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        if(s == 0){
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Sri Mahaveer Bankers",boldFont1), 140, 730, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Cash Book : 1-Mar-2015 to 31-Apr-2016"), 170, 715, 0);
        }
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Page" + document.getPageNumber()), 530, 720, 0);
    }  
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        LineSeparator ls = new LineSeparator();
        ls.drawLine(cb,50,552,40);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("...Continued"), 552, 28, 0);
    }
}
