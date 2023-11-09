package com.att.atten.adminController;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.itextpdf.text.DocumentException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class adminHome {

    @Autowired
    StudentDB studentDB;

    @Autowired
    attaendenceDB attaendenceDB;

    @Autowired
    AdminDB adminDB;

    @Autowired
    ClassesDB classesDB;

    @Autowired
    TotalDB totalDB;

    @Autowired
    stDB stDB;

    @GetMapping("/")
    public String home(Principal principal) {
        return "home";
    }

    @GetMapping("/home")
    public String home_one() {
        return "home";
    }

    @GetMapping("/create-class")
    public String classes(Principal principal) {
        Atten at = new Atten();
        String name = principal.getName();
        adminEn adminEn = adminDB.getAdmin(name);
        return "classes";
    }

    @PostMapping("/get-class")
    public String GetDataClass(@RequestParam("name") String cname,
            @RequestParam("sem") String sem,
            @RequestParam("date") String date,
            Principal principal) {

        String clname = cname.replaceAll("\\s", "_");
        /*
         * when every time you fetched class name form database you need to consider
         * following
         * 1-if your fetched & use for targetting folder/derctories --
         * * folder/directories are uses space in behalf of '_' underscore
         * * try replaceAll("\\s","_") function like this
         */
        Atten at = new Atten();
        String username = principal.getName();
        adminEn adminEn = adminDB.getAdmin(username);
        Classes classes = new Classes();
        String fullname = adminEn.getName();
        classes.setAdminEn(adminEn);
        classes.setSem(sem);
        classes.setClassName(cname);
        classes.setDate(date);
        Classes cla = classesDB.save(classes);
        System.out.println(cla);
        at.FolderClasses(username, clname);
        try {
            at.createExcel(cname, fullname, username, clname, cname);
            at.createExcel(cname+"_DAILY",fullname,username,clname,cname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "classes";
    }

    @GetMapping("/list-students")
    public String takeDataFromStudent(Model model) {
        List<studentEn> studentEn = studentDB.findAll();
        model.addAttribute("obj", studentEn);
        return "listStudents";
    }

    @PostMapping("/submit-att")
    public String submitAtt(@RequestParam("students") List<Integer> students) {
        LocalDate date = LocalDate.now();
        for (Integer id : students) {
            studentEn studentEn = studentDB.findById(id).orElse(null);
            if (studentEn != null) {
                Attendence attendence = new Attendence();
                attendence.setDate(date.toString());
                attendence.setStudentEn(studentEn);
                attendence.setStatus(1);
                attaendenceDB.save(attendence);
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/your-class")
    public String viewClass(Principal principal, Model model) {
        String username = principal.getName();
        adminEn adminEn = adminDB.getAdmin(username);
        List<Classes> classes = classesDB.getClasses(adminEn);
        model.addAttribute("classes", classes);
        classes.forEach(c -> {
            System.out.println(c.getClassName());
        });
        return "viewClass";
    }

    @GetMapping("/your-class/add/{id}")
    public String ClassInToClass(@PathVariable UUID id, HttpSession session) {
        Classes cl = classesDB.getById(id);
        session.setAttribute("id", cl.getId());
        cl.getClassName();

        return "classintoclass";
    }
    @GetMapping("/day")
    public String day(){
        return "day";
    }
    @GetMapping("/csv")
    public String YourCSv(HttpSession session, Principal principal, Model model) {
        String username = principal.getName();
        UUID id = (UUID) session.getAttribute("id");
        Classes classes = classesDB.getById(id);
        String sheetname = classes.getClassName()+".xlsx";
        List<studentEn> studentEns = studentDB.GetByIdList(classes);
        model.addAttribute("username", username);
        model.addAttribute("classname", classes.getClassName().replaceAll("\\s", "_"));
        model.addAttribute("sheetname", sheetname);
        String filePath = "src/main/resources/static/" + username + "/" + classes.getClassName().replaceAll("\\s", "_")
                + "/" + classes.getClassName() + ".xlsx";

        String path = "src/main/resources/static/" + username + "/" + classes.getClassName().replaceAll("\\s", "_")
                + "/" + classes.getClassName() + "_DAILY.xlsx";

                model.addAttribute("path", filePath);
        // Atten at = new Atten();
        int i = 0;
        int j = 4;
        for (studentEn s : studentEns) {
            System.out.println(s.getName());
            Total total = totalDB.getByStudentid(s);
            // at.Original(username, classes.getClassName().replaceAll("\\s", "_"),
            // classes.getClassName(), null, null,
            // total.getPresent());
            try {
                // Sheet sheet = workbook.createSheet(sheetName);
                FileInputStream fileInputStream = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fileInputStream);
                Sheet sheet = workbook.getSheetAt(0);

                //created a row of cell
                Row row = sheet.getRow(3 + i);
                Cell cell = row.createCell(3);
                cell.setCellValue(total.getPresent());

                Row row1 = sheet.getRow(3 + i);
                Cell cell1 = row1.createCell(2);
                cell1.setCellValue(total.getTotal());

                Row row2 = sheet.getRow(3+i);
                Cell cell2= row2.createCell(4);
                cell2.setCellFormula("D"+j+"/"+"C"+j+"*100");

                Row row3 = sheet.getRow(3+i);
                Cell cell3 = row3.createCell(5);
                cell3.setCellFormula("IF(E"+j+">=75,\"GOOD\",\"BAD\")");

                HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                String formattedValue = dataFormatter.formatCellValue(cell2);

                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    workbook.write(fos);
                    fileInputStream.close();
                }

                System.out.println("Excel file created successfully at: " + filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            i++;
            j++;
        }
        return "csv";
    }

    @GetMapping("/add-students")
    public String takeDataFromStudents(Model model, HttpSession session) {
        String endPoint = "/post-data/" + session.getAttribute("id");
        model.addAttribute("endPoint", endPoint);
        return "add-st";
    }

    @GetMapping("/already")
    public String already(Model model, HttpSession session){
        String endPoint = "/post-data1/" + session.getAttribute("id")+"?roll=";
        model.addAttribute("endPoint", endPoint);
        List<student> st = stDB.findAll();
        model.addAttribute("students", st);
        return "already";
    }

    @GetMapping("/manually")
    public String manually(Model model, HttpSession session){
        String endPoint = "/post-data/" + session.getAttribute("id");
        model.addAttribute("endPoint", endPoint);
        return "studentForm";
    }
    @GetMapping("/viewData")
    public String ViewData(HttpSession session, Model model) {
        UUID id = (UUID) session.getAttribute("id");
        Classes cl = classesDB.getById(id);
        List<studentEn> studentEn = studentDB.GetByIdList(cl);
        model.addAttribute("students", studentEn);
        return "viewData";
    }

    @GetMapping("/student/{id}")
    public String details(@PathVariable int id, Model model){
        studentEn st = studentDB.getById(id);
        model.addAttribute("st", st);
        return "details";
    }

    @PostMapping()
    public String SetDataInToClass() {
        return "";
    }

    @GetMapping("/classes")
    public String ListOfYour(Principal principal, Model model) {
        adminEn adminEn = adminDB.getAdmin(principal.getName());
        List<Classes> cl = classesDB.getClasses(adminEn);
        model.addAttribute("class", cl);
        return "ListOfYour";
    }

    @GetMapping("/classes/Attendence/{id}")
    public String takeAttendence(@PathVariable UUID id, Model model, HttpSession session) {
        Classes cl = classesDB.getById(id);
        List<studentEn> studentEn = studentDB.GetByIdList(cl);
        model.addAttribute("students", studentEn);
        session.setAttribute("id", id);
        return "ClassesOfStudents";
    }

    @PostMapping("/take-attendence")
    @Transactional
    public String takeAttendence(@RequestParam("SelectedStudents") List<Integer> sId,
            @RequestParam("students") List<Integer> allStudentIds,
            @RequestParam("date") String date1, HttpSession session, Principal principal,
            HttpServletResponse response) {
        LocalDate date = LocalDate.now();
        UUID id1 = (UUID) session.getAttribute("id");
        Classes classes = classesDB.getById(id1);

        try {
            adminEn adminEn = adminDB.getAdmin(principal.getName());

            
            for (Integer id : allStudentIds) {
                studentEn studentEn = studentDB.findById(id).orElse(null);

                if (studentEn != null) {
                    Attendence attendence = new Attendence();
                    Total total = new Total();
                    attendence.setDate(date.toString());
                    attendence.setName(studentEn.getName());
                    attendence.setTeacherdate(date1);
                    attendence.setStudentEn(studentEn);

                    int total12 = totalDB.getTotal(studentEn);
                    int present = totalDB.getPresent(studentEn);

                    totalDB.UpdateTotal(total12 + 1, studentEn);

                    if (sId.contains(id)) {
                        attendence.setStatus(1);
                        totalDB.UpdatePresent(present + 1, studentEn);
                       

                    } else {
                        attendence.setStatus(0);

                    }

                    // Total total1 = totalDB.save(total);
                    // System.out.println(total1);
                    attendence.setClasses(classes);
                    attaendenceDB.save(attendence);

                }
               
            }
            
        }catch (Exception e) {
            e.printStackTrace();
        }

            // 1-class id (single id)
            // through class id get students id (List)
            // through students id get total form toatl entity (List)
        return "redirect:/home";
        }


    // @GetMapping("/total")
    // public String toatl(HttpSession session) {
    //     int i = (int) session.getAttribute("id");
    //     Classes classes = classesDB.getById(i);
    //     List<studentEn> studentEns = studentDB.GetByIdList(classes);
    //     for (studentEn s : studentEns) {
    //         System.out.println(s.getName() + "  " + s.getRoll());
    //         Total t = totalDB.getByStudentid(s);
    //         System.out.println(t.getStudentEn());
    //         System.out.println(t.getTotal());
    //         System.out.println(t.getPresent());
    //     }
    //     return "home";
    // }

    @GetMapping("/settings")
    public String settings(){
        return "settings";
    }


    LocalDateTime linkexpiration;
    @PostMapping("/set-link")
    public String duration(@RequestParam("duration") int minutes){
        LocalDateTime currenttime = LocalDateTime.now();
        linkexpiration = currenttime.plusMinutes(minutes);
        return "redirect:/home";
    }
//    LocalTime starttime = LocalTime.now().minusMinutes(2);
//    LocalTime endstime = starttime.plus(5, ChronoUnit.MINUTES);
    @GetMapping("/cic")
    public String srikanta(Model model){
        LocalDateTime currenttime = LocalDateTime.now();
        if(linkexpiration != null && currenttime.isBefore(linkexpiration)){
            List<Classes> cl =  classesDB.findAll();
            model.addAttribute("classes", cl);
            return "se";
        }else{
            return "NotFound404";
        }
    }

    @PostMapping("/get")
    public String data(
        @RequestParam("name") String name,
        @RequestParam("dep") String dep,
        @RequestParam("roll") String roll,
        @RequestParam("c1") String c1,
        @RequestParam("c2") String c2,
        @RequestParam("fname") String fname,
        @RequestParam("mname") String mname,
        @RequestParam("pphone") String pphone,
        @RequestParam("ad1") String ad1,
        @RequestParam("ad2") String ad2,
        @RequestParam("email") String email,
        @RequestParam("sem") String sem
    ){
        student st = new student();
        st.setName(name);
        st.setDep(dep);
        st.setRoll(roll);
        st.setContact_01(c1);
        st.setContact_02(c2);
        st.setFather_name(fname);
        st.setMother_name(mname);
        st.setParents_contact(pphone);
        st.setAddress_01(ad1);
        st.setAddress_02(ad2);
        st.setEmail(email);
        st.setSem(sem);
        stDB.save(st);
        return "thanks";
    }
    
    @GetMapping("/day-get")
    public String day(@RequestParam("date") String date, Principal principal, HttpSession session,
            HttpServletResponse response) throws DocumentException {
        UUID id = (UUID) session.getAttribute("id");
        System.out.println(id);
        System.out.println(date);
        Classes cl = classesDB.getById(id);
        System.out.println(cl.getClassName());
        List<Attendence> at = attaendenceDB.GetByClass(cl, date);
        session.setAttribute("obj", at);
        return "Enjoy";
    }
}