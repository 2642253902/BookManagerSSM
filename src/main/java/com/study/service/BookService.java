package com.study.service;

import com.study.entity.Book;
import com.study.entity.Borrow;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book>getActiveBookList();

    List<Borrow> getBorrowAll();

    void deleteBorrowById(int id);

    void addBorrow(int sid, int bid);

    Map<Book, Boolean> getBookAll();

    Book getBookById(int id);

    void deleteBookById(int id);

    void addBook(String title, String desc, Double price);


}
