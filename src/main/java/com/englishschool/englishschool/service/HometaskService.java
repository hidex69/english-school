package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.StudentRating;
import com.englishschool.englishschool.entity.HometaskEntity;

import java.util.List;

public interface HometaskService {
    void saveHometask(HometaskEntity hometask, long userId);
    HometaskEntity getHometask(long hometaskId);
    List<HometaskEntity> getHometasks(long userId);
    void rate(long hometaskId, int mark);
    StudentRating getRatingForUser(long userId);
}
