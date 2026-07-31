package com.mambunveasna.student_management_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @Schema(
            description = "Student's full name",
            example = "John Doe"
    )
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    @Schema(
            description = "Student's email address",
            example = "john.doe@example.com"
    )
    private String email;

    @NotNull(message = "Department is required")
    @Schema(
            description = "ID of the department the student belongs to",
            example = "1"
    )
    private Long departmentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}