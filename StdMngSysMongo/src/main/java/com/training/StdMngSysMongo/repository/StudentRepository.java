package com.training.StdMngSysMongo.repository;

import com.training.StdMngSysMongo.entity.Students;
import com.training.StdMngSysMongo.enums.CourseStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Students, Long> {
    
    List<Students> findByEnrollmentsCourseId(Long courseId);
    
    List<Students> findByEnrollmentsCourseStatus(CourseStatus courseStatus);
}

