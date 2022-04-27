package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.GroupEntity;

public interface GroupService {
    GroupEntity getGroupForUser(long userId);
    GroupEntity getGroupForTeacher(long teacherId);
}
