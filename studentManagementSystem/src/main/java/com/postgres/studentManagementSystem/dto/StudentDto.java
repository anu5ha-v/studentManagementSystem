package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.entity.Course;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {
//    private Long id;
    private String name;
    private Date dateOfBirth;
}
