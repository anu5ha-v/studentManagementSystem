package com.postgres.studentManagementSystem.services;

import com.postgres.studentManagementSystem.dto.CourseDetailDto;
import com.postgres.studentManagementSystem.dto.CourseDto;
import com.postgres.studentManagementSystem.dto.InstructorDto;
import com.postgres.studentManagementSystem.dto.StudentDto;
import com.postgres.studentManagementSystem.entity.Course;

import java.util.List;

public interface CourseService {
    CourseDto saveCourse(CourseDto courseDto);

    CourseDetailDto saveCourseDetail(CourseDetailDto courseDetailDto);

    void deleteByCourseId(Long courseId);

    Course findByCourseId(Long courseId);

    List<StudentDto> getStudentsByCourseId(Long courseId);

    InstructorDto getInstructorByCourseId(Long courseId);

    Course updateCourse(Course course);
}
