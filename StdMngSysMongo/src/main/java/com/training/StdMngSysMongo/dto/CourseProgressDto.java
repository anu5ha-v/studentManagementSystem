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
public class CourseProgressDto {
    public Long courseId;
    private String name;
    private Double fee;
    private String instructorName;
    private CourseStatus courseStatus;
}
