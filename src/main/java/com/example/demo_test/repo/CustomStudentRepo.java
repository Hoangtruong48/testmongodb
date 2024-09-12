package com.example.demo_test.repo;

import com.example.demo_test.model.Student;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomStudentRepo {
    List<Student> findAllByTenSV(String tenSV);
    List<Student> findALlByKhoaAndOrderByTenSV(String khoa);
    void add10000StudentToDB();
    List<Student> getStudentByKhoaPageable(String khoa, int start, int limit);
    long updateStudentByKhoaAndKhoaHoc(String khoa, Integer khoahoc);
}
