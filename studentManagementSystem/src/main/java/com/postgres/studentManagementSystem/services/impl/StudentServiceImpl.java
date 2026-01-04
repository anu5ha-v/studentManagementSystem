package com.postgres.studentManagementSystem.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.studentManagementSystem.dto.*;
import com.postgres.studentManagementSystem.entity.Course;
import com.postgres.studentManagementSystem.entity.CourseState;
import com.postgres.studentManagementSystem.entity.Student;
import com.postgres.studentManagementSystem.enums.CourseStates;
import com.postgres.studentManagementSystem.repository.StudentRepository;
import com.postgres.studentManagementSystem.services.CourseService;
import com.postgres.studentManagementSystem.services.CourseStateService;
import com.postgres.studentManagementSystem.services.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseStateService courseStateService;

    @Override
    @Transactional
    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = objectMapper.convertValue(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        return objectMapper.convertValue(savedStudent, StudentDto.class);
    }

    @Override
    @Transactional
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        if(student == null){
            throw new RuntimeException("Student is not found with id " + studentId);
        }
        BeanUtils.copyProperties(studentDto, student, "id");
        Student updatedStudent = studentRepository.save(student);
        return convertToDto(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteByStudentId(Long studnentId) {
        studentRepository.deleteById(studnentId);
    }

    @Override
    @Transactional
    public StudentDetailsDto getStudentDetails(Long studentId) {
        // Build DTO with courses from CourseState
        return buildStudentDetailsDto(studentId);
    }

    @Override
    public void withdrawFromTheCourse(Long studentId, Long courseId) {
        Optional<CourseState> enrollment = courseStateService.findByStudentIdAndCourseId(studentId, courseId);
        if (enrollment.isEmpty()) {
            throw new RuntimeException("Student is not enrolled in this course!");
        }
        courseStateService.deleteEnrollment(enrollment.orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public StudentDetailsDto enrollStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        Course course = courseService.findByCourseId(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }

        Optional<CourseState> enrollment = courseStateService.findByStudentIdAndCourseId(studentId, courseId);
        if (enrollment.isPresent()) {
            throw new RuntimeException("Student is already enrolled in this course!");
        }

        CourseState courseState = new CourseState();
        courseState.setStudent(student);
        courseState.setCourse(course);
        courseState.setCourseStates(CourseStates.TO_DO);
        courseStateService.save(courseState);

        return buildStudentDetailsDto(studentId);
    }

    private StudentDetailsDto buildStudentDetailsDto(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        StudentDetailsDto studentDetailsDto = new StudentDetailsDto();
        studentDetailsDto.setName(student.getName());
        studentDetailsDto.setDateOfBirth(student.getDateOfBirth());
        
        List<CourseState> courseStates = courseStateService.findByStudentId(studentId);
        
        List<CourseDto> courseDtos = new ArrayList<>();
        if (courseStates != null && !courseStates.isEmpty()) {
            for (CourseState courseState : courseStates) {
                Course course = courseState.getCourse();
                CourseDto courseDto = new CourseDto();
                courseDto.setName(course.getName());
                courseDto.setFee(course.getFee());
                courseDtos.add(courseDto);
            }
        }
        
        studentDetailsDto.setCourses(courseDtos);
        
        return studentDetailsDto;
    }

    @Override
    public Long getAllStudentsCount() {
        return studentRepository.count();
    }

    private StudentDto convertToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);
        return studentDto;
    }
}
