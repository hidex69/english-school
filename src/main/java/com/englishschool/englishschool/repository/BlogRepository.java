package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    Optional<BlogEntity> findById(long id);
    List<BlogEntity> findAllByOrderByPostingDateDesc();
}
