package com.training.StdMngSysMongo.service.impl;

import com.training.StdMngSysMongo.dto.CourseDto;
import com.training.StdMngSysMongo.dto.CourseProgressDto;
import com.training.StdMngSysMongo.dto.CourseStateDto;
import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.entity.Courses;
import com.training.StdMngSysMongo.entity.Enrollments;
import com.training.StdMngSysMongo.entity.Instructors;
import com.training.StdMngSysMongo.entity.Students;
import com.training.StdMngSysMongo.enums.CourseStatus;
import com.training.StdMngSysMongo.exception.BusinessException;
import com.training.StdMngSysMongo.exception.ResourceNotFoundException;
import com.training.StdMngSysMongo.exception.ValidationException;
import com.training.StdMngSysMongo.repository.InstructorRepository;
import com.training.StdMngSysMongo.repository.StudentRepository;
import com.training.StdMngSysMongo.service.CourseService;
import com.training.StdMngSysMongo.service.CourseStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseStateServiceImpl implements CourseStateService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    InstructorRepository instructorRepository;

    @Override
    public Optional<Enrollments> findByStudentIdAndCourseId(Long studentId, Long courseId) {
        Students student = studentRepository.findById(studentId).orElse(null);
        if (student == null || student.getEnrollments() == null) {
            return Optional.empty();
        }
        return student.getEnrollments().stream()
                .filter(enrollment -> enrollment.getCourseId().equals(courseId))
                .findFirst();
    }

    @Override
    public void save(Long studentId, Enrollments enrollment) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));
        if (student.getEnrollments() == null) {
            student.setEnrollments(new ArrayList<>());
        }
        student.getEnrollments().add(enrollment);
        studentRepository.save(student);
    }

    @Override
    public void deleteEnrollment(Long studentId, Long courseId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));
        if (student.getEnrollments() == null) {
            return;
        }
        boolean removed = student.getEnrollments().removeIf(
                enrollment -> enrollment.getCourseId().equals(courseId)
        );
        if (removed) {
            studentRepository.save(student);
        }
    }

    @Override
    public List<CourseProgressDto> getCourseProgress(Long studentId) {
        List<CourseProgressDto> courseProgressDtos = new ArrayList<>();
        Students student = studentRepository.findById(studentId).orElse(null);
        
        if (student == null || student.getEnrollments() == null || student.getEnrollments().isEmpty()) {
            return courseProgressDtos;
        }
        
        for (Enrollments enrollment : student.getEnrollments()) {
            if (enrollment == null) {
                continue;
            }
            
            Courses course = courseService.findByCourseId(enrollment.getCourseId());
            if (course == null) {
                continue;
            }
            
            CourseProgressDto courseProgressDto = new CourseProgressDto();
            courseProgressDto.setName(enrollment.getCourseName());
            courseProgressDto.setFee(enrollment.getCourseFee());
            
            if (course.getInstructorId() != null) {
                Optional<Instructors> instructor = instructorRepository.findByCourseId(course.getCourseId());
                if (instructor.isPresent()) {
                    courseProgressDto.setInstructorName(instructor.get().getInstructorName());
                } else {
                    courseProgressDto.setInstructorName("No Instructor Assigned");
                }
            } else {
                courseProgressDto.setInstructorName("No Instructor Assigned");
            }
            
            courseProgressDto.setCourseStatus(enrollment.getCourseStatus());
            courseProgressDtos.add(courseProgressDto);
        }
        return courseProgressDtos;
    }

    @Override
    public CourseStateDto updateCourseStatus(Long courseId, Long studentId, CourseStatus newStatus) {
        if (newStatus == null) {
            throw new ValidationException("Status cannot be null");
        }

        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", studentId));

        if (student.getEnrollments() == null || student.getEnrollments().isEmpty()) {
            throw new ResourceNotFoundException("Student is not enrolled in any course");
        }

        Enrollments enrollment = student.getEnrollments().stream()
                .filter(e -> e.getCourseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Student is not enrolled in this course"));

        CourseStatus currentStatus = enrollment.getCourseStatus();
        if (currentStatus == newStatus) {
            throw new BusinessException("The course status is already " + newStatus.name() + ". No update needed.");
        }

        enrollment.setCourseStatus(newStatus);
        studentRepository.save(student);

        CourseStateDto courseStateDto = new CourseStateDto();
        courseStateDto.setCourseId(enrollment.getCourseId());
        courseStateDto.setCourseName(enrollment.getCourseName());
        courseStateDto.setStudentId(student.getStudentId());
        courseStateDto.setStudentName(student.getStudentName());
        courseStateDto.setCourseStatus(enrollment.getCourseStatus());

        return courseStateDto;
    }

    @Override
    public List<Enrollments> findByCourseId(Long courseId) {
        List<Students> students = studentRepository.findByEnrollmentsCourseId(courseId);
        List<Enrollments> enrollments = new ArrayList<>();
        for (Students student : students) {
            if (student.getEnrollments() != null) {
                enrollments.addAll(
                        student.getEnrollments().stream()
                                .filter(e -> e.getCourseId().equals(courseId))
                                .collect(Collectors.toList())
                );
            }
        }
        return enrollments;
    }

    @Override
    public List<Enrollments> findByStudentId(Long studentId) {
        Students student = studentRepository.findById(studentId).orElse(null);
        if (student == null || student.getEnrollments() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(student.getEnrollments());
    }

    @Override
    public Long countByCourseId(Long courseId) {
        return (long) studentRepository.findByEnrollmentsCourseId(courseId).size();
    }

    @Override
    public List<StudentDetailsDto> getStudentsByCourseStatus(String courseStatus) {
        try {
            if (courseStatus == null || courseStatus.trim().isEmpty()) {
                throw new ValidationException("Course status cannot be null or empty");
            }
            
            CourseStatus status;
            try {
                status = CourseStatus.valueOf(courseStatus.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Invalid course status: " + courseStatus);
            }

        List<Students> students = studentRepository.findByEnrollmentsCourseStatus(status);
        
        if (students == null || students.isEmpty()) {
            return new ArrayList<>();
        }

        List<StudentDetailsDto> studentDetailsDtos = new ArrayList<>();
        for (Students student : students) {
            if (student == null || student.getEnrollments() == null) {
                continue;
            }
            
            List<Enrollments> matchingEnrollments = student.getEnrollments().stream()
                    .filter(e -> e.getCourseStatus() == status)
                    .collect(Collectors.toList());
            
            if (matchingEnrollments.isEmpty()) {
                continue;
            }
            
            StudentDetailsDto studentDetailsDto = new StudentDetailsDto();
            studentDetailsDto.setName(student.getStudentName());
            studentDetailsDto.setDateOfBirth(student.getStudentDateOfBirth());
            
            List<CourseDto> courseDtos = new ArrayList<>();
            for (Enrollments enrollment : matchingEnrollments) {
                CourseDto courseDto = new CourseDto();
                courseDto.setName(enrollment.getCourseName());
                courseDto.setFee(enrollment.getCourseFee());
                courseDtos.add(courseDto);
            }
            studentDetailsDto.setCourses(courseDtos);
            studentDetailsDtos.add(studentDetailsDto);
        }
        
        return studentDetailsDtos;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching students by course status: " + e.getMessage(), e);
        }
    }
}

