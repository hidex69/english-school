package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.CourseRating;
import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.domain.UserRequest;
import com.englishschool.englishschool.entity.*;
import com.englishschool.englishschool.enums.ContentType;
import com.englishschool.englishschool.service.AttachmentService;
import com.englishschool.englishschool.service.GroupService;
import com.englishschool.englishschool.service.SecurityAssistant;
import com.englishschool.englishschool.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.englishschool.englishschool.enums.UserRole.*;

@CrossOrigin("http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AttachmentService attachmentService;
    private final SecurityAssistant securityAssistant;
    private final GroupService groupService;

    @GetMapping("/current")
    public UserEntity getCurrentUser() {
        return securityAssistant.getCurrentUser();
    }

    @GetMapping("/current-id")
    public Long getCurrentUserId() {
        return securityAssistant.getCurrentUserId();
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/save")
    public Long saveUser(@RequestBody UserRequest newUser) {
        return userService.saveUser(newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        securityAssistant.currentUserHasRole(ADMIN);
        userService.deleteUser(id);
    }

    @PostMapping("/restore/{id}")
    public void restoreUser(@PathVariable long id) {
        securityAssistant.currentUserHasRole(ADMIN);
        userService.restoreUser(id);
    }

    @PostMapping("/assign-group")
    public void assignGroupToUser(@RequestBody GroupRequest request) {
        securityAssistant.currentUserHasRole(ADMIN);
        userService.assignToGroup(request);
    }

    @PostMapping("/delete-group")
    public void deleteUserFromGroup(@RequestBody GroupRequest request) {
        securityAssistant.currentUserHasRole(ADMIN);
        userService.deleteFromGroup(request);
    }

    @GetMapping("/get-group")
    public GroupEntity getGroupForUser() {
        return userService.getGroupForUser(securityAssistant.getCurrentUserId());
    }

    @PostMapping("/rate-courses")
    public void rateCourses(@RequestBody CourseRatingEntity ratingEntity) {
        userService.rateCourses(ratingEntity, securityAssistant.getCurrentUserId());
    }

    @GetMapping("/courses-rating")
    public List<CourseRating> getCoursesRating() {
        return userService.getRating();
    }

    @PostMapping("/{id}/save-photo")
    public void savePhoto(@RequestParam MultipartFile file, @PathVariable long id) throws IOException {
        securityAssistant.currentUserHasRole(ADMIN, TEACHER, STUDENT);
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setEntityId(id);
        attachmentEntity.setContentType(file.getContentType());
        attachmentEntity.setData(file.getBytes());
        attachmentEntity.setName(file.getName());
        attachmentService.savePhoto(attachmentEntity, ContentType.USER_PHOTO);
    }

    @GetMapping("/{id}/get-photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable long id) {
        AttachmentEntity attachmentEntity = attachmentService.getPhoto(id, ContentType.USER_PHOTO);
        MediaType mediaType = MediaType.valueOf(attachmentEntity.getContentType());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ContentDisposition contentDisposition = ContentDisposition
                .inline()
                .filename(attachmentEntity.getName())
                .build();
        headers.setContentDisposition(contentDisposition);
        return new ResponseEntity<>(attachmentEntity.getData(), headers, HttpStatus.OK);
    }

    @PostMapping("/create-group")
    public Long createGroup(@RequestParam String groupName, @RequestParam long teacherId) {
        securityAssistant.currentUserHasRole(ADMIN);
        return groupService.createGroup(groupName, teacherId);
    }

}
