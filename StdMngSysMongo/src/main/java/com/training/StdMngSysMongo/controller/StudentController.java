package com.training.StdMngSysMongo.controller;

import com.training.StdMngSysMongo.dto.StudentDetailsDto;
import com.training.StdMngSysMongo.dto.StudentDto;
import com.training.StdMngSysMongo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentDetails")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/postStudentDetails")
    public ResponseEntity<StudentDto> addBook(@RequestBody StudentDto studentDto){
        if (studentDto.getName() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            StudentDto createdBook = studentService.saveStudent(studentDto);
            return new ResponseEntity<>(createdBook, HttpStatus.OK);
        }
    }

    @PutMapping("/updateStudent/{studentId}")
    public ResponseEntity<StudentDto> updateByStudentId(
            @PathVariable Long studentId,
            @RequestBody StudentDto studentDto
    ){
        StudentDto updatedStudent = studentService.updateStudent(studentId, studentDto);
        return new ResponseEntity<>(updatedStudent,HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{studnentId}")
    public ResponseEntity<Boolean> deleteByName(
            @PathVariable Long studnentId
    ) {
        studentService.deleteByStudentId(studnentId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/getStudentDetails/{studentId}")
    public ResponseEntity<StudentDetailsDto> getStudentDetails(
            @PathVariable Long studentId
    ) {
        try {
            StudentDetailsDto studentDetails = studentService.getStudentDetails(studentId);
            return new ResponseEntity<>(studentDetails, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/enroll/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentDetailsDto> enrollStudentInCourse(
            @PathVariable Long studentId, @PathVariable Long courseId
    ){
        StudentDetailsDto studentDetailsDto = studentService.enrollStudent(studentId, courseId);
        return new ResponseEntity<>(studentDetailsDto, HttpStatus.OK);
    }

    @DeleteMapping("/withdraw/{studentId}/courses/{courseId}")
    public ResponseEntity<Boolean> withdrawFromCourse(
            @PathVariable Long studentId, @PathVariable Long courseId
    ){
        studentService.withdrawFromTheCourse(studentId, courseId);
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

}

