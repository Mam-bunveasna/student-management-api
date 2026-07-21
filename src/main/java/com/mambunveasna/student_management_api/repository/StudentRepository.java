package com.mambunveasna.student_management_api.repository;

import com.mambunveasna.student_management_api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> id(Long id);
}
