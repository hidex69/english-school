package com.englishschool.englishschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class StudentRating {
    private long userId;
    private String userFullName;
    private double rating;
    private long hometasksDone;
    private long allHometasks;
}
