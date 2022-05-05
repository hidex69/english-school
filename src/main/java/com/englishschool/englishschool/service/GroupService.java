package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Group;
import com.englishschool.englishschool.domain.GroupShort;
import com.englishschool.englishschool.entity.GroupEntity;

import java.util.List;

public interface GroupService {
    GroupEntity getGroupForUser(long userId);
    GroupEntity getGroupForTeacher(long teacherId);
    Long createGroup(String name, Long id);
    List<GroupShort> getFreeGroups();
    List<GroupShort> getGroupsWithoutTimetable();
    GroupEntity getGroupById(long id);
}
