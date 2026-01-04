package com.training.StdMngSysMongo.dto;

import com.training.StdMngSysMongo.enums.CourseStatus;
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
    private CourseStatus courseStatus;
}
