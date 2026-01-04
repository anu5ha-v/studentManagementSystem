package com.training.StdMngSysMongo.service.impl;

import com.training.StdMngSysMongo.dto.CourseDto;
import com.training.StdMngSysMongo.dto.InstructorDto;
import com.training.StdMngSysMongo.dto.StudentDto;
import com.training.StdMngSysMongo.entity.Courses;
import com.training.StdMngSysMongo.entity.Instructors;
import com.training.StdMngSysMongo.entity.Students;
import com.training.StdMngSysMongo.exception.ResourceNotFoundException;
import com.training.StdMngSysMongo.exception.ValidationException;
import com.training.StdMngSysMongo.repository.CourseRepository;
import com.training.StdMngSysMongo.repository.InstructorRepository;
import com.training.StdMngSysMongo.repository.StudentRepository;
import com.training.StdMngSysMongo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public CourseDto saveCourse(CourseDto courseDto) {
        if (courseDto == null || courseDto.getName() == null || courseDto.getName().trim().isEmpty()) {
            throw new ValidationException("Course name is required and cannot be empty");
        }
        if (courseDto.getFee() == null || courseDto.getFee() < 0) {
            throw new ValidationException("Course fee must be a positive number");
        }
        Courses course = new Courses();
        course.setCourseName(courseDto.getName());
        course.setFee(courseDto.getFee());
        Courses savedCourse = courseRepository.save(course);
        CourseDto savedCourseDto = new CourseDto();
        savedCourseDto.setName(savedCourse.getCourseName());
        savedCourseDto.setFee(savedCourse.getFee());
        return savedCourseDto;
    }

    @Override
    public void deleteByCourseId(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public Courses findByCourseId(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId));
    }

    @Override
    public Courses updateCourse(Courses course) {
        return courseRepository.save(course);
    }

    @Override
    public List<StudentDto> getStudentsByCourseId(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId));

        List<Students> studentsWithCourse = studentRepository.findByEnrollmentsCourseId(courseId);

        List<StudentDto> studentDtos = new ArrayList<>();
        if (studentsWithCourse != null && !studentsWithCourse.isEmpty()) {
            for (Students student : studentsWithCourse) {
                if (student != null) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setName(student.getStudentName());
                    studentDto.setDateOfBirth(student.getStudentDateOfBirth());
                    studentDtos.add(studentDto);
                }
            }
        }

        return studentDtos;
    }

    @Override
    public InstructorDto getInstructorByCourseId(Long courseId) {
        Courses course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", courseId));

        if (course.getInstructorId() == null) {
            throw new ResourceNotFoundException("Course has no instructor assigned");
        }

        Instructors instructor = instructorRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found for course id: " + courseId));

        InstructorDto instructorDto = new InstructorDto();
        instructorDto.setName(instructor.getInstructorName());
        instructorDto.setDateOfBirth(instructor.getInstructorDateOfBirth());

        return instructorDto;
    }
}

