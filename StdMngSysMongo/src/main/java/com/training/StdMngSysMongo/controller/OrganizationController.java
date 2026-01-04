package com.training.StdMngSysMongo.controller;

import com.training.StdMngSysMongo.dto.CourseDetailDto;
import com.training.StdMngSysMongo.dto.InstructorDetailsDto;
import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @GetMapping("/getAllStudents")
    public ResponseEntity<Long> getAllStudents() {
        return new ResponseEntity<>(organizationService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/studentsCount/{courseId}")
    public ResponseEntity<Long> findCountByCourseId(
            @RequestParam Long courseId
    ) {
        return new ResponseEntity<>(organizationService.getStudentsByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/instructorDetails/{courseId}")
    public ResponseEntity<InstructorDetailsDto> getInstructorDetails(
            @RequestParam Long courseId
    ) {
        return new ResponseEntity<>(organizationService.getInstructorDetails(courseId), HttpStatus.OK);
    }

    @GetMapping("/total/instructorCount")
    public ResponseEntity<Long> getTotalInstructorCount(){
        return new ResponseEntity<>(organizationService.getTotalInstructorCount(), HttpStatus.OK);
    }

    @GetMapping("/getAllDetails/{courseId}")
    public ResponseEntity<CourseDetailDto> getAllDetails(
            @PathVariable Long courseId
    ){
        return new ResponseEntity<>(organizationService.getAllDetails(courseId), HttpStatus.OK);
    }

    @GetMapping("/getStudents/{Coursestatus}")
    public ResponseEntity<List<StudentDetailsDto>> getStudentsByCourseStatus(
            @RequestParam String courseStatus
    ){
        return new ResponseEntity<>(organizationService.getStudentsByCourseStatus(courseStatus), HttpStatus.OK);
    }
}

