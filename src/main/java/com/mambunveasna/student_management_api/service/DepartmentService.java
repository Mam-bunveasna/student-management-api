package com.mambunveasna.student_management_api.service;
import java.util.List;

import com.mambunveasna.student_management_api.model.Department;
import com.mambunveasna.student_management_api.model.Student;
import com.mambunveasna.student_management_api.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository=departmentRepository;
    }
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
    public Department getById(Long id){
        return departmentRepository.findById(id).orElse(null);
    }
    public Department addDepartment(Department department){
        return departmentRepository.save(department);
    }
    public Department updateDepartment(Long id, Department updateDepartment){
      Department department =   departmentRepository.findById(id).orElse(null);
      if(department != null){
          department.setName(updateDepartment.getName());
          return department;
      }
      return null;
    }
    public Department deleteDepartment(Long id){
        Department department = departmentRepository.findById(id).orElse(null);
        if(department!=null) {
            departmentRepository.delete(department);
            return department;
        }
        return null;
    }
}