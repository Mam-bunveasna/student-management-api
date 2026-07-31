package com.mambunveasna.student_management_api.controller;

import com.mambunveasna.student_management_api.model.Department;
import com.mambunveasna.student_management_api.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Department Management",
        description = "APIs for managing departments"
)
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @Operation(
            summary = "Get all departments",
            description = "Returns a list of all departments in the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Departments retrieved successfully."
    )
    @GetMapping("/department")
    public List<Department> getAllDepartments() {

        return departmentService.getAllDepartments();
    }


    @Operation(
            summary = "Get department by ID",
            description = "Returns a department using the given ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Department retrieved successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Department not found."
    )
    @GetMapping("/department/{id}")
    public Department getById(
            @PathVariable Long id
    ) {

        return departmentService.getById(id);
    }


    @Operation(
            summary = "Create a new department",
            description = "Creates a new department and saves it into the database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Department created successfully."
    )
    @PostMapping("/department")
    public Department addDepartment(
            @Valid @RequestBody Department department
    ) {

        return departmentService.addDepartment(department);
    }


    @Operation(
            summary = "Update department",
            description = "Updates an existing department."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Department updated successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Department not found."
    )
    @PutMapping("/department/{id}")
    public Department updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody Department department
    ) {

        return departmentService.updateDepartment(id, department);
    }


    @Operation(
            summary = "Delete department",
            description = "Deletes a department using the given ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Department deleted successfully."
    )
    @ApiResponse(
            responseCode = "404",
            description = "Department not found."
    )
    @DeleteMapping("/department/{id}")
    public Department deleteDepartment(
            @PathVariable Long id
    ) {

        return departmentService.deleteDepartment(id);
    }
}