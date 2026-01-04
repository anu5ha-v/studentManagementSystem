package com.postgres.studentManagementSystem.controller;


import com.postgres.studentManagementSystem.dto.InstructorDto;
import com.postgres.studentManagementSystem.dto.InstructorDetailsDto;
import com.postgres.studentManagementSystem.services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructorDetails")
public class InstructorController {

    @Autowired
    InstructorService instructorService;

    @PostMapping("/postInstructorDetails")
    public ResponseEntity<InstructorDto> addInstuctor(@RequestBody InstructorDto instructorDto){
        if (instructorDto.getName() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            InstructorDto createdInstructor = instructorService.saveInstructor(instructorDto);
            return new ResponseEntity<>(createdInstructor, HttpStatus.OK);
        }
    }

    @PutMapping("/updateInstructor/{instructorId}")
    public ResponseEntity<InstructorDto> updateByinstructorId(
            @PathVariable Long instructorId,
            @RequestBody InstructorDto instructorDto
    ){
        InstructorDto updatedInstructor = instructorService.updateInstructor(instructorId, instructorDto);
        return new ResponseEntity<>(updatedInstructor,HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{instructorId}")
    public ResponseEntity<Boolean> deleteById(
            @PathVariable Long instructorId
    ) {
        instructorService.deleteByInstructorId(instructorId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<InstructorDetailsDto> getInstructorDetails(
            @PathVariable Long instructorId
    ) {
        try {
            InstructorDetailsDto instructorDetails = instructorService.getInstructorDetails(instructorId);
            return new ResponseEntity<>(instructorDetails, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/register/{instructorId}/courses/{courseId}")
    public ResponseEntity<InstructorDetailsDto> registerInstructorToCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId
    ) {
        try {
            InstructorDetailsDto instructorDetails = instructorService.registerInstructorToCourse(instructorId, courseId);
            return new ResponseEntity<>(instructorDetails, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Handle different error cases
            if (e.getMessage().contains("already registered") || e.getMessage().contains("already has an instructor")) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            } else if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping("/withdraw/{instructorId}/courses/{courseId}")
    public ResponseEntity<Boolean> withdrawInstructorFromCourse(
            @PathVariable Long instructorId,
            @PathVariable Long courseId
    ) {
        try {
            instructorService.withdrawInstructorFromCourse(instructorId, courseId);
            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found") || e.getMessage().contains("not registered")) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
