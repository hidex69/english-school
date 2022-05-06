package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Hometask;
import com.englishschool.englishschool.domain.HometaskMark;
import com.englishschool.englishschool.domain.StudentRating;
import com.englishschool.englishschool.entity.HometaskEntity;

import java.util.List;

public interface HometaskService {
    void saveHometask(HometaskEntity hometask, long userId, long groupId);
    HometaskEntity getHometask(long hometaskId);
    List<Hometask> getHometasks(long userId);
    void rate(long hometaskId, int mark, long userId);
    StudentRating getRatingForUser(long userId);
    List<HometaskMark> getMarks(long userId);
}
