package com.study.mapper;

import com.study.entity.Book;
import com.study.entity.Borrow;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface BookMapper {




    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "sid", column = "sid"),
            @Result(property = "bid", column = "bid"),
            @Result(property = "time", column = "time"),
            @Result(property = "StudentName", column = "name"),
            @Result(property = "BookName", column = "title")
    })
    @Select("""
            select  * from borrow 
            left join student on borrow.sid = student.id 
            left join book on borrow.bid =book.id 
                    """)
    List<Borrow> getBorrowAll();       // 查询所有借阅记录


    @Delete("delete from borrow where id = #{id}")
    void deleteBorrowById(int id);      // 根据id删除借阅记录


    @Insert(" insert into borrow (sid, bid, time) values (#{sid}, #{bid},now()) ")
    void addBorrow(@Param("sid") int sid, @Param("bid") int bid); // 插入借阅记录

    @Select("select * from book")
    List<Book> getBookAll();       // 查询所有图书

    @Select("select * from book where id = #{id}")
    Book getBookById(int id);  // 根据id查询图书

    @Delete("delete from book where id = #{id}")
    void deleteBookById(int id);      // 根据id删除图书

    @Insert("INSERT INTO book (title, `desc`, price) VALUES (#{title}, #{desc}, #{price})")
    void addBook(@Param("title") String title, @Param("desc") String desc, @Param("price") Double price);  // 插入图书记录


}
