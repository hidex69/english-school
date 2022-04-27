package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.TimetableEntity;

public interface TimetableService {
    TimetableEntity getTimetable(long userId);
    void saveTimetable(TimetableEntity timetableEntity);
    void deleteTimetable(long id);
}
