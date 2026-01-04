package com.postgres.studentManagementSystem.services;

import com.postgres.studentManagementSystem.dto.CourseProgressDto;
import com.postgres.studentManagementSystem.dto.CourseStateDto;
import com.postgres.studentManagementSystem.dto.StudentDetailsDto;
import com.postgres.studentManagementSystem.entity.CourseState;
import com.postgres.studentManagementSystem.enums.CourseStates;

import java.util.List;
import java.util.Optional;

public interface CourseStateService {
    Optional<CourseState> findByStudentIdAndCourseId(Long studentId, Long courseId);

    void save(CourseState courseState);

    void deleteEnrollment(CourseState enrollment);

    List<CourseProgressDto> getCourseProgress(Long studentId);

    CourseStateDto updateCourseStatus(Long courseId, Long studentId, CourseStates newStatus);

    List<CourseState> findByCourseId(Long courseId);

    List<CourseState> findByStudentId(Long studentId);

    Long countByCourseId(Long courseId);

    List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus);
}
