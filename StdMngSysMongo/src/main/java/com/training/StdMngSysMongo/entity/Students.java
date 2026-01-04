package com.training.StdMngSysMongo.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = Students.COLLECTION_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Students {
    public static final String COLLECTION_NAME = "students";
    @Id
    public Long studentId;
    public String studentName;
    public Date studentDateOfBirth;
    public List<Enrollments> enrollments;
}
