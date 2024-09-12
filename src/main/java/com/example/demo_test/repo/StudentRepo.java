package com.example.demo_test.repo;

import com.example.demo_test.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface StudentRepo extends MongoRepository<Student, String>, CustomStudentRepo {
}
