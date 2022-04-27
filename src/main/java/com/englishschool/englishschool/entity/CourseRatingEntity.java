package com.englishschool.englishschool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course_rating")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Column
    private long userId;

    @Column
    private int mark;

    @Column
    private Date ratingDate;
}
