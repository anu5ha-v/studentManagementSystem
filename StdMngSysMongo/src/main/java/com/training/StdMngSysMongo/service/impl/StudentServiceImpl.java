package com.training.StdMngSysMongo.service.impl;

import com.training.StdMngSysMongo.dto.CourseDto;
import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.dto.StudentDto;
import com.training.StdMngSysMongo.entity.Courses;
import com.training.StdMngSysMongo.entity.Enrollments;
import com.training.StdMngSysMongo.entity.Students;
import com.training.StdMngSysMongo.enums.CourseStatus;
import com.training.StdMngSysMongo.exception.BusinessException;
import com.training.StdMngSysMongo.exception.ResourceNotFoundException;
import com.training.StdMngSysMongo.exception.ValidationException;
import com.training.StdMngSysMongo.repository.StudentRepository;
import com.training.StdMngSysMongo.service.CourseService;
import com.training.StdMngSysMongo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private CourseService courseService;

    @Override
    @CachePut(value = "student", key = "#result.studentId")
    public StudentDto saveStudent(StudentDto studentDto) {
        if (studentDto == null || studentDto.getName() == null || studentDto.getName().trim().isEmpty()) {
            throw new ValidationException("Student name is required and cannot be empty");
        }
        Students student = new Students();
        student.setStudentName(studentDto.getName());
        student.setStudentDateOfBirth(studentDto.getDateOfBirth());
        student.setEnrollments(new ArrayList<>());
        Students savedStudent = studentRepository.save(student);
        StudentDto savedStudentDto = new StudentDto();
        savedStudentDto.setStudentId(savedStudent.getStudentId());
        savedStudentDto.setName(savedStudent.getStudentName());
        savedStudentDto.setDateOfBirth(savedStudent.getStudentDateOfBirth());
        return savedStudentDto;
    }

    @Override
    @CacheEvict(value="student", key="#studentId")
    @CachePut(value = "student", key = "#studentId")
    public StudentDto updateStudent(Long studentId, StudentDto studentDto) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));
        student.setStudentName(studentDto.getName());
        student.setStudentDateOfBirth(studentDto.getDateOfBirth());
        Students updatedStudent = studentRepository.save(student);
        return convertToDto(updatedStudent);
    }

    @Override
    @CacheEvict(value = "student", key="#studnentId")
    public void deleteByStudentId(Long studnentId) {
        if (!studentRepository.existsById(studnentId)) {
            throw new ResourceNotFoundException("Student", studnentId);
        }
        studentRepository.deleteById(studnentId);
    }

    @Override
    @Cacheable(value = "student", key = "#studentId")
    public StudentDetailsDto getStudentDetails(Long studentId) {
        return buildStudentDetailsDto(studentId);
    }

    @Override
    @CacheEvict(value = "student", key = "#studentId")
    public void withdrawFromTheCourse(Long studentId, Long courseId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));

        if (student.getEnrollments() == null || student.getEnrollments().isEmpty()) {
            throw new BusinessException("Student is not enrolled in any course!");
        }

        boolean removed = student.getEnrollments().removeIf(
                enrollment -> enrollment.getCourseId().equals(courseId)
        );

        if (!removed) {
            throw new ResourceNotFoundException("Student is not enrolled in this course");
        }

        studentRepository.save(student);
    }

    @Override
    @CacheEvict(value = "student", key = "#studentId")
    public StudentDetailsDto enrollStudent(Long studentId, Long courseId) {
        try {
            Students student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));

            Courses course = courseService.findByCourseId(courseId);
            if (course == null) {
                throw new ResourceNotFoundException("Course", courseId);
            }

            if (student.getEnrollments() == null) {
                student.setEnrollments(new ArrayList<>());
            }

            boolean alreadyEnrolled = student.getEnrollments().stream()
                    .anyMatch(enrollment -> enrollment.getCourseId().equals(courseId));

            if (alreadyEnrolled) {
                throw new BusinessException("Student is already enrolled in this course!");
            }

            Enrollments enrollment = new Enrollments();
            enrollment.setCourseId(course.getCourseId());
            enrollment.setCourseName(course.getCourseName());
            enrollment.setCourseFee(course.getFee());
            enrollment.setCourseStatus(CourseStatus.TO_DO);

            student.getEnrollments().add(enrollment);
            studentRepository.save(student);

            return buildStudentDetailsDto(studentId);
        } catch (ResourceNotFoundException | BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while enrolling student: " + e.getMessage(), e);
        }
    }

    private StudentDetailsDto buildStudentDetailsDto(Long studentId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));

        StudentDetailsDto studentDetailsDto = new StudentDetailsDto();
        studentDetailsDto.setName(student.getStudentName());
        studentDetailsDto.setDateOfBirth(student.getStudentDateOfBirth());

        List<CourseDto> courseDtos = new ArrayList<>();
        if (student.getEnrollments() != null && !student.getEnrollments().isEmpty()) {
            for (Enrollments enrollment : student.getEnrollments()) {
                CourseDto courseDto = new CourseDto();
                courseDto.setName(enrollment.getCourseName());
                courseDto.setFee(enrollment.getCourseFee());
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

    private StudentDto convertToDto(Students student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setName(student.getStudentName());
        studentDto.setDateOfBirth(student.getStudentDateOfBirth());
        return studentDto;
    }
}

