package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.StudentRating;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.HometaskEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.entity.UserHometaskEntity;
import com.englishschool.englishschool.repository.HometaskRepository;
import com.englishschool.englishschool.repository.UserHometaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HometaskServiceImpl implements HometaskService {

    private final HometaskRepository hometaskRepository;
    private final UserHometaskRepository userHometaskRepository;
    private final GroupService groupService;
    private final UserService userService;

    @Override
    public void saveHometask(HometaskEntity hometask, long userId) {
        HometaskEntity hometaskEntity = hometaskRepository.save(hometask);
        GroupEntity groupEntity = groupService.getGroupForTeacher(userId);
        Set<Long> studentIds = groupEntity.getParticipants().stream()
                .map(UserEntity::getId).collect(Collectors.toSet());
        List<UserHometaskEntity> userHometaskEntities = new LinkedList<>();
        studentIds.forEach(x -> {
            UserHometaskEntity userHometaskEntity = new UserHometaskEntity();
            userHometaskEntity.setUserId(x);
            userHometaskEntity.setHometaskId(hometaskEntity.getId());
            userHometaskEntity.setDone(false);
            userHometaskEntities.add(userHometaskEntity);
        });
        userHometaskRepository.saveAll(userHometaskEntities);
    }

    @Override
    public HometaskEntity getHometask(long hometaskId) {
        return hometaskRepository.findById(hometaskId).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<HometaskEntity> getHometasks(long userId) {
        Set<Long> hometaskIds = userHometaskRepository.findByUserId(userId)
                .stream().map(UserHometaskEntity::getHometaskId).collect(Collectors.toSet());
        return hometaskRepository.findAllById(hometaskIds);
    }

    @Override
    public void rate(long hometaskId, int mark) {
        if (mark < 0 || mark > 10) {
            return;
        }
        UserHometaskEntity userHometaskEntity = userHometaskRepository.findByHometaskId(hometaskId)
                .orElseThrow(RuntimeException::new);
        if (!userHometaskEntity.isDone()) {
            return;
        }
        userHometaskEntity.setMark(mark);
        userHometaskRepository.save(userHometaskEntity);
    }

    @GetMapping
    public StudentRating getRatingForUser(long userId) {
        UserEntity user = userService.getUser(userId);
        List<UserHometaskEntity> userHometaskEntities = userHometaskRepository.findByUserId(userId);
        double avgMark = userHometaskEntities
                .stream()
                .mapToInt(UserHometaskEntity::getMark)
                .average()
                .orElseThrow(RuntimeException::new);
        long hometaskDone = userHometaskEntities
                .stream()
                .filter(x -> x.isDone() && x.getMark() != null)
                .count();
        long allHometasks = userHometaskEntities.size();
        String fullName = user.getSurname() + " " + user.getName();
        return new StudentRating(userId, fullName, avgMark, hometaskDone, allHometasks);
    }
}
