package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.entity.Instructor;
import com.postgres.studentManagementSystem.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDetailDto {
    private String name;

    private Double fee;

    private InstructorDto instructor;

    private List<StudentDto> students;
}
