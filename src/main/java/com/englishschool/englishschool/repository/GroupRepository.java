package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    Optional<GroupEntity> findFirstByParticipantsIn(Collection<UserEntity> users);
    Optional<GroupEntity> findFirstByTeacherId(long teacherId);
}
