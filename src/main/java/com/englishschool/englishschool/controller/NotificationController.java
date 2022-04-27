package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.NotificationRequest;
import com.englishschool.englishschool.entity.NotificationEntity;
import com.englishschool.englishschool.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/start")
    public void sendNotifications(@RequestBody NotificationRequest notificationRequest) {
        notificationService.sendNotifications(notificationRequest);
    }

    @DeleteMapping("/delete-for-user/{id}")
    public void deleteNotifications(@PathVariable Long id) {
        notificationService.deleteNotificationForUser(id);
    }

    @GetMapping("/get-notifications/{id}")
    public List<NotificationEntity> getNotifications(@PathVariable Long id) {
        return notificationService.getNotificationForUser(id);
    }
}
