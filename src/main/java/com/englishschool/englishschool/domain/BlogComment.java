package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogComment {
    private UserEntity user;
    private String text;
    private Date postingDate;
}
