package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDetailsDto {
    private String name;
    private Date dateOfBirth;
    private List<CourseDto> courses;
}
