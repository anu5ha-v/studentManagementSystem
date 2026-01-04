package com.postgres.studentManagementSystem.services.impl;

import com.postgres.studentManagementSystem.dto.CourseStudentsDto;
import com.postgres.studentManagementSystem.dto.InstructorDto;
import com.postgres.studentManagementSystem.dto.InstructorDetailsDto;
import com.postgres.studentManagementSystem.entity.Course;
import com.postgres.studentManagementSystem.entity.Instructor;
import com.postgres.studentManagementSystem.repository.InstructorRepository;
import com.postgres.studentManagementSystem.services.CourseService;
import com.postgres.studentManagementSystem.services.CourseStateService;
import com.postgres.studentManagementSystem.services.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseStateService courseStateService;

    @Override
    @Transactional
    public InstructorDto saveInstructor(InstructorDto instructorDto) {
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDto, instructor);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return convertToDto(savedInstructor);
    }

    @Override
    @Transactional
    public InstructorDto updateInstructor(Long instructorId, InstructorDto instructorDto) {
        Instructor instructor = instructorRepository.findById(instructorId).orElseThrow();
        if(instructor == null){
            throw new RuntimeException("Instructor is not found with id " + instructorId);
        }
        BeanUtils.copyProperties(instructorDto, instructor,"id");
        Instructor updatedInstructor = instructorRepository.save(instructor);
        return convertToDto(updatedInstructor);
    }

    @Override
    @Transactional
    public void deleteByInstructorId(Long instructorId) {
        instructorRepository.deleteById(instructorId);
    }

    @Override
    @Transactional(readOnly = true)
    public InstructorDetailsDto getInstructorDetails(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + instructorId));

        InstructorDetailsDto instructorDetailsDto = new InstructorDetailsDto();
        instructorDetailsDto.setName(instructor.getName());
        instructorDetailsDto.setDateOfBirth(instructor.getDateOfBirth());

        // Get course information if instructor has a course
        if (instructor.getCourse() != null) {
            CourseStudentsDto courseDto = new CourseStudentsDto();
            courseDto.setName(instructor.getCourse().getName());
            courseDto.setFee(instructor.getCourse().getFee());

            // Count students enrolled in this course using CourseStateService
            Long studentsCount = courseStateService.countByCourseId(instructor.getCourse().getId());
            courseDto.setStudentsCount(studentsCount != null ? studentsCount : 0);

            instructorDetailsDto.setCourse(courseDto);
        }

        return instructorDetailsDto;
    }

    @Override
    @Transactional
    public InstructorDetailsDto registerInstructorToCourse(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + instructorId));

        Course course = courseService.findByCourseId(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }

        if (instructor.getCourse() != null) {
            throw new RuntimeException("Instructor is already registered to a course. Please withdraw first.");
        }

        if (course.getInstructor() != null) {
            throw new RuntimeException("Course already has an instructor assigned. Please withdraw first.");
        }

        instructor.setCourse(course);
        course.setInstructor(instructor);

        instructorRepository.save(instructor);
        courseService.updateCourse(course);

        return getInstructorDetails(instructorId);
    }

    @Override
    @Transactional
    public void withdrawInstructorFromCourse(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + instructorId));

        Course course = courseService.findByCourseId(courseId);
        if (course == null) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }

        if (instructor.getCourse() == null || !instructor.getCourse().getId().equals(courseId)) {
            throw new RuntimeException("Instructor is not registered to this course.");
        }

        instructor.setCourse(null);
        course.setInstructor(null);

        instructorRepository.save(instructor);
        courseService.updateCourse(course);
    }

    @Override
    public Long getAllInstructorsCount() {
        return instructorRepository.count();
    }

    private InstructorDto convertToDto(Instructor instructor) {
        InstructorDto instructorDto = new InstructorDto();
        BeanUtils.copyProperties(instructor, instructorDto);
        return instructorDto;
    }
}
