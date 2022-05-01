package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.entity.AttachmentEntity;
import com.englishschool.englishschool.entity.BlogCommentEntity;
import com.englishschool.englishschool.entity.BlogEntity;
import com.englishschool.englishschool.enums.ContentType;
import com.englishschool.englishschool.service.AttachmentService;
import com.englishschool.englishschool.service.BlogService;
import com.englishschool.englishschool.service.SecurityAssistant;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.englishschool.englishschool.enums.UserRole.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;
    private final AttachmentService attachmentService;
    private final SecurityAssistant securityAssistant;

    @GetMapping("/get-blog/{id}")
    public Blog getBlog(@PathVariable long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public void saveBlog(@RequestBody BlogEntity blogRequest) {
        securityAssistant.currentUserHasRole(ADMIN);
        blogService.saveBlog(blogRequest);
    }

    @GetMapping("/get-blog-comments/{blogId}")
    public List<BlogCommentEntity> getBlogComments(@PathVariable Long blogId) {
        return blogService.getBlogComments(blogId);
    }

    @PostMapping("/save-comment")
    public void saveBlogComment(@RequestBody BlogCommentEntity comment) {
        securityAssistant.currentUserHasRole(ADMIN, TEACHER, STUDENT);
        blogService.saveBlogComment(comment);
    }


    @PostMapping("/{id}/save-photo")
    public void savePhoto(@RequestParam MultipartFile file, @PathVariable long id) throws IOException {
        securityAssistant.currentUserHasRole(ADMIN);
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setEntityId(id);
        attachmentEntity.setContentType(file.getContentType());
        attachmentEntity.setData(file.getBytes());
        attachmentEntity.setName(file.getName());
        attachmentService.savePhoto(attachmentEntity, ContentType.BLOG_PHOTO);
    }

    @GetMapping("/{id}/get-photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable long id) {
        AttachmentEntity attachmentEntity = attachmentService.getPhoto(id, ContentType.BLOG_PHOTO);
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
