package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.BlogCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogCommentEntity, Long> {
    List<BlogCommentEntity> findByBlogId(Long blogId);
}
