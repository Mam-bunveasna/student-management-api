package com.mambunveasna.student_management_api.controller;

import com.mambunveasna.student_management_api.model.Department;
import com.mambunveasna.student_management_api.model.Student;
import com.mambunveasna.student_management_api.repository.DepartmentRepository;
import com.mambunveasna.student_management_api.service.DepartmentService;
import com.mambunveasna.student_management_api.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository){

        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }
    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @GetMapping("/departments/{id}")
    public Department getById(@PathVariable Long id){

        return departmentService.getById(id);
    }
    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }
    @PutMapping("/departments/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department department ){
        return departmentService.updateDepartment(id,department);
    }
    @DeleteMapping("/department/{id}")
    public Department deleteDepartment(@PathVariable Long id){
        return departmentService.deleteDepartment(id);
    }

}