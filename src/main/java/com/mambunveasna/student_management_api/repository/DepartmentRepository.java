package com.mambunveasna.student_management_api.repository;

import com.mambunveasna.student_management_api.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
