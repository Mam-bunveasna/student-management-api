package com.mambunveasna.student_management_api.service;

import com.mambunveasna.student_management_api.exception.DepartmentNotFoundException;
import com.mambunveasna.student_management_api.exception.StudentNotFoundException;
import com.mambunveasna.student_management_api.model.Student;
import com.mambunveasna.student_management_api.repository.DepartmentRepository;
import com.mambunveasna.student_management_api.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.mambunveasna.student_management_api.dto.StudentRequestDTO;
import com.mambunveasna.student_management_api.model.Department;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.ArgumentCaptor;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldReturnAllStudents() {

        // Arrange
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("John");
        student1.setEmail("john@gmail.com");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("David");
        student2.setEmail("david@gmail.com");

        when(studentRepository.findAll())
                .thenReturn(List.of(student1, student2));

        // Act
        var result = studentService.getAllStudents();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getName());
        assertEquals("David", result.get(1).getName());
    }
    @Test
    void shouldReturnStudentById() {

        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setEmail("john@gmail.com");

        when(studentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(student));

        // Act
        var result = studentService.getById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
        assertEquals("john@gmail.com", result.getEmail());
    }
    @Test
    void shouldThrowExceptionWhenStudentNotFound() {

        // Arrange
        when(studentRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        // Act + Assert
        StudentNotFoundException exception  =
                org.junit.jupiter.api.Assertions.assertThrows(
                        StudentNotFoundException.class,
                        () -> studentService.getById(99L)
                );
        assertEquals(
                "Student with id 99 not found",
                exception.getMessage()
        );
    }
    @Test
    void shouldAddStudent() {

        // Arrange

        StudentRequestDTO request = new StudentRequestDTO();
        request.setName("Alex");
        request.setEmail("alex@gmail.com");
        request.setDepartmentId(1L);

        Department department = new Department();
        department.setId(1L);
        department.setName("Computer Science");

        when(departmentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(department));

        Student savedStudent = new Student();
        savedStudent.setId(1L);
        savedStudent.setName("Alex");
        savedStudent.setEmail("alex@gmail.com");
        savedStudent.setDepartment(department);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(savedStudent);

        // Act

        var result = studentService.addStudent(request);

        // Assert

        assertEquals(1L, result.getId());
        assertEquals("Alex", result.getName());
        assertEquals("alex@gmail.com", result.getEmail());
        assertEquals("Computer Science", result.getDepartmentName());
        // Verify
        verify(studentRepository).save(any(Student.class));
    }
    @Test
    void shouldThrowExceptionWhenDepartmentNotFound() {

        // Arrange

        Student student = new Student();
        student.setId(1L);
        student.setName("Alex");
        student.setEmail("alex@gmail.com");

        when(studentRepository.findById(1L))
                .thenReturn(java.util.Optional.of(student));

        StudentRequestDTO request = new StudentRequestDTO();
        request.setName("Alex");
        request.setEmail("alex@gmail.com");
        request.setDepartmentId(99L);

        when(departmentRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        // Act + Assert

        DepartmentNotFoundException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        DepartmentNotFoundException.class,
                        () -> studentService.updateStudent(1L, request)
                );

        assertEquals(
                "Department not found",
                exception.getMessage()
        );
    }
    @Test
    void shouldUpdateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Alex");
        student.setEmail("alex@gmail.com");
         when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        StudentRequestDTO request = new StudentRequestDTO();
        request.setName("Alex Updated");
        request.setEmail("alex.updated@gmail.com");
        request.setDepartmentId(2L);
        Department department = new Department();
        department.setId(2L);
        department.setName("Software Engineering");
        when(departmentRepository.findById(2L))
                .thenReturn(Optional.of(department));
        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);
        var result = studentService.updateStudent(1L, request);

        ArgumentCaptor<Student> studentCaptor =
                ArgumentCaptor.forClass(Student.class);

        assertEquals("Alex Updated", result.getName());
        assertEquals("alex.updated@gmail.com", result.getEmail());

        verify(studentRepository).save(studentCaptor.capture());
        Student capturedStudent = studentCaptor.getValue();
        assertEquals("Alex Updated", capturedStudent.getName());
        assertEquals("alex.updated@gmail.com", capturedStudent.getEmail());
    }
    @Test
    void shouldDeleteStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Alex");
        student.setEmail("alex@gmail.com");
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));
        studentService.deleteStudent(1L);

        // Assert
        verify(studentRepository).delete(student);
    }
    @Test
    void shouldThrowExceptionWhenDeletingStudentNotFound() {

        // Arrange
        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        StudentNotFoundException exception =
                org.junit.jupiter.api.Assertions.assertThrows(
                        StudentNotFoundException.class,
                        () -> studentService.deleteStudent(99L)
                );

        assertEquals(
                "Student 99 not found!",
                exception.getMessage()
        );
    }
}
 