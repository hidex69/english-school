package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.NotificationRequest;
import com.englishschool.englishschool.entity.NotificationEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.enums.UserRole;
import com.englishschool.englishschool.repository.NotificationRepository;
import com.englishschool.englishschool.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void sendNotifications(NotificationRequest notificationRequest) {
        if (notificationRequest == null || notificationRequest.getUserToNotify().isEmpty()) {
            return;
        }
        List<Long> usersToNotify = userRepository.findByIdInAndUserRoleIn(notificationRequest.getUserToNotify()
                , Arrays.asList(UserRole.STUDENT, UserRole.TEACHER)).stream()
                .map(UserEntity::getId).collect(Collectors.toList());
        List<NotificationEntity> result = new LinkedList<>();
        usersToNotify.forEach(x -> {
            NotificationEntity notification = new NotificationEntity();
            notification.setReceivingTime(new Date());
            notification.setText(notificationRequest.getText());
            notification.setRecipientId(x);
            result.add(notification);
        });
        notificationRepository.saveAll(result);
    }

    @Override
    @Transactional
    public List<NotificationEntity> getNotificationForUser(Long userId) {
        if (userId == null) {
            throw new RuntimeException();
        }
        return notificationRepository.findByRecipientId(userId);
    }

    @Override
    @Transactional
    public void deleteNotificationForUser(Long userId) {
        notificationRepository.deleteAll(notificationRepository.findByRecipientId(userId));
    }
}
