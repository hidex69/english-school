package com.englishschool.englishschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hometask {
    private Long id;
    private String name;
    private String url;
}
