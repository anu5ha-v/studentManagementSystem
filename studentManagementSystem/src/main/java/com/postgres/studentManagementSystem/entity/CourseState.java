package com.postgres.studentManagementSystem.entity;

import com.postgres.studentManagementSystem.enums.CourseStates;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "course_state")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    private CourseStates courseStates;
}
