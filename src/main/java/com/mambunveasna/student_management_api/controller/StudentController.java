package com.mambunveasna.student_management_api.controller;

import com.mambunveasna.student_management_api.dto.StudentRequestDTO;
import com.mambunveasna.student_management_api.dto.StudentResponseDTO;
import com.mambunveasna.student_management_api.model.Student;
import com.mambunveasna.student_management_api.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Tag(
        name = "Student Management",
        description = "APIs for managing students"
)
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @Operation(
            summary = "Get all students",
            description = "Returns a list of all students in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Students retrieved successfully."
    )
    @GetMapping("/student")
    public List<StudentResponseDTO> getAllStudents() {

        return studentService.getAllStudents();
    }


    @Operation(
            summary = "Get student by ID",
            description = "Returns a student using the given ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Student retrieved successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Student not found."
    )
    @GetMapping("/student/{id}")
    public StudentResponseDTO getStudent(@PathVariable Long id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH USER = " + authentication.getName());
        System.out.println("AUTH STATUS = " + authentication.isAuthenticated());
        System.out.println("AUTHORITIES = " + authentication.getAuthorities());

        return studentService.getById(id);
    }

    @Operation(
            summary = "Update student information",
            description = "Updates an existing student's information."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Student updated successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Student not found."
    )
    @PutMapping("/student/{id}")
    public StudentResponseDTO updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO studentRequestDTO
    ) {

        return studentService.updateStudent(id, studentRequestDTO);
    }


    @Operation(
            summary = "Create a new student",
            description = "Creates a new student and saves it into the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Student created successfully."
    )
    @PostMapping("/student")
    public StudentResponseDTO addStudent(
            @Valid @RequestBody StudentRequestDTO dto
    ) {

        return studentService.addStudent(dto);
    }
    @Operation(
            summary = "Delete student",
            description = "Deletes a student using the given ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Student deleted successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Student not found."
    )
    @DeleteMapping("/student/{id}")
    public Student deleteStudent(
            @PathVariable Long id
    ) {

        return studentService.deleteStudent(id);
    }
}