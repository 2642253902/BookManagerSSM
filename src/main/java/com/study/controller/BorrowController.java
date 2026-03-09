package com.study.controller;

import com.study.entity.Book;
import com.study.entity.Student;
import com.study.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.study.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BorrowController {

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    @GetMapping({"/borrow", "/"})  //处理GET请求，访问/borrow路径时会调用这个方法
    public String borrow(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  //获取当前登录用户的信息
        model.addAttribute("nickname", user.getUsername()); //将当前用户的用户名添加到模型中，以便在视图中显示
        model.addAttribute("borrow_list", bookService.getBorrowAll());   //从BookService中获取所有的借阅信息，并将其添加到模型中，以便在视图中显示
        model.addAttribute("book_count", bookService.getBookAll().size());   //从BookService中获取所有的图书信息，并将其添加到模型中，以便在视图中显示
        model.addAttribute("student_count", studentService.getStudentAll().size());   //从StudentService中获取所有的学生信息，并将其添加到模型中，以便在视图中显示
        return "borrow";
    }

    @GetMapping("/add-borrow")
    public String addBorrow(Model model) {
        model.addAttribute("book_list", bookService.getActiveBookList());  //从BookService中获取所有的图书信息，并将其添加到模型中，以便在视图中显示
        model.addAttribute("student_list", studentService.getStudentAll());   //从StudentService中获取所有的学生信息，并将其添加到模型中，以便在视图中显示
        return "add-borrow";
    }

    @PostMapping("/add-borrow")
    public String addBorrow(@RequestParam(value = "StudentId") Integer StudentId,
                            @RequestParam(value = "BookId") Integer BookId) {
        bookService.addBorrow(StudentId, BookId);  //调用BookService中的addBorrow方法，传入学生ID和图书ID，以添加新的借阅记录
        return "redirect:/borrow"; //重定向到/borrow路径，以显示更新后的借阅信息列表
    }

    @GetMapping("return-book")
    public String deleteBorrow(int id) {
        bookService.deleteBorrowById(id);  //调用BookService中的deleteBorrowById方法，传入借阅记录的ID，以删除对应的借阅记录
        return "redirect:/borrow"; //重定向到/borrow路径，以显示更新后的借阅信息列表
    }


}
