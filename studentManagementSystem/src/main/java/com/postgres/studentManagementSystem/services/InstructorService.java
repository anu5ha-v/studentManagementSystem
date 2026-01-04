package com.postgres.studentManagementSystem.services;

import com.postgres.studentManagementSystem.dto.InstructorDto;
import com.postgres.studentManagementSystem.dto.InstructorDetailsDto;

public interface InstructorService {
    InstructorDto saveInstructor(InstructorDto studentDto);

    InstructorDto updateInstructor(Long studentId, InstructorDto studentDto);

    void deleteByInstructorId(Long studnentId);

    InstructorDetailsDto getInstructorDetails(Long instructorId);

    InstructorDetailsDto registerInstructorToCourse(Long instructorId, Long courseId);

    void withdrawInstructorFromCourse(Long instructorId, Long courseId);

    Long getAllInstructorsCount();
}
