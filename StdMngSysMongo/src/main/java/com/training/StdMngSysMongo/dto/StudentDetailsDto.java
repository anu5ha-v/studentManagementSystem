package com.training.StdMngSysMongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDetailsDto {
    public Long studentId;
    private String name;
    private Date dateOfBirth;
    private List<CourseDto> courses;
}
