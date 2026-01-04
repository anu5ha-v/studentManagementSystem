package com.training.StdMngSysMongo.service;

import com.training.StdMngSysMongo.dto.InstructorDetailsDto;
import com.training.StdMngSysMongo.dto.InstructorDto;

public interface InstructorService {
    InstructorDto saveInstructor(InstructorDto studentDto);

    InstructorDto updateInstructor(Long studentId, InstructorDto studentDto);

    void deleteByInstructorId(Long studnentId);

    InstructorDetailsDto getInstructorDetails(Long instructorId);

    InstructorDetailsDto registerInstructorToCourse(Long instructorId, Long courseId);

    void withdrawInstructorFromCourse(Long instructorId, Long courseId);

    Long getAllInstructorsCount();
}
