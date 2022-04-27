package com.englishschool.englishschool.repository;

import com.englishschool.englishschool.entity.AttachmentEntity;
import com.englishschool.englishschool.enums.ContentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
    Optional<AttachmentEntity> findByEntityIdAndEntityType(Long id, ContentType contentType);
}
