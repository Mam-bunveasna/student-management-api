package com.mambunveasna.student_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StudentRequestDTO {


        @NotBlank(message = "Name cannot be blank")
        private String name;

        @Email(message = "Email should be valid")
        private String email;

        @NotNull(message = "Department is required")
        private Long departmentId;

        // getters and setters...
    

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
