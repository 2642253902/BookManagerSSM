package com.study.service.impl;

import com.study.entity.Student;
import com.study.mapper.StudentMapper;
import com.study.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;


    @Override
    public List<Student> getStudentAll() {
        return  studentMapper.getStudentAll();
    }

    @Override
    public Student getStudentById(int id) {
        return studentMapper.getStudentById(id);
    }
}
