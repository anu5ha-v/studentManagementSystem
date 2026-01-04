package com.training.StdMngSysMongo.service;

import com.training.StdMngSysMongo.dto.CourseDto;
import com.training.StdMngSysMongo.dto.InstructorDto;
import com.training.StdMngSysMongo.dto.StudentDto;
import com.training.StdMngSysMongo.entity.Courses;

import java.util.List;

public interface CourseService {
    CourseDto saveCourse(CourseDto courseDto);
    void deleteByCourseId(Long courseId);
    Courses findByCourseId(Long courseId);
    Courses updateCourse(Courses course);
    List<StudentDto> getStudentsByCourseId(Long courseId);
    InstructorDto getInstructorByCourseId(Long courseId);
}
