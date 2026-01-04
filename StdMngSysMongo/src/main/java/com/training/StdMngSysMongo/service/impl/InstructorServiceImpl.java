package com.training.StdMngSysMongo.service.impl;

import com.training.StdMngSysMongo.dto.CourseStudentsDto;
import com.training.StdMngSysMongo.dto.InstructorDetailsDto;
import com.training.StdMngSysMongo.dto.InstructorDto;
import com.training.StdMngSysMongo.entity.Courses;
import com.training.StdMngSysMongo.entity.Instructors;
import com.training.StdMngSysMongo.exception.BusinessException;
import com.training.StdMngSysMongo.exception.ResourceNotFoundException;
import com.training.StdMngSysMongo.repository.InstructorRepository;
import com.training.StdMngSysMongo.repository.StudentRepository;
import com.training.StdMngSysMongo.service.CourseService;
import com.training.StdMngSysMongo.service.InstructorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public InstructorDto saveInstructor(InstructorDto instructorDto) {
        Instructors instructor = new Instructors();
        BeanUtils.copyProperties(instructorDto, instructor);
        instructor.setInstructorName(instructorDto.getName());
        instructor.setInstructorDateOfBirth(instructorDto.getDateOfBirth());
        Instructors savedInstructor = instructorRepository.save(instructor);
        return convertToDto(savedInstructor);
    }

    @Override
    public InstructorDto updateInstructor(Long instructorId, InstructorDto instructorDto) {
        Instructors instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor", instructorId));
        instructor.setInstructorName(instructorDto.getName());
        instructor.setInstructorDateOfBirth(instructorDto.getDateOfBirth());
        Instructors updatedInstructor = instructorRepository.save(instructor);
        return convertToDto(updatedInstructor);
    }

    @Override
    public void deleteByInstructorId(Long instructorId) {
        if (!instructorRepository.existsById(instructorId)) {
            throw new ResourceNotFoundException("Instructor", instructorId);
        }
        instructorRepository.deleteById(instructorId);
    }

    @Override
    public InstructorDetailsDto getInstructorDetails(Long instructorId) {
        Instructors instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor", instructorId));

        InstructorDetailsDto instructorDetailsDto = new InstructorDetailsDto();
        instructorDetailsDto.setName(instructor.getInstructorName());
        instructorDetailsDto.setDateOfBirth(instructor.getInstructorDateOfBirth());

        if (instructor.getCourseId() != null) {
            Courses course = courseService.findByCourseId(instructor.getCourseId());
            if (course != null) {
                CourseStudentsDto courseDto = new CourseStudentsDto();
                courseDto.setName(course.getCourseName());
                courseDto.setFee(course.getFee());

                Long studentsCount = (long) studentRepository.findByEnrollmentsCourseId(course.getCourseId()).size();
                courseDto.setStudentsCount(studentsCount);

                instructorDetailsDto.setCourse(courseDto);
            }
        }

        return instructorDetailsDto;
    }

    @Override
    public InstructorDetailsDto registerInstructorToCourse(Long instructorId, Long courseId) {
        try {
            Instructors instructor = instructorRepository.findById(instructorId)
                    .orElseThrow(() -> new ResourceNotFoundException("Instructor", instructorId));

            Courses course = courseService.findByCourseId(courseId);
            if (course == null) {
                throw new ResourceNotFoundException("Course", courseId);
            }

            if (instructor.getCourseId() != null) {
                throw new BusinessException("Instructor is already registered to a course. Please withdraw first.");
            }

            if (course.getInstructorId() != null) {
                throw new BusinessException("Course already has an instructor assigned. Please withdraw first.");
            }

            instructor.setCourseId(courseId);
            course.setInstructorId(instructorId);

            instructorRepository.save(instructor);
            courseService.updateCourse(course);

            return getInstructorDetails(instructorId);
        } catch (ResourceNotFoundException | BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while registering instructor: " + e.getMessage(), e);
        }
    }

    @Override
    public void withdrawInstructorFromCourse(Long instructorId, Long courseId) {
        Instructors instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor", instructorId));

        Courses course = courseService.findByCourseId(courseId);
        if (course == null) {
            throw new ResourceNotFoundException("Course", courseId);
        }

        if (instructor.getCourseId() == null || !instructor.getCourseId().equals(courseId)) {
            throw new ResourceNotFoundException("Instructor is not registered to this course");
        }

        instructor.setCourseId(null);
        course.setInstructorId(null);

        instructorRepository.save(instructor);
        courseService.updateCourse(course);
    }

    @Override
    public Long getAllInstructorsCount() {
        return instructorRepository.count();
    }

    private InstructorDto convertToDto(Instructors instructor) {
        InstructorDto instructorDto = new InstructorDto();
        instructorDto.setName(instructor.getInstructorName());
        instructorDto.setDateOfBirth(instructor.getInstructorDateOfBirth());
        return instructorDto;
    }
}

