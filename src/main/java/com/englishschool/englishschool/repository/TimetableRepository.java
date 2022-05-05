package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.TimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {
    Optional<TimetableEntity> findByGroupId(Long groupId);
    List<TimetableEntity> findByGroupIdIn(Collection<Long> ids);
}
