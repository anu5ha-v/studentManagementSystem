package com.training.StdMngSysMongo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = Instructors.COLLECTION_NAME)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Instructors implements Serializable {
    public static final String COLLECTION_NAME = "instructors";

    @Id
    public Long instructorId;
    public String instructorName;
    public Date instructorDateOfBirth;
    public Long courseId;

}
