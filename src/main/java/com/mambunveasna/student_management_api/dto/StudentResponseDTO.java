package com.mambunveasna.student_management_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class StudentResponseDTO {

    @Schema(
            description = "Unique ID of the student",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Student's email address",
            example = "john.doe@example.com"
    )
    private String email;

    @Schema(
            description = "Student's full name",
            example = "John Doe"
    )
    private String name;

    @Schema(
            description = "Name of the department the student belongs to",
            example = "Computer Science"
    )
    private String departmentName;

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(Long id, String email, String name, String departmentName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}