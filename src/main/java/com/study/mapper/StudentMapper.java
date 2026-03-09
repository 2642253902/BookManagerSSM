package com.study.mapper;

import com.study.entity.Book;
import com.study.entity.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    @Select("select * from student")
    List<Student> getStudentAll();        // 查询所有学生信息

    @Select("select * from student where id = #{id}")
    Student getStudentById(Integer id);     // 根据id查询学生信息


}
