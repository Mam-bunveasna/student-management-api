package com.mambunveasna.student_management_api.controller;

import com.mambunveasna.student_management_api.dto.StudentRequestDTO;
import com.mambunveasna.student_management_api.dto.StudentResponseDTO;
import com.mambunveasna.student_management_api.exception.StudentNotFoundException;
import com.mambunveasna.student_management_api.model.Student;
import com.mambunveasna.student_management_api.service.StudentService;
import com.mambunveasna.student_management_api.service.CustomUserDetailsService;
import com.mambunveasna.student_management_api.service.JwtService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
@WithMockUser(username = "testuser")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;


    @Test
    void shouldGetAllStudents() throws Exception {

        StudentResponseDTO student = new StudentResponseDTO();
        student.setId(1L);
        student.setName("Alex");
        student.setEmail("alex@gmail.com");

        when(studentService.getAllStudents())
                .thenReturn(List.of(student));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[0].email").value("alex@gmail.com"));
    }


    @Test
    void shouldGetStudentById() throws Exception {

        StudentResponseDTO student = new StudentResponseDTO();
        student.setId(1L);
        student.setName("Alex");
        student.setEmail("alex@gmail.com");

        when(studentService.getById(1L))
                .thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@gmail.com"));
    }


    @Test
    void shouldReturn404WhenStudentNotFound() throws Exception {

        when(studentService.getById(99L))
                .thenThrow(
                        new StudentNotFoundException(
                                "Student with id 99 not found"
                        )
                );

        mockMvc.perform(get("/student/99"))
                .andExpect(status().isNotFound());
    }


    @Test
    void shouldAddStudent() throws Exception {

        StudentResponseDTO response = new StudentResponseDTO();
        response.setId(1L);
        response.setName("Alex");
        response.setEmail("alex@gmail.com");
        response.setDepartmentName("Computer Science");

        when(studentService.addStudent(any(StudentRequestDTO.class)))
                .thenReturn(response);

        String requestBody = """
                {
                    "name": "Alex",
                    "email": "alex@gmail.com",
                    "departmentId": 1
                }
                """;

        mockMvc.perform(
                        post("/student")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@gmail.com"))
                .andExpect(jsonPath("$.departmentName")
                        .value("Computer Science"));
    }


    @Test
    void shouldUpdateStudent() throws Exception {

        StudentResponseDTO response = new StudentResponseDTO();
        response.setId(1L);
        response.setName("Alex Updated");
        response.setEmail("alex.updated@gmail.com");
        response.setDepartmentName("Software Engineering");

        when(studentService.updateStudent(
                eq(1L),
                any(StudentRequestDTO.class)
        )).thenReturn(response);

        String requestBody = """
                {
                    "name": "Alex Updated",
                    "email": "alex.updated@gmail.com",
                    "departmentId": 2
                }
                """;

        mockMvc.perform(
                        put("/student/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name")
                        .value("Alex Updated"))
                .andExpect(jsonPath("$.email")
                        .value("alex.updated@gmail.com"))
                .andExpect(jsonPath("$.departmentName")
                        .value("Software Engineering"));
    }


    @Test
    void shouldDeleteStudent() throws Exception {

        Student student = new Student();
        student.setId(1L);
        student.setName("Alex");
        student.setEmail("alex@gmail.com");

        when(studentService.deleteStudent(1L))
                .thenReturn(student);

        mockMvc.perform(
                        delete("/student/1")
                                .with(csrf())
                )
                .andExpect(status().isOk());

        verify(studentService).deleteStudent(1L);
    }
}