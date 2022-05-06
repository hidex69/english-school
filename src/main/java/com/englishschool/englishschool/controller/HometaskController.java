package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.Hometask;
import com.englishschool.englishschool.domain.HometaskMark;
import com.englishschool.englishschool.domain.StudentRating;
import com.englishschool.englishschool.entity.HometaskEntity;
import com.englishschool.englishschool.service.HometaskService;
import com.englishschool.englishschool.service.SecurityAssistant;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.englishschool.englishschool.enums.UserRole.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/hometask")
@AllArgsConstructor
public class HometaskController {

    private final HometaskService hometaskService;
    private final SecurityAssistant securityAssistant;

    @PostMapping
    public void saveHometask(@RequestParam MultipartFile file,
                             @RequestParam Long groupId) throws IOException {
        securityAssistant.currentUserHasRole(TEACHER);
        HometaskEntity hometaskEntity = new HometaskEntity();
        hometaskEntity.setData(file.getBytes());
        hometaskEntity.setDate(new Date());
        hometaskEntity.setName(file.getName());
        hometaskEntity.setContentType(file.getContentType());
        hometaskEntity.setGroupId(groupId);
        hometaskService.saveHometask(hometaskEntity, securityAssistant.getCurrentUserId(), groupId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getHometask(@PathVariable long id) {
        securityAssistant.currentUserHasRole(STUDENT, TEACHER);
        HometaskEntity hometaskEntity = hometaskService.getHometask(id);
        MediaType mediaType = MediaType.valueOf(hometaskEntity.getContentType());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        ContentDisposition contentDisposition = ContentDisposition
                .attachment()
                .filename(hometaskEntity.getName())
                .build();
        headers.setContentDisposition(contentDisposition);
        return new ResponseEntity<>(hometaskEntity.getData(), headers, HttpStatus.OK);
    }

    @GetMapping("/get-for-student")
    public List<Hometask> getHometaskForStudent() {
        securityAssistant.currentUserHasRole(STUDENT);
        return hometaskService.getHometasks(securityAssistant.getCurrentUserId());
    }

    @PostMapping("/rate-hometask")
    public void rateHometask(@RequestParam long hometaskId, @RequestParam long userId, @RequestParam int mark) {
        securityAssistant.currentUserHasRole(TEACHER);
        hometaskService.rate(hometaskId, mark, userId);
    }

    @GetMapping("/rating/{id}")
    public StudentRating getUserRating(@PathVariable long userId) {
        securityAssistant.currentUserHasRole(TEACHER, ADMIN, STUDENT);
        return hometaskService.getRatingForUser(userId);
    }


    @GetMapping("/get-marks/{userId}")
    public List<HometaskMark> getUserMarks(@PathVariable long userId) {
        securityAssistant.currentUserHasRole(TEACHER, ADMIN, STUDENT);
        return hometaskService.getMarks(userId);
    }



}
