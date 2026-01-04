package com.training.StdMngSysMongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InstructorDto {
    public Long instructorId;
    private String name;
    private Date dateOfBirth;
}
