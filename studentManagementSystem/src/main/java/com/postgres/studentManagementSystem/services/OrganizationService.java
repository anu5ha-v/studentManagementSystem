package com.postgres.studentManagementSystem.services;

import com.postgres.studentManagementSystem.dto.CourseDetailDto;
import com.postgres.studentManagementSystem.dto.InstructorDetailsDto;
import com.postgres.studentManagementSystem.dto.StudentDetailsDto;

import java.util.List;

public interface OrganizationService {

    Long getAllStudents();

    Long getStudentsByCourseId(Long courseId);

    InstructorDetailsDto getInstructorDetails(Long courseId);

    Long getTotalInstructorCount();

    CourseDetailDto getAllDetails(Long couseId);

    List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus);
}
