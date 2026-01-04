package com.training.StdMngSysMongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Courses.COLLECTION_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Courses {
    public static final String COLLECTION_NAME = "courses";
    @Id
    public Long courseId;
    public String courseName;
    public Double fee;
    public Long instructorId;
}
