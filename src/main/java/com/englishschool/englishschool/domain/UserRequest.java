package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequest {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private UserRole userRole;

    private Boolean deleted;

    private String password;
}
