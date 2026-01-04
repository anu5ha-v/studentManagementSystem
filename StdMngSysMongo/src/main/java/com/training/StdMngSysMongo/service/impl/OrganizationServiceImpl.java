package com.training.StdMngSysMongo.service.impl;

import com.training.StdMngSysMongo.dto.CourseDetailDto;
import com.training.StdMngSysMongo.dto.CourseStudentsDto;
import com.training.StdMngSysMongo.dto.InstructorDetailsDto;
import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.entity.Courses;
import com.training.StdMngSysMongo.service.CourseService;
import com.training.StdMngSysMongo.service.CourseStateService;
import com.training.StdMngSysMongo.service.InstructorService;
import com.training.StdMngSysMongo.service.OrganizationService;
import com.training.StdMngSysMongo.service.StudentService;
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
        Courses course = courseService.findByCourseId(courseId);
        InstructorDetailsDto instructorDetailsDto = new InstructorDetailsDto();
        
        if (course.getInstructorId() != null) {
            com.training.StdMngSysMongo.dto.InstructorDto instructorDto = courseService.getInstructorByCourseId(courseId);
            instructorDetailsDto.setName(instructorDto.getName());
            instructorDetailsDto.setDateOfBirth(instructorDto.getDateOfBirth());
        }
        
        CourseStudentsDto courseStudentsDto = new CourseStudentsDto();
        courseStudentsDto.setFee(course.getFee());
        courseStudentsDto.setName(course.getCourseName());
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
        Courses course = courseService.findByCourseId(courseId);
        courseDetailDto.setName(course.getCourseName());
        courseDetailDto.setFee(course.getFee());
        if (course.getInstructorId() != null) {
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

