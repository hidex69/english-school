package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.StudentRating;
import com.englishschool.englishschool.entity.HometaskEntity;
import com.englishschool.englishschool.service.HometaskService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hometask")
@AllArgsConstructor
public class HometaskController {

    private final HometaskService hometaskService;

    @PostMapping
    public void saveHometask(@RequestParam MultipartFile file,
                             @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate,
                             @RequestParam Long id) throws IOException {
        HometaskEntity hometaskEntity = new HometaskEntity();
        hometaskEntity.setData(file.getBytes());
        hometaskEntity.setEndDate(endDate);
        hometaskEntity.setName(file.getName());
        hometaskEntity.setContentType(file.getContentType());
        hometaskService.saveHometask(hometaskEntity, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getHometask(@PathVariable long id) {
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

    @GetMapping("/for-student/{id}")
    public List<HometaskEntity> getHometaskForStudent(@PathVariable long id) {
        return hometaskService.getHometasks(id);
    }

    @PostMapping("/rate-hometask")
    public void rateHometask(@RequestParam long hometaskId, @RequestParam int mark) {
        hometaskService.rate(hometaskId, mark);
    }

    @GetMapping("/rating/{id}")
    public StudentRating getUserRating(@PathVariable long userId) {
        return hometaskService.getRatingForUser(userId);
    }

}
