package com.example.demo_test.test;

import com.example.demo_test.Util.MappingService;
import com.example.demo_test.model.Student;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MappingServiceTest {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<Student>();
        int cnt = 1;
        for (int i = 0; i < 10; i++){
            Student studenInsert = Student
                    .builder()
                    .maSV(String.valueOf(cnt++))
                    .tenSV("Thanh")
                    .khoa("CNTT")
                    .diaChi("null_value")
                    .ngayTao(String.valueOf(LocalDateTime.now()))
                    .ngayCapNhat(null)
                    .khoaHoc(17)
                    .build();
            students.add(studenInsert);
        }
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("------DEBUG-----");
        MappingService<Student> studentMappingService = new MappingService<>();
        students.parallelStream().forEach(studentMappingService::remapEmptyValue);
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
