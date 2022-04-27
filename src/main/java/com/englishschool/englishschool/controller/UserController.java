package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.entity.AttachmentEntity;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.HometaskEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.enums.ContentType;
import com.englishschool.englishschool.service.AttachmentService;
import com.englishschool.englishschool.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AttachmentService attachmentService;

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/save")
    public Long saveUser(@RequestBody UserEntity newUser) {
        return userService.saveUser(newUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/restore/{id}")
    public void restoreUser(@PathVariable long id) {
        userService.restoreUser(id);
    }

    @PostMapping("/assign-group")
    public void assignGroupToUser(@RequestBody GroupRequest request) {
        userService.assignToGroup(request);
    }

    @PostMapping("/delete-group")
    public void deleteUserFromGroup(@RequestBody GroupRequest request) {
        userService.deleteFromGroup(request);
    }

    @PostMapping("/rate-courses")
    public void rateCourses(@RequestBody CourseRatingEntity ratingEntity) {
        long currentUserId = 1L;
        userService.rateCourses(ratingEntity, currentUserId);
    }

    @PostMapping("/{id}/save-photo")
    public void savePhoto(@RequestParam MultipartFile file, @PathVariable long id) throws IOException {
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

}
