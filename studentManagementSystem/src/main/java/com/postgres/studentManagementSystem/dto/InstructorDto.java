package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.entity.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
//    private Long id;
    private String name;
    private Date dateOfBirth;
}
