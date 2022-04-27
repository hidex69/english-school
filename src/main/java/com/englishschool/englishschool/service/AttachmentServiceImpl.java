package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.AttachmentEntity;
import com.englishschool.englishschool.enums.ContentType;
import com.englishschool.englishschool.repository.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public void savePhoto(AttachmentEntity attachment, ContentType contentType) {
        attachment.setEntityType(contentType);
        attachmentRepository.save(attachment);
    }

    @Override
    public AttachmentEntity getPhoto(long entityId, ContentType contentType) {
        return attachmentRepository.findByEntityIdAndEntityType(entityId, contentType).orElseThrow(RuntimeException::new);
    }
}
