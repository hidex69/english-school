package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.entity.TimetableEntity;
import com.englishschool.englishschool.service.SecurityAssistant;
import com.englishschool.englishschool.service.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.englishschool.englishschool.enums.UserRole.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/timetable")
@AllArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;
    private final SecurityAssistant securityAssistant;

    @GetMapping("/{userId}")
    public TimetableEntity getTimetable(@PathVariable Long userId) {
        securityAssistant.currentUserHasRole(ADMIN, TEACHER, STUDENT);
        return timetableService.getTimetable(userId);
    }

    @PostMapping("/save")
    public void saveTimetable(@RequestBody TimetableEntity timetable) {
        securityAssistant.currentUserHasRole(ADMIN, TEACHER);
        timetableService.saveTimetable(timetable);
    }

}
