package com.mambunveasna.student_management_api.controller;

import com.mambunveasna.student_management_api.dto.StudentRequestDTO;
import com.mambunveasna.student_management_api.model.Department;
import com.mambunveasna.student_management_api.model.Student;
import com.mambunveasna.student_management_api.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mambunveasna.student_management_api.dto.StudentResponseDTO;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentservice;


    public StudentController(StudentService studentservice) {

        this.studentservice = studentservice;

    }

    @GetMapping("/student")
    public List<StudentResponseDTO> getAllStudents() {

        return studentservice.getAllStudents();
    }

    @GetMapping("/student/{id}")
    public StudentResponseDTO getById(@PathVariable Long id) {
        return studentservice.getById(id);
    }

    @PutMapping("/student/{id}")
    public StudentResponseDTO updateStudent(@PathVariable Long id ,@RequestBody StudentRequestDTO studentRequestDTO ){
        return studentservice.updateStudent(id, studentRequestDTO);
    }
    @PostMapping("/student")
    public StudentResponseDTO addStudent(@RequestBody StudentRequestDTO dto){
        return studentservice.addStudent(dto);
    }
    @DeleteMapping("/student/{id}")
    public String  deleteStudent(@PathVariable Long id){
        return studentservice.deleteStudent(id);
    }




}