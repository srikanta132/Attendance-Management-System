package com.att.atten.adminController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.att.atten.Database.StudentDB;
import com.att.atten.Database.attaendenceDB;

import com.att.atten.Entity.studentEn;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.microsoft.schemas.office.visio.x2012.main.PageContentsDocument;
import com.microsoft.schemas.office.visio.x2012.main.PageContentsType;

import jakarta.servlet.http.HttpServletResponse;

public class Atten {

    @Autowired
    attaendenceDB attaendenceDB;
    @Autowired
    StudentDB studentDB;

    // for creating forlder as username
    public void Folder(String username) {
        String folderPath = "src/main/resources/static/" + username;
        Path path = Paths.get(folderPath);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // for creating folder as username+classesname
    public void FolderClasses(String username, String classes) {
        String folderPath = "src/main/resources/static/" + username + "/" + classes;
        Path path = Paths.get(folderPath);
        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // creating csv for a single class
    public void MakeCSV(String username, String classname, String filename, String sem, String fullname) {
        String path = "src/main/resources/static/" + username + "/" + classname + "/" + filename + ".csv";
        try {
            File file = new File(path);
            FileWriter fileWriter = new FileWriter(path, true);
            CSVFormat csvFormat = CSVFormat.DEFAULT;
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);

            if (!file.exists() || file.length() == 0) {
                csvPrinter.printRecord(sem + " " + classname + " by " + fullname);
            }

            csvPrinter.close();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createExcel(String sheetName, String name, String username, String classname, String clname) {
        String path = "src/main/resources/static/" + username + "/" + classname + "/" + sheetName + ".xlsx";
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            // Row row = sheet.getRow(2);
            // if (row == null) {
            // row = sheet.createRow(2);
            // }

            // Row rw = sheet.createRow(0);
            // Cell ce = rw.createCell(0);
            // ce.setCellValue(clname);

            // Cell cell = row.createCell(0);
            // cell.setCellValue("STUDENTS NAME");

            // int id = sheet.getLastRowNum();
            // short sh = row.getLastCellNum();

            // // row = sheet.createRow(id + 1);
            // Cell cell1 = row.createCell(sh);
            // cell1.setCellValue("REGISTRATION NUMBER");
            // sheet.autoSizeColumn(sh);

            Row row = sheet.createRow(2); // Create the header row (row 0)

            Cell cell0 = row.createCell(0);
            cell0.setCellValue("STUDENTS NAME");

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("REGISTRATION NUMBER ");

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("TOTAL CLASS ");

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(" TOTAL PRESENT ");

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("PERCENTAGE ");

            Cell cell5 = row.createCell(5);
            cell5.setCellValue("CONDITION ");


            // Cell cell4 = row.createCell(4);
            // cell1.setCellValue("PERCENTAGE");

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            // sheet.autoSizeColumn(4);
            CellStyle style = workbook.createCellStyle();

            boolean isBold = true;
            String fontName = "Times New Roman";
            short fontSize = 10;
            short color = IndexedColors.BLACK.getIndex();
            Font font = workbook.createFont();
            font.setBold(isBold);
            font.setFontName(fontName);
            font.setFontHeightInPoints(fontSize);
            font.setColor(color);

            style.setFont(font);
            cell0.setCellStyle(style);
            cell1.setCellStyle(style);
            cell2.setCellStyle(style);
            cell3.setCellStyle(style);
            cell4.setCellStyle(style);
            cell5.setCellStyle(style);

            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }

            System.out.println("Excel file created successfully at: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createExcel123(String username, String classname, String sheetname, String name) {
        try {
            String filePath = "src/main/resources/static/" + username + "/" + classname + "/" + sheetname + ".xlsx";
            // Sheet sheet = workbook.createSheet(sheetName);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int id = sheet.getLastRowNum();
            Row row = sheet.getRow(id);
            short sh = row.getLastCellNum();

            System.out.println(id);
            System.out.println(sh);

            Cell cell = row.createCell(id);
            cell.setCellValue(name); // for daily attendence

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
                fileInputStream.close();
            }

            System.out.println("Excel file created successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Insert(String username, String classname, String sheetname, String name, studentEn studentEn) {
        try {
            String filePath = "src/main/resources/static/" + username + "/" + classname + "/" + sheetname + ".xlsx";
            // Sheet sheet = workbook.createSheet(sheetName);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int i = getLastDataRowNumber(sheet, 0);
            System.out.println(i);
            int i1 = getLastDataRowNumber(sheet, 1);
            System.out.println(i1);

            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(studentEn.getName());
            sheet.autoSizeColumn(i + 1);

            Row row1 = sheet.getRow(i1 + 1);
            Cell cell1 = row1.createCell(1);
            cell1.setCellValue(studentEn.getRoll());

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
                fileInputStream.close();
            }

            System.out.println("Excel file created successfully at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getLastDataRowNumber(Sheet sheet, int columnIndex) {
        int lastRowNumber = 0;
        for (Row row : sheet) {
            Cell cell = row.getCell(columnIndex);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                lastRowNumber = row.getRowNum();
            }
        }
        return lastRowNumber;
    }

    public void Original(String username, String classname, String sheetname, String name,
            studentEn studentEn, int total) {
        try {
            String filePath = "src/main/resources/static/" + username + "/" + classname + "/" + sheetname + ".xlsx";
            // Sheet sheet = workbook.createSheet(sheetName);
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);


            // List<studentEn> st = studentDB.findAll();
            // int s = st.size();

            for (int i = 0; i < 3; i++) {
                Row row = sheet.getRow(3 + i);
                Cell cell = row.createCell(3);
                cell.setCellValue(total);
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
                fileInputStream.close();
            }

            System.out.println("Excel file created successfully at: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pdf(HttpServletResponse response){
        try{
            PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document,page);
        contentStream.beginText();
        contentStream.showText("Hello, World!");
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        document.save(arrayOutputStream);
        document.close();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=example.pdf");
        arrayOutputStream.writeTo(response.getOutputStream());
        arrayOutputStream.flush();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
