package com.training.StdMngSysMongo.entity;

import com.training.StdMngSysMongo.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Enrollments {
    public Long courseId;
    public String courseName;
    public Double courseFee;
    public CourseStatus courseStatus;
}
