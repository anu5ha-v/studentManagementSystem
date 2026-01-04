package com.postgres.studentManagementSystem.dto;

import com.postgres.studentManagementSystem.entity.Instructor;
import com.postgres.studentManagementSystem.entity.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDto {
//    private Long  id;

    private String name;

    private Double fee;

}
