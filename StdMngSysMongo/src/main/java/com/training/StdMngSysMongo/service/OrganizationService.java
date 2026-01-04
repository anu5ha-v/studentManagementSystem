package com.training.StdMngSysMongo.service;

import com.training.StdMngSysMongo.dto.CourseDetailDto;
import com.training.StdMngSysMongo.dto.InstructorDetailsDto;
import com.training.StdMngSysMongo.dto.StudentDetailsDto;

import java.util.List;

public interface OrganizationService {

    Long getAllStudents();

    Long getStudentsByCourseId(Long courseId);

    InstructorDetailsDto getInstructorDetails(Long courseId);

    Long getTotalInstructorCount();

    CourseDetailDto getAllDetails(Long couseId);

    List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus);
}

