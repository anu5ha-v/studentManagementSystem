package com.training.StdMngSysMongo.service;

import com.training.StdMngSysMongo.dto.CourseProgressDto;
import com.training.StdMngSysMongo.dto.CourseStateDto;
import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.entity.Enrollments;
import com.training.StdMngSysMongo.enums.CourseStatus;

import java.util.List;
import java.util.Optional;

public interface CourseStateService {
    Optional<Enrollments> findByStudentIdAndCourseId(Long studentId, Long courseId);

    void save(Long studentId, Enrollments enrollment);

    void deleteEnrollment(Long studentId, Long courseId);

    List<CourseProgressDto> getCourseProgress(Long studentId);

    CourseStateDto updateCourseStatus(Long courseId, Long studentId, CourseStatus newStatus);

    List<Enrollments> findByCourseId(Long courseId);

    List<Enrollments> findByStudentId(Long studentId);

    Long countByCourseId(Long courseId);

    List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus);
}

