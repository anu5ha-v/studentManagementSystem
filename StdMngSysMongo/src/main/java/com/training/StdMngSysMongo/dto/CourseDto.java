package com.training.StdMngSysMongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDto {
    public Long courseId;
    private String name;
    private Double fee;
}
