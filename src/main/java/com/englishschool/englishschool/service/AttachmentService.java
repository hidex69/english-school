package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.AttachmentEntity;
import com.englishschool.englishschool.enums.ContentType;

public interface AttachmentService {
    void savePhoto(AttachmentEntity attachment, ContentType contentType);
    AttachmentEntity getPhoto(long entityId, ContentType contentType);
}
