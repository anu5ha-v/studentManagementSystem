package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.enums.CourseStates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseStateDto {
    private Long courseId;
    private String courseName;
    private Long studentId;
    private String studentName;
    private CourseStates courseStates;
}
