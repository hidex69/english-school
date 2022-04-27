package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.TimetableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {
    Optional<TimetableEntity> findByGroupId(Long groupId);
}
