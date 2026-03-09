package com.study.controller;

import com.study.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Security;
import java.util.ArrayList;

@Controller
public class BookController {

    @Autowired
    BookService bookService;


    @GetMapping("/books")  //处理GET请求，访问/book路径时会调用这个方法
    public String book(Model model) {
        model.addAttribute("nickname", SecurityContextHolder.getContext().getAuthentication().getPrincipal());  //获取当前登录用户的信息，并将其添加到模型中，以便在视图中显示
        model.addAttribute("book_list", bookService.getBookAll().keySet()); //从BookService中获取所有的图书信息，并将其添加到模型中，以便在视图中显示
        model.addAttribute("borrow_list_status", new ArrayList<>(bookService.getBookAll().values()));   //从BookService中获取所有的借阅信息，并将其添加到模型中，以便在视图中显示
        return "books";
    }

    @GetMapping("/add-book")
    public String addBook() {
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(String title, String desc, Double price, Model model) {
        bookService.addBook(title, desc, price);  //调用BookService中的addBook方法，传入图书的标题、描述和价格，以添加新的图书记录
        model.addAttribute("nickname", SecurityContextHolder.getContext().getAuthentication().getPrincipal());  //获取当前登录用户的信息，并将其添加到模型中，以便在视图中显示
        return "redirect:/books"; //重定向到/books路径，以显示更新后的图书信息列表
    }

    @GetMapping("/delete-book")
    public String deleteBook(int id) {
        bookService.deleteBookById(id);  //调用BookService中的deleteBookById方法，传入图书的ID，以删除对应的图书记录
        return "redirect:/books"; //重定向到/books路径，以显示更新后的图书信息列表
    }


}
