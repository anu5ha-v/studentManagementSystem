package com.postgres.studentManagementSystem.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.studentManagementSystem.dto.*;
import com.postgres.studentManagementSystem.entity.Course;
import com.postgres.studentManagementSystem.services.CourseService;
import com.postgres.studentManagementSystem.services.CourseStateService;
import com.postgres.studentManagementSystem.services.InstructorService;
import com.postgres.studentManagementSystem.services.OrganizationService;
import com.postgres.studentManagementSystem.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    StudentService studentService;

    @Autowired
    CourseStateService courseStateService;

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorService instructorService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Long getAllStudents() {
        return studentService.getAllStudentsCount();
    }

    @Override
    public Long getStudentsByCourseId(Long courseId) {
        return courseStateService.countByCourseId(courseId);
    }

    @Override
    public InstructorDetailsDto getInstructorDetails(Long courseId) {
        Course course = courseService.findByCourseId(courseId);
        InstructorDetailsDto instructorDetailsDto = new InstructorDetailsDto();
        instructorDetailsDto.setName(course.getInstructor().getName());
        instructorDetailsDto.setDateOfBirth(course.getInstructor().getDateOfBirth());
        CourseStudentsDto courseStudentsDto = new CourseStudentsDto();
        courseStudentsDto.setFee(course.getFee());
        courseStudentsDto.setName(course.getName());
        courseStudentsDto.setStudentsCount(courseStateService.countByCourseId(courseId));
        instructorDetailsDto.setCourse(courseStudentsDto);
        return instructorDetailsDto;
    }

    @Override
    public Long getTotalInstructorCount() {
        return instructorService.getAllInstructorsCount();
    }

    @Override
    public CourseDetailDto getAllDetails(Long courseId) {
        CourseDetailDto courseDetailDto = new CourseDetailDto();
        Course course = courseService.findByCourseId(courseId);
        courseDetailDto.setName(course.getName());
        courseDetailDto.setFee(course.getFee());
        if (course.getInstructor() != null) {
            courseDetailDto.setInstructor(courseService.getInstructorByCourseId(courseId));
        }
        courseDetailDto.setStudents(courseService.getStudentsByCourseId(courseId));
        return courseDetailDto;
    }

    @Override
    public List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus) {
        return courseStateService.getStudentsByCourseStatus(courseStatus);
    }
}
