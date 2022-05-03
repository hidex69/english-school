package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.NotificationRequest;
import com.englishschool.englishschool.entity.NotificationEntity;
import com.englishschool.englishschool.service.NotificationService;
import com.englishschool.englishschool.service.SecurityAssistant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.englishschool.englishschool.enums.UserRole.*;

@CrossOrigin("http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final SecurityAssistant securityAssistant;

    @PostMapping("/start")
    public void sendNotifications(@RequestBody NotificationRequest notificationRequest) {
        securityAssistant.currentUserHasRole(ADMIN);
        notificationService.sendNotifications(notificationRequest);
    }

    @DeleteMapping("/delete-for-user/{id}")
    public void deleteNotifications(@PathVariable Long id) {
        securityAssistant.currentUserHasRole(ADMIN);
        notificationService.deleteNotificationForUser(id);
    }

    @GetMapping("/get-notifications")
    public List<NotificationEntity> getNotifications() {
        return notificationService.getNotificationForUser(securityAssistant.getCurrentUserId());
    }
}
