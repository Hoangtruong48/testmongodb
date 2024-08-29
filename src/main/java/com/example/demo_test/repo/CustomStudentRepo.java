package com.example.demo_test.repo;

import com.example.demo_test.model.Student;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface CustomStudentRepo {
    List<Student> findAllByTenSV(Criteria criteria);
}
