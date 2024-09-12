package com.example.demo_test.service;


import com.example.demo_test.model.Student;
import com.example.demo_test.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    public List<Student> findAllStudentByTenSV(String tenSV) {
        if (ObjectUtils.isEmpty(tenSV)) {
            return studentRepo.findAll();
        }

        return studentRepo.findAllByTenSV(tenSV);
    }
    public Student addNewStudent(Student student){
        if (ObjectUtils.isEmpty(student.getNgayTao())) {
            student.setNgayTao(String.valueOf(LocalDate.now()));
        }
        return studentRepo.save(student);
    }
    public List<Student> findAllByKhoaAndOrderByTenSV(String khoa){
        if (ObjectUtils.isEmpty(khoa)) {
            return studentRepo.findAll(Sort.by(Sort.Direction.ASC, "tenSV"));
        }

        return studentRepo.findALlByKhoaAndOrderByTenSV(khoa);
    }

    public void add10000Student(){
        studentRepo.add10000StudentToDB();
    }

    public List<Student> findStudentByKhoaLimit(String khoa, int start, int limit){
        return studentRepo.getStudentByKhoaPageable(khoa, start, limit);
    }

    public Long updateStudentByKhoaAndKhoaHoc(String khoa, Integer khoaHoc){
        return studentRepo.updateStudentByKhoaAndKhoaHoc(khoa, khoaHoc);
    }
    public static int minimumNumber(int n, String password) {
        boolean isLower = false, isUpper = false, isNum = false, isSpe = false;
        Set<Character> specialChars = new HashSet<>(Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+'));
        for (int i = 0; i < n; i++){
            if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z'){
                isLower = true;
            }
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z'){
                isUpper = true;
            }
            if (password.charAt(i) >= '0' && password.charAt(i) <= '9'){
                isNum = true;
            }
            if (specialChars.contains(password.charAt(i))){
                isSpe = true;
            }
        }
        int cnt = 0;
        if (!isLower){
            cnt++;
            n++;
        }
        if (!isUpper){
            cnt++;
            n++;
        }
        if (!isNum){
            cnt++;
            n++;
        }
        if (!isSpe){
            cnt++;
            n++;
        }
        if (n < 6){
            cnt += (6 - n);
        }
        return cnt;
    }

    public static void main(String[] args) {
        System.out.println("Equal".equalsIgnoreCase("equal"));
    }
}
