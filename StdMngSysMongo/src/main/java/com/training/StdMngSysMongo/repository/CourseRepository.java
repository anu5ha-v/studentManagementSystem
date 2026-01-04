package com.training.StdMngSysMongo.repository;

import com.training.StdMngSysMongo.entity.Courses;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Courses, Long> {
}

