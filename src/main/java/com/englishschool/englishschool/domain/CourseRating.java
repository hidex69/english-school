package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.entity.CourseRatingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRating {
    private String fullName;
    private long id;
    private String text;
    private int mark;
    private Date ratingDate;

    public CourseRating(CourseRatingEntity courseRatingEntity, String fullName) {
        this.fullName = fullName;
        this.id = courseRatingEntity.getId();
        this.text = courseRatingEntity.getText();
        this.ratingDate = courseRatingEntity.getRatingDate();
        this.mark = courseRatingEntity.getMark();
    }
}
