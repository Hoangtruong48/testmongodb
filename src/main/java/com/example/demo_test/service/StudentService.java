package com.example.demo_test.service;

import com.example.demo_test.model.Student;
import com.example.demo_test.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    public List<Student> findAllStudentByTenSV(String tenSV) {
        if (ObjectUtils.isEmpty(tenSV)) {
            return studentRepo.findAll();
        }
        Criteria criteria = new Criteria();
        criteria.and("tenSV").is(tenSV);
        return studentRepo.findAllByTenSV(criteria);
    }
    public Student addNewStudent(Student student){
        if (ObjectUtils.isEmpty(student.getNgayTao())) {
            student.setNgayTao(String.valueOf(LocalDate.now()));
        }
        return studentRepo.save(student);
    }
}
