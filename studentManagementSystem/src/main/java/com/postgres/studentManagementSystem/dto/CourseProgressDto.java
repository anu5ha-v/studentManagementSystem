package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.entity.Instructor;
import com.postgres.studentManagementSystem.enums.CourseStates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseProgressDto {
    private String name;

    private Double fee;

    private String InstructorName;

    private CourseStates courseStates;
}
