package com.study.controller;

import com.study.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @Autowired
    StudentService studentService;


    @GetMapping("/student")  //处理GET请求，访问/students路径时会调用这个方法
    public String student(Model model) {
        model.addAttribute("student_list", studentService.getStudentAll());   //从StudentService
        return "students";
    }


}
