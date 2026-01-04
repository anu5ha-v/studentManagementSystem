package com.postgres.studentManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseStudentsDto {
    private String name;

    private Double fee;

    Long StudentsCount;
}
