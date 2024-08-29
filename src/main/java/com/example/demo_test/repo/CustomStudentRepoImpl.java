package com.example.demo_test.repo;

import com.example.demo_test.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
@Repository
public class CustomStudentRepoImpl implements CustomStudentRepo {

    private MongoTemplate mongoTemplate;
    @Autowired
    public CustomStudentRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public List<Student> findAllByTenSV(Criteria criteria) {
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Student.class);
    }
}
