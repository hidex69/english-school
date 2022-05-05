package com.englishschool.englishschool.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupShort {
    private Long id;
    private String name;
    private Integer participantsCount;
}
