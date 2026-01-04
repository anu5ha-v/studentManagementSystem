package com.postgres.studentManagementSystem.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postgres.studentManagementSystem.dto.CourseProgressDto;
import com.postgres.studentManagementSystem.dto.CourseStateDto;
import com.postgres.studentManagementSystem.dto.CourseDto;
import com.postgres.studentManagementSystem.dto.StudentDetailsDto;
import com.postgres.studentManagementSystem.entity.CourseState;
import com.postgres.studentManagementSystem.enums.CourseStates;
import com.postgres.studentManagementSystem.repository.CourseStateRepository;
import com.postgres.studentManagementSystem.services.CourseStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class CourseStateServiceImpl implements CourseStateService {

    @Autowired
    CourseStateRepository courseStateRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Optional<CourseState> findByStudentIdAndCourseId(Long studentId, Long courseId) {
        return courseStateRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    @Override
    public void save(CourseState courseState) {
        courseStateRepository.save(courseState);
    }

    @Override
    public void deleteEnrollment(CourseState enrollment) {
        courseStateRepository.delete(enrollment);
    }

    @Override
    @Transactional
    public List<CourseProgressDto> getCourseProgress(Long studentId) {
        List<CourseProgressDto> courseProgressDtos = new ArrayList<>();
        List<CourseState> courseStates = courseStateRepository.findByStudentId(studentId);
        
        if (courseStates == null || courseStates.isEmpty()) {
            return courseProgressDtos;
        }
        
        for(CourseState courseState: courseStates){
            if (courseState == null || courseState.getCourse() == null) {
                continue;
            }
            
            CourseProgressDto courseProgressDto = new CourseProgressDto();
            courseProgressDto.setName(courseState.getCourse().getName());
            courseProgressDto.setFee(courseState.getCourse().getFee());
            
            if (courseState.getCourse().getInstructor() != null) {
                courseProgressDto.setInstructorName(courseState.getCourse().getInstructor().getName());
            } else {
                courseProgressDto.setInstructorName("No Instructor Assigned");
            }
            
            courseProgressDto.setCourseStates(courseState.getCourseStates());
            courseProgressDtos.add(courseProgressDto);
        }
        return courseProgressDtos;
    }

    @Override
    @Transactional
    public CourseStateDto updateCourseStatus(Long courseId, Long studentId, CourseStates newStatus) {
        if (newStatus == null) {
            throw new RuntimeException("Status cannot be null");
        }

        Optional<CourseState> courseStateOpt = courseStateRepository.findByStudentIdAndCourseId(studentId, courseId);
        
        if (courseStateOpt.isEmpty()) {
            throw new RuntimeException("Student is not enrolled in this course");
        }

        CourseState courseState = courseStateOpt.get();

        CourseStates currentStatus = courseState.getCourseStates();
        if (currentStatus == newStatus) {
            throw new RuntimeException("The course status is already " + newStatus.name() + ". No update needed.");
        }

        courseState.setCourseStates(newStatus);
        
        CourseState savedCourseState = courseStateRepository.save(courseState);
        
        CourseStateDto courseStateDto = new CourseStateDto();
        courseStateDto.setCourseId(savedCourseState.getCourse().getId());
        courseStateDto.setCourseName(savedCourseState.getCourse().getName());
        courseStateDto.setStudentId(savedCourseState.getStudent().getId());
        courseStateDto.setStudentName(savedCourseState.getStudent().getName());
        courseStateDto.setCourseStates(savedCourseState.getCourseStates());
        
        return courseStateDto;
    }

    @Override
    @Transactional
    public List<CourseState> findByCourseId(Long courseId) {
        return courseStateRepository.findByCourseId(courseId);
    }

    @Override
    @Transactional
    public List<CourseState> findByStudentId(Long studentId) {
        return courseStateRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public Long countByCourseId(Long courseId) {
        return courseStateRepository.countByCourseId(courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus) {
        CourseStates status;
        try {
            status = CourseStates.valueOf(courseStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid course status: " + courseStatus);
        }

        List<CourseState> courseStates = courseStateRepository.findByCourseStates(status);
        
        if (courseStates == null || courseStates.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, StudentDetailsDto> studentMap = new HashMap<>();
        
        for (CourseState courseState : courseStates) {
            if (courseState == null || courseState.getStudent() == null || courseState.getCourse() == null) {
                continue;
            }
            
            Long studentId = courseState.getStudent().getId();
            
            StudentDetailsDto studentDetailsDto = studentMap.get(studentId);
            if (studentDetailsDto == null) {
                studentDetailsDto = new StudentDetailsDto();
                studentDetailsDto.setName(courseState.getStudent().getName());
                studentDetailsDto.setDateOfBirth(courseState.getStudent().getDateOfBirth());
                studentDetailsDto.setCourses(new ArrayList<>());
                studentMap.put(studentId, studentDetailsDto);
            }
            
            CourseDto courseDto = new CourseDto();
            courseDto.setName(courseState.getCourse().getName());
            courseDto.setFee(courseState.getCourse().getFee());
            studentDetailsDto.getCourses().add(courseDto);
        }
        
        return new ArrayList<>(studentMap.values());
    }

}
