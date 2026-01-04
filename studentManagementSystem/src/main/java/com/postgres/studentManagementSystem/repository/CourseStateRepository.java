package com.postgres.studentManagementSystem.repository;

import com.postgres.studentManagementSystem.entity.CourseState;
import com.postgres.studentManagementSystem.enums.CourseStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStateRepository extends JpaRepository<CourseState, Long> {

    @Query("SELECT cs FROM CourseState cs WHERE cs.student.id = :studentId AND cs.course.id = :courseId")
    Optional<CourseState> findByStudentIdAndCourseId(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT cs FROM CourseState cs WHERE cs.student.id = :studentId")
    List<CourseState> findByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(cs) FROM CourseState cs WHERE cs.course.id = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT cs FROM CourseState cs WHERE cs.course.id = :courseId")
    List<CourseState> findByCourseId(@Param("courseId") Long courseId);

    List<CourseState> findByCourseStates(CourseStates courseStates);
}
