package com.example.demo_test.controller;

import com.dts.tsdc.common.domain.response.BaseResponse;
import com.dts.tsdc.common.domain.response.GetArrayResponse;
import com.example.demo_test.model.Student;
import com.example.demo_test.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hocsinh")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @GetMapping("/getAll")
    public BaseResponse getStudent(@RequestParam String tenSV) {
        GetArrayResponse<Student> response = new GetArrayResponse<>();
        List<Student> result = studentService.findAllStudentByTenSV(tenSV);
        if (result.isEmpty()){
            response.setFailed("Khong tim thay sinh vien nao");
        } else {
            response.setSuccess(result, result.size());
        }
        return response;
    }
    @PostMapping("/addOne")
    public Student createStudent(@RequestBody Student student) {
        return studentService.addNewStudent(student);
    }

}
