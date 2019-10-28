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
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();

//    @RequestMapping("/greeting")
//    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
//        return new Greeting(counter.incrementAndGet(),
//                String.format(template, name));
//    }

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

    @PostMapping("/students/{studentId}")
    // ArrayList<ArrayList<String>>
    public String editStudent(@PathVariable String studentId, @RequestBody String student) {
        return student;
    }
}
