package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupWithStudents {
    private Long id;
    private String name;
    private UserEntity teacher;
    private List<StudentWithMark> students;
}
