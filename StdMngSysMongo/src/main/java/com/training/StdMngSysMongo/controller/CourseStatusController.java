package com.training.StdMngSysMongo.controller;

import com.training.StdMngSysMongo.dto.CourseProgressDto;
import com.training.StdMngSysMongo.dto.CourseStateDto;
import com.training.StdMngSysMongo.enums.CourseStatus;
import com.training.StdMngSysMongo.service.CourseStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseState")
public class CourseStatusController {

    @Autowired
    CourseStateService courseStateService;

    @GetMapping("/getStudentDetailsWithProgress/{studentId}")
    public ResponseEntity<List<CourseProgressDto>> getStudentDetailsWithProgress(
            @PathVariable Long studentId
    ) {
        try {
            List<CourseProgressDto> courseProgressDtos = courseStateService.getCourseProgress(studentId);
            return new ResponseEntity<>(courseProgressDtos, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/courses/{courseId}/students/{studentId}/status")
    public ResponseEntity<CourseStateDto> updateStudentCourseStatus(
            @PathVariable Long courseId,
            @PathVariable Long studentId,
            @RequestParam String status
    ) {
        try {
            CourseStatus courseStatus;
            try {
                courseStatus = CourseStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            CourseStateDto updatedCourseState = courseStateService.updateCourseStatus(courseId, studentId, courseStatus);
            return new ResponseEntity<>(updatedCourseState, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("already")) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            } else if (e.getMessage().contains("not enrolled")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

}
