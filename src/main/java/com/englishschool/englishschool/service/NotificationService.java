package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.NotificationRequest;
import com.englishschool.englishschool.entity.NotificationEntity;

import java.util.List;

public interface NotificationService {
    void sendNotifications(NotificationRequest notificationRequest);
    List<NotificationEntity> getNotificationForUser(Long userId);
    void deleteNotificationForUser(Long userId);
}
