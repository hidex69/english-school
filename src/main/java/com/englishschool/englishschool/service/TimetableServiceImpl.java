package com.englishschool.englishschool.service;

import com.englishschool.englishschool.entity.TimetableEntity;
import com.englishschool.englishschool.repository.TimetableRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final GroupService groupService;

    @Override
    public TimetableEntity getTimetable(long userId) {
        return timetableRepository.findByGroupId(groupService.getGroupForUser(userId).getId()).orElseThrow(RuntimeException::new);
    }

    @Override
    public void saveTimetable(TimetableEntity timetableEntity) {
        TimetableEntity  newTimetable = new TimetableEntity();
        if (timetableEntity.getId() != null) {
            newTimetable = timetableRepository.findById(timetableEntity.getId()).orElseThrow(RuntimeException::new);
        }
        newTimetable.setEndDate(timetableEntity.getEndDate());
        newTimetable.setDaysOfWeek(timetableEntity.getDaysOfWeek());
        newTimetable.setGroupId(timetableEntity.getGroupId());
        newTimetable.setStartDate(timetableEntity.getStartDate());
        newTimetable.setGroupId(timetableEntity.getGroupId());
        timetableRepository.save(newTimetable);
    }

    @Override
    public void deleteTimetable(long id) {
        timetableRepository.delete(timetableRepository.findById(id).orElseThrow(RuntimeException::new));
    }
}
