package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.entity.TimetableEntity;
import com.englishschool.englishschool.service.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/timetable")
@AllArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;

    @GetMapping()
    public TimetableEntity getTimetable(@RequestBody Long userId) {
        return timetableService.getTimetable(userId);
    }

    @PostMapping("/save")
    public void saveTimetable(@RequestBody TimetableEntity timetable) {
        timetableService.saveTimetable(timetable);
    }

}
