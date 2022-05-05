package com.englishschool.englishschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HometaskMark {
    private String name;
    private int mark;
    private String hometaskUrl;
    private Date date;
}
