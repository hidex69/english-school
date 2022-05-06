package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Group;
import com.englishschool.englishschool.domain.GroupShort;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.TimetableEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.exception.BadRequsetException;
import com.englishschool.englishschool.repository.GroupRepository;
import com.englishschool.englishschool.repository.TimetableRepository;
import com.englishschool.englishschool.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final TimetableRepository timetableRepository;

    @Override
    public GroupEntity getGroupForUser(long userId) {
        Set<UserEntity> user = Collections.singleton(userRepository.findById(userId).orElseThrow(RuntimeException::new));
        return groupRepository.findFirstByParticipantsIn(user).orElseThrow(RuntimeException::new);
    }

    @Override
    public GroupEntity getGroupForTeacher(long teacherId) {
        UserEntity user = userRepository.findById(teacherId).orElseThrow(RuntimeException::new);
        return groupRepository.findFirstByTeacherId(teacherId).orElseThrow(RuntimeException::new);
    }

    @Override
    public Long createGroup(String name, Long id) {
        return groupRepository.save(new GroupEntity(null, name, id, Collections.emptySet())).getId();
    }

    @Override
    public List<GroupShort> getFreeGroups() {
        List<GroupEntity> groups = groupRepository.findAll().stream().filter(x -> x.getTeacherId() == null).collect(Collectors.toList());
        Map<Long, UserEntity> teacherMap = userRepository.findByIdIn(groups.stream().map(GroupEntity::getTeacherId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(UserEntity::getId, x -> x));
        return groups.stream()
                .map(x -> new GroupShort(x.getId(), x.getName(), x.getParticipants().size()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupShort> getGroupsWithoutTimetable() {
        List<GroupEntity> groups = groupRepository.findAll();
        List<Long> groupsWithTimeTableIds = timetableRepository.findByGroupIdIn(
                groups
                        .stream()
                        .map(GroupEntity::getId)
                        .collect(Collectors.toList())
        )
                .stream()
                .map(TimetableEntity::getGroupId)
                .collect(Collectors.toList());
        return groups.stream().filter(x -> !groupsWithTimeTableIds.contains(x.getId()))
                .map(x -> new GroupShort(x.getId(), x.getName(), x.getParticipants().size()))
                .collect(Collectors.toList());
    }

    @Override
    public GroupEntity getGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(BadRequsetException::new);
    }

    @Override
    public List<GroupShort> getAll() {
        return groupRepository.findAll().stream().map(x -> new GroupShort(x.getId(), x.getName(), x.getParticipants().size()))
                .collect(Collectors.toList());
    }
}
