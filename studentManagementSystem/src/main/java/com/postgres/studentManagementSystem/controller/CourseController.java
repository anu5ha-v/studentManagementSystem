package com.postgres.studentManagementSystem.controller;


import com.postgres.studentManagementSystem.dto.CourseDto;
import com.postgres.studentManagementSystem.dto.InstructorDto;
import com.postgres.studentManagementSystem.dto.StudentDto;
import com.postgres.studentManagementSystem.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseDetails")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/postCourseDetails")
    public ResponseEntity<CourseDto> addCourse(@RequestBody CourseDto courseDto){
        if (courseDto.getName() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            CourseDto createdBook = courseService.saveCourse(courseDto);
            return new ResponseEntity<>(createdBook, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteById/{courseId}")
    public ResponseEntity<Boolean> deleteByName(
            @PathVariable Long courseId
    ) {
        courseService.deleteByCourseId(courseId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentDto>> getStudentsByCourseId(
            @PathVariable Long courseId
    ) {
        try {
            List<StudentDto> students = courseService.getStudentsByCourseId(courseId);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/{courseId}/instructor")
    public ResponseEntity<InstructorDto> getInstructorByCourseId(
            @PathVariable Long courseId
    ) {
        try {
            InstructorDto instructor = courseService.getInstructorByCourseId(courseId);
            return new ResponseEntity<>(instructor, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found") || e.getMessage().contains("no instructor")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
