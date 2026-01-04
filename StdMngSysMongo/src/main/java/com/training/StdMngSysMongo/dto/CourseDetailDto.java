package com.training.StdMngSysMongo.dto;

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
    public Long courseId;
    private String name;
    private Double fee;
    private InstructorDto instructor;
    private List<StudentDto> students;
}
