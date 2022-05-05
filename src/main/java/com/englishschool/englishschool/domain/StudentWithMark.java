package com.englishschool.englishschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithMark {
    private Long id;
    private String fullName;
    private List<HometaskMark> hometasks;
}
