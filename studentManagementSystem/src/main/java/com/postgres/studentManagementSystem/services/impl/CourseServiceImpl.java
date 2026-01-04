package com.postgres.studentManagementSystem.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.studentManagementSystem.dto.CourseDetailDto;
import com.postgres.studentManagementSystem.dto.CourseDto;
import com.postgres.studentManagementSystem.dto.InstructorDto;
import com.postgres.studentManagementSystem.dto.StudentDto;
import com.postgres.studentManagementSystem.entity.Course;
import com.postgres.studentManagementSystem.entity.CourseState;
import com.postgres.studentManagementSystem.repository.CourseRepository;
import com.postgres.studentManagementSystem.services.CourseService;
import com.postgres.studentManagementSystem.services.CourseStateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseStateService courseStateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public CourseDto saveCourse(CourseDto courseDto) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto,course);
        Course savedCourse = courseRepository.save(course);
        CourseDto savedCourseDto = new CourseDto();
        BeanUtils.copyProperties(savedCourse, savedCourseDto);
        return savedCourseDto;
    }

    @Override
    public CourseDetailDto saveCourseDetail(CourseDetailDto courseDetailDto) {
        Course course = new Course();
        objectMapper.convertValue(courseDetailDto, Course.class);
        return objectMapper.convertValue(courseRepository.save(course), CourseDetailDto.class);
    }

    @Override
    @Transactional
    public void deleteByCourseId(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public Course findByCourseId(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDto> getStudentsByCourseId(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        List<CourseState> courseStates = courseStateService.findByCourseId(courseId);
        List<StudentDto> studentDtos = new ArrayList<>();
        if (courseStates != null && !courseStates.isEmpty()) {
            for (CourseState courseState : courseStates) {
                if (courseState.getStudent() != null) {
                    StudentDto studentDto = new StudentDto();
                    studentDto.setName(courseState.getStudent().getName());
                    studentDto.setDateOfBirth(courseState.getStudent().getDateOfBirth());
                    studentDtos.add(studentDto);
                }
            }
        }

        return studentDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorDto getInstructorByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        if (course.getInstructor() == null) {
            throw new RuntimeException("Course has no instructor assigned");
        }

        InstructorDto instructorDto = new InstructorDto();
        instructorDto.setName(course.getInstructor().getName());
        instructorDto.setDateOfBirth(course.getInstructor().getDateOfBirth());

        return instructorDto;
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }
}
