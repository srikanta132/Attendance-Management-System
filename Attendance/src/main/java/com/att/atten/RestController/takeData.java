package com.att.atten.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.att.atten.Database.AdminDB;
import com.att.atten.Database.ClassesDB;
import com.att.atten.Database.StudentDB;
import com.att.atten.Database.TotalDB;
import com.att.atten.Database.attaendenceDB;
import com.att.atten.Database.stDB;
import com.att.atten.Entity.Attendence;
import com.att.atten.Entity.Classes;
import com.att.atten.Entity.Total;
import com.att.atten.Entity.adminEn;
import com.att.atten.Entity.student;
import com.att.atten.Entity.studentEn;
import com.att.atten.adminController.Atten;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class takeData {

    @Autowired
    StudentDB studentDB;

    @Autowired
    ClassesDB classesDB;

    @Autowired
    AdminDB adminDB;

    @Autowired
    TotalDB totalDB;

    @Autowired
    stDB st;

    @Autowired
    attaendenceDB attaendenceDB;

    @PostMapping("/post-data/{id}")
    public ResponseEntity<String> take(@PathVariable UUID id, @RequestBody studentEn data, Principal principal) {
        String username = principal.getName();
        Classes cl = classesDB.getById(id);
        String classname = cl.getClassName().replaceAll("\\s", "_");
        Atten at = new Atten();
        data.setClasses(cl);
        studentEn studentEn = studentDB.save(data);

        studentEn st = studentDB.getS(data.getId());
        Total total = new Total();
        adminEn adminEn = adminDB.getAdmin(username);

        total.setAdminEn(adminEn);
        total.setClasses(cl);
        total.setStudentEn(st);
        totalDB.save(total);

        // at.createExcel123(username, classname, cl.getClassName(), data.getName());
        at.Insert(username, classname, cl.getClassName(), data.getName(), studentEn);
        try {
            at.Insert(username, classname, cl.getClassName() + "_DAILY", data.getName(), studentEn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // at.appendDataToExcel(username,classname,cl.getClassName(),data.getName(),data.getRoll());
        System.out.println(studentEn);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/post-data1/{id}")
    public ResponseEntity<String> take_01(@PathVariable UUID id, @RequestParam("roll") String roll,
            Principal principal) {
        String username = principal.getName();
        student sd = st.find(roll);
        Classes cl = classesDB.getById(id);
        String classname = cl.getClassName().replaceAll("\\s", "_");
        Atten at = new Atten();

        studentEn data = new studentEn();
        data.setName(sd.getName());
        data.setDep(sd.getDep());
        data.setRoll(sd.getRoll());
        data.setContact_01(sd.getContact_01());
        data.setContact_02(sd.getContact_02());
        data.setFather_name(sd.getFather_name());
        data.setMother_name(sd.getMother_name());
        data.setParents_contact(sd.getParents_contact());
        data.setAddress_01(sd.getAddress_01());
        data.setAddress_02(sd.getAddress_02());
        data.setEmail(sd.getEmail());
        data.setSem(sd.getSem());
        data.setClasses(cl);

        studentEn studentEn = studentDB.save(data);

        studentEn st = studentDB.getS(data.getId());
        Total total = new Total();
        adminEn adminEn = adminDB.getAdmin(username);

        total.setAdminEn(adminEn);
        total.setClasses(cl);
        total.setStudentEn(st);
        totalDB.save(total);
        System.out.println(roll + " " + "------------------------------");

        at.Insert(username, classname, cl.getClassName(), data.getName(), studentEn);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/check-roll")
    public ResponseEntity<?> findname(@RequestParam("username") String roll) {
        System.out.println(roll);
        boolean is = !st.exexistsByRoll(roll);
        Map<String, Object> res = new HashMap<>();
        res.put("avail", is);
        return ResponseEntity.ok(res);
    }

//    @GetMapping("/csv/{user}/{classes}/{sheet}")
//    public ResponseEntity<Resource> Download(@PathVariable String user, @PathVariable String classes,
//            @PathVariable String sheet) {
//        String filepath = "src/main/resources/static/" + user + "/" + classes + "/" + sheet;
//        Resource resource = new ClassPathResource(filepath);
//        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .header("Content-Disposition", "attachment; filename=" + sheet)
//                .body(resource);
//    }
@GetMapping("/csv/{user}/{classes}/{sheet}")
public ResponseEntity<Resource> Download(@PathVariable String user, @PathVariable String classes,
                                         @PathVariable String sheet) {
    String filepath = "src/main/resources/static/" + user + "/" + classes + "/" + sheet;
//    Resource resource = new ClassPathResource(filepath);

    try{
        Resource resource1 = new FileSystemResource(filepath);
        if(resource1.exists()){
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header("Content-Disposition", "attachment; filename=" + sheet)
                    .body(resource1);
        }else{
            return  ResponseEntity.notFound().build();
        }

    }catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}


    // @GetMapping("/pdf")
    // public String pdf(HttpServletResponse response){
    // try{
    // PDDocument document = new PDDocument();
    // PDPage page = new PDPage();
    // document.addPage(page);

    // PDPageContentStream contentStream = new PDPageContentStream(document,page);
    // contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
    // contentStream.beginText();
    // contentStream.newLineAtOffset(100, 700);
    // contentStream.showText("Hello, World!");
    // contentStream.endText();
    // contentStream.close();
    // ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
    // document.save(arrayOutputStream);
    // document.close();

    // response.setContentType("application/pdf");
    // response.setHeader("Content-Disposition", "attachment;
    // filename=example.pdf");
    // arrayOutputStream.writeTo(response.getOutputStream());
    // arrayOutputStream.flush();
    // }catch(Exception e){
    // e.printStackTrace();
    // }
    // return "pdf";
    // }
    @GetMapping("/pdf")
    public void generatePdf(HttpServletResponse response, HttpSession session) {
        try {
            List<Attendence> at = (List<Attendence>) session.getAttribute("obj");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            for(Attendence a : at){
                Paragraph p = new Paragraph(a.getName());
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);
            }
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=srikanta.pdf");
            baos.writeTo(response.getOutputStream());
            baos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // @PostMapping("/take-attendence")
    // @Transactional
    // public String takeAttendence(@RequestParam("SelectedStudents") List<Integer>
    // sId,
    // @RequestParam("students") List<Integer> allStudentIds,
    // @RequestParam("date") String date1, HttpSession session, Principal principal,
    // HttpServletResponse response) {
    // LocalDate date = LocalDate.now();
    // UUID id1 = (UUID) session.getAttribute("id");
    // Classes classes = classesDB.getById(id1);

    // try {
    // adminEn adminEn = adminDB.getAdmin(principal.getName());

    // // ------------------------------------------

    // // -----------------------------------------

    // for (Integer id : allStudentIds) {
    // studentEn studentEn = studentDB.findById(id).orElse(null);

    // if (studentEn != null) {
    // Attendence attendence = new Attendence();
    // Total total = new Total();
    // attendence.setDate(date);
    // attendence.setName(studentEn.getName());
    // attendence.setTeacherdate(date1);
    // attendence.setStudentEn(studentEn);

    // int total12 = totalDB.getTotal(studentEn);
    // int present = totalDB.getPresent(studentEn);

    // totalDB.UpdateTotal(total12 + 1, studentEn);
    // if (sId.contains(id)) {
    // attendence.setStatus(1);
    // totalDB.UpdatePresent(present + 1, studentEn);
    // // ----------------

    // // ----------------

    // } else {
    // attendence.setStatus(0);

    // }

    // // Total total1 = totalDB.save(total);
    // // System.out.println(total1);
    // attendence.setClasses(classes);
    // attaendenceDB.save(attendence);

    // }
    // }

    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // // 1-class id (single id)
    // // through class id get students id (List)
    // // through students id get total form toatl entity (List)
    // return "taken successfully";
    // }

    

}
