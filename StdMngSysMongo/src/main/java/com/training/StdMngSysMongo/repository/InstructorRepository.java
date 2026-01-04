package com.training.StdMngSysMongo.repository;

import com.training.StdMngSysMongo.entity.Instructors;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends MongoRepository<Instructors, Long> {
    
    Optional<Instructors> findByCourseId(Long courseId);
}

