package com.study.service;

import com.study.entity.Student;

import java.util.List;

public interface StudentService {


    List<Student> getStudentAll();

    Student getStudentById(int id);

}
