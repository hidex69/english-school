package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByIdInAndUserRoleIn(Collection<Long> ids, Collection<UserRole> roles);
    List<UserEntity> findByIdIn(Collection<Long> ids);
}
