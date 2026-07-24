package com.mambunveasna.student_management_api.service;
import com.mambunveasna.student_management_api.dto.StudentRequestDTO;
import com.mambunveasna.student_management_api.exception.DepartmentNotFoundException;
import com.mambunveasna.student_management_api.exception.StudentNotFoundException;
import com.mambunveasna.student_management_api.repository.DepartmentRepository;
import com.mambunveasna.student_management_api.dto.StudentResponseDTO;
import com.mambunveasna.student_management_api.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mambunveasna.student_management_api.model.Student;
import java.util.ArrayList;
import com.mambunveasna.student_management_api.model.Department;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    public StudentService(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository=departmentRepository;
    }

    private StudentResponseDTO convertToDTO(Student student) {

        StudentResponseDTO dto = new StudentResponseDTO();

        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());

        if(student.getDepartment() != null){
            dto.setDepartmentName(student.getDepartment().getName());
        }

        return dto;
    }


    private Student convertToEntity(StudentRequestDTO dto) {

        Student student = new Student();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());

        Department department =
                departmentRepository.findById(dto.getDepartmentId()).orElse(null);

        student.setDepartment(department);

        return student;
    }
    public List<StudentResponseDTO> getAllStudents() {

        List<Student> students = studentRepository.findAll();

        List<StudentResponseDTO> dtos = new ArrayList<>();

        for (Student student : students) {

            StudentResponseDTO dto = convertToDTO(student);

            dtos.add(dto);
        }

        return dtos;
    }
    public StudentResponseDTO addStudent(StudentRequestDTO dto){

        Student student = convertToEntity(dto);

        student = studentRepository.save(student);

        return convertToDTO(student);
    }
    public StudentResponseDTO getById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new DepartmentNotFoundException(
                        "Student with id " + id + " not found"
                ));
        return convertToDTO(student);
    }
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO studentRequestDTO){

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student with id " + id + " not found"
                        )
                );

        student.setName(studentRequestDTO.getName());
        student.setEmail(studentRequestDTO.getEmail());

        Department department = departmentRepository.findById(studentRequestDTO.getDepartmentId())
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Department not found"
                        )
                );

        student.setDepartment(department);

        studentRepository.save(student);

        return convertToDTO(student);
    }
    public Student deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(
                ()->new StudentNotFoundException("Student"+" "+id+" "+"not found!")
        );
        studentRepository.delete(student);
        return student;
    }
}
