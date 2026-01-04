package com.training.StdMngSysMongo.service;

import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.dto.StudentDto;

public interface StudentService {
    StudentDto saveStudent(StudentDto studentDto);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);

    void deleteByStudentId(Long studnentId);

    StudentDetailsDto enrollStudent(Long studentId, Long courseId);

    StudentDetailsDto getStudentDetails(Long studentId);

    void withdrawFromTheCourse(Long studentId, Long courseId);

    Long getAllStudentsCount();
}
