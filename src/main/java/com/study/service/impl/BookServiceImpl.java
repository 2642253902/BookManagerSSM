package com.study.service.impl;

import com.study.entity.Book;
import com.study.entity.Borrow;
import com.study.mapper.BookMapper;
import com.study.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BookServiceImpl implements BookService {


    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> getActiveBookList() {
        Set<Integer> set = new HashSet<>();     //创建一个HashSet来存储已经被借出的图书ID，HashSet具有快速查找的特点，可以在常数时间内判断一个元素是否存在于集合中
        this.getBorrowAll().forEach(borrow -> set.add(borrow.getBid()));  //遍历所有的借阅记录，将借阅的图书ID添加到一个Set集合中，以便后续判断哪些图书已经被借出

        return bookMapper.getBookAll() //获取所有的图书信息，并通过Java Stream API对图书列表进行过滤，使用filter方法来筛选出那些图书ID不在之前创建的Set集合中的图书，即那些没有被借出的图书，最后将过滤后的结果转换为一个新的列表并返回，以便在视图中显示可借阅的图书列表
                .stream()   //将图书列表转换为一个Stream对象，以便使用Stream API进行操作
                .filter(book -> !set.contains(book.getId()))    //使用filter方法来筛选出那些图书ID不在之前创建的Set集合中的图书，即那些没有被借出的图书，filter方法接受一个Lambda表达式作为参数，该表达式定义了过滤条件，在这里是判断图书ID是否不在Set集合中
                .toList();    //将过滤后的结果转换为一个新的列表并返回，以便在视图中显示可借阅的图书列表，toList方法是Stream API中的一个终端操作，用于将Stream中的元素收集到一个新的列表中，并返回该列表
    }

    @Override
    public List<Borrow> getBorrowAll() {
        return bookMapper.getBorrowAll();
    }

    @Override
    public void deleteBorrowById(int id) {
        bookMapper.deleteBorrowById(id);
    }

    @Override
    public void addBorrow(int sid, int bid) {
        bookMapper.addBorrow(sid, bid);
    }

    @Override
    public Map<Book, Boolean> getBookAll() {        //获取所有图书信息，并判断每本图书是否被借出，返回一个包含图书信息和借阅状态的LinkedHashMap，以便在视图中显示图书列表和对应的借阅状态
        Set<Integer> set = new HashSet<>();     //创建一个HashSet来存储已经被借出的图书ID，HashSet具有快速查找的特点，可以在常数时间内判断一个元素是否存在于集合中
        this.getBorrowAll().forEach(borrow -> set.add(borrow.getBid()));  //遍历所有的借阅记录，将借阅的图书ID添加到一个Set集合中，以便后续判断哪些图书已经被借出
        Map<Book, Boolean> map = new LinkedHashMap<>();    //创建一个LinkedHashMap来存储图书信息和借阅状态，LinkedHashMap可以保持插入顺序，以便在视图中按照添加的顺序显示图书列表
        bookMapper.getBookAll().forEach(book -> {       //遍历所有的图书信息，对于每本图书，判断其ID是否在之前创建的Set集合中，如果在集合中，说明该图书已经被借出，将其对应的值设置为true；如果不在集合中，说明该图书没有被借出，将其对应的值设置为false
            map.put(book, set.contains(book.getId()));  //将图书对象作为键，借阅状态作为值，存储到LinkedHashMap中，以便在视图中显示图书列表和对应的借阅状态
        });
        return map; //返回包含图书信息和借阅状态的LinkedHashMap，以便在视图中显示图书列表和对应的借阅状态
    }

    @Override
    public Book getBookById(int id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public void deleteBookById(int id) {
        bookMapper.deleteBookById(id);
    }

    @Override
    public void addBook(String title, String desc, Double price) {
        bookMapper.addBook(title, desc, price);
    }
}
