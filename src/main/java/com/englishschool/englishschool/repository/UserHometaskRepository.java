package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.UserHometaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHometaskRepository extends JpaRepository<UserHometaskEntity, Long> {
    List<UserHometaskEntity> findByUserId(long userId);
    Optional<UserHometaskEntity> findByHometaskId(long hometaskId);
    Optional<UserHometaskEntity> findByHometaskIdAndUserId(long hId, long uId);
}
