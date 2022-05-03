package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.repository.GroupRepository;
import com.englishschool.englishschool.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

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
}
