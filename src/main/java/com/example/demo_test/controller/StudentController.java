package com.example.demo_test.controller;

import com.dts.tsdc.common.domain.response.BaseResponse;
import com.dts.tsdc.common.domain.response.GetArrayResponse;
import com.example.demo_test.model.Student;
import com.example.demo_test.response.GetSingleItemResponse;
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

    @GetMapping("/getByKhoaAndOrder")
    public BaseResponse getAllStudentByKhoaAndOrder(@RequestParam String khoa){
        GetArrayResponse<Student> response = new GetArrayResponse<>();
        List<Student> result = studentService.findAllByKhoaAndOrderByTenSV(khoa);
        if (result.isEmpty()){
            response.setFailed("Khong tim thay sinh vien");
        } else {
            response.setSuccess(result, result.size());
        }
        return response;
    }

    @PostMapping("/add10000Students")
    public BaseResponse add10000Students() {
        studentService.add10000Student();
        BaseResponse response = new BaseResponse();
        response.setSuccess();
        return response;
    }

    @GetMapping("/findStudentLimit")
    public BaseResponse findStudentLimit(@RequestParam String khoa, @RequestParam int start, @RequestParam int limit) {
        GetArrayResponse<Student> response = new GetArrayResponse<>();
        List<Student> result = studentService.findStudentByKhoaLimit(khoa, start, limit);
        if (result.isEmpty()){
            response.setFailed("Khong tim thay sinh vien thuoc khoa " + khoa);
        } else {
            response.setSuccess(result, result.size());
        }
        return response;
    }

    @PostMapping("/updateStudentByKhoaAndKhoaHoc")
    public BaseResponse updateStudentByKhoaAndKhoaHoc(@RequestParam String khoa,
                                                      @RequestParam Integer khoaHoc) {
        GetSingleItemResponse<Long> response = new GetSingleItemResponse<>();
        Long count = studentService.updateStudentByKhoaAndKhoaHoc(khoa, khoaHoc);
        if (count == 0){
            response.setFailed("Không có sinh viên nào được cập nhật");
        } else {
            response.setSuccess(count);
        }
        return response;
    }
}
