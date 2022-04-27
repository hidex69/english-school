package com.englishschool.englishschool.domain;


import com.englishschool.englishschool.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    private long groupId;
    private long userId;
}
