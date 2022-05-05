package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private Long id;
    private String name;
    private Set<UserEntity> participants;
    private UserEntity teacher;
}
