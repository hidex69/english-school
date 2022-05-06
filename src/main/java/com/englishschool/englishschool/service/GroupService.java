package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Group;
import com.englishschool.englishschool.domain.GroupShort;
import com.englishschool.englishschool.entity.GroupEntity;

import java.util.List;

public interface GroupService {
    GroupEntity getGroupEntityById(long id);
    GroupEntity getGroupForUser(long userId);
    GroupEntity getGroupForTeacher(long teacherId);
    Long createGroup(String name);
    List<GroupShort> getAll();
    void deleteGroup(long id);
}
