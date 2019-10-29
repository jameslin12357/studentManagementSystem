package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.MySQL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class StudentController {

    @GetMapping("/indexPaginated")
    // ArrayList<ArrayList<String>>
    public HashMap<String, Object> indexPaginated(@RequestParam("page") String page, @RequestParam("rows") String rows) {
        String offset = Integer.toString((Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        ArrayList<HashMap<String, Object>> Rows = MySQL.Query("select * from students order by studentId desc limit " + rows + " offset " + offset);
        ArrayList<HashMap<String, Object>> total = MySQL.Query("select count(*) as total from students");
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rows", Rows);
        map.put("total", total.get(0).get("total"));
        //String json = "{\"total\":" + count.get(0).get(0) + ",\"rows\":" + data + "}";
        //return json;
        return map;
    }

    @GetMapping("/searchStudent")
    // ArrayList<ArrayList<String>>
    public HashMap<String, Object> indexPaginated(@RequestParam("page") String page, @RequestParam("rows") String rows, @RequestParam("term") String term) {
        String offset = Integer.toString((Integer.parseInt(page) - 1) * Integer.parseInt(rows));
        String sql = "select * from students";
        String sql2 = "select count(*) as total from students";
        if (term != ""){
            sql += " where firstName like '%" + term + "%'";
            sql2 += " where firstName like '%" + term + "%'";
        }
        sql += String.format(" order by studentId desc limit %s offset %s",rows,offset);
        ArrayList<HashMap<String, Object>> Rows = MySQL.Query(sql);
        ArrayList<HashMap<String, Object>> total = MySQL.Query(sql2);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rows", Rows);
        map.put("total", total.get(0).get("total"));
        //String json = "{\"total\":" + count.get(0).get(0) + ",\"rows\":" + data + "}";
        //return json;
        return map;
    }

    @PostMapping("/students")
    // ArrayList<ArrayList<String>>
    public Object createStudent(@RequestBody String student) {
        String[] body = student.split("&");
        String firstName = body[0].split("=")[1];
        String lastName = body[1].split("=")[1];
        String major = body[2].split("=")[1];
        String bio = body[3].split("=")[1];
        String age = body[4].split("=")[1];
        String grade = body[5].split("=")[1];
        String gpa = body[6].split("=")[1];
        String gender = body[7].split("=")[1];
        String sql = String.format("insert into students (firstName, lastName, major, bio, age, grade, gpa, gender) values ('%s','%s','%s','%s','%s','%s','%s','%s')",
                firstName,lastName,major,bio,age,grade,gpa,gender);
//        String sql = "update students set firstName=" + firstName + ",lastName=" + lastName +
//                ",major=" + major + ",bio=" + bio + ",age=" + age + ",grade=" + grade +
//                ",gpa=" + gpa + ",gender=" + gender + " where studentId=" + studentId;
        int Rows = MySQL.Update(sql);
        return Rows;
    }

    @PostMapping("/students/{studentId}")
    // ArrayList<ArrayList<String>>
    public Object editStudent(@PathVariable String studentId, @RequestBody String student) {
        String[] body = student.split("&");
        String firstName = body[0].split("=")[1];
        String lastName = body[1].split("=")[1];
        String major = body[2].split("=")[1];
        String bio = body[3].split("=")[1];
        String age = body[4].split("=")[1];
        String grade = body[5].split("=")[1];
        String gpa = body[6].split("=")[1];
        String gender = body[7].split("=")[1];
        String sql = String.format("update students set firstName='%s', lastName='%s', major='%s', bio='%s', age='%s', grade='%s', gpa='%s', gender='%s' where studentId=%s",
                firstName,lastName,major,bio,age,grade,gpa,gender,studentId);
//        String sql = "update students set firstName=" + firstName + ",lastName=" + lastName +
//                ",major=" + major + ",bio=" + bio + ",age=" + age + ",grade=" + grade +
//                ",gpa=" + gpa + ",gender=" + gender + " where studentId=" + studentId;
        int Rows = MySQL.Update(sql);
        return Rows;
    }

    @GetMapping("/deleteStudent/{studentId}")
    // ArrayList<ArrayList<String>>
    public Object deleteStudent(@PathVariable String studentId) {
        String sql = String.format("delete from students where studentId=%s",studentId);
        int Rows = MySQL.Update(sql);
        return Rows;
    }
}
