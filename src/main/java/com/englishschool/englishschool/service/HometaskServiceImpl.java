package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Hometask;
import com.englishschool.englishschool.domain.HometaskMark;
import com.englishschool.englishschool.domain.StudentRating;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.HometaskEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.entity.UserHometaskEntity;
import com.englishschool.englishschool.enums.UserRole;
import com.englishschool.englishschool.repository.HometaskRepository;
import com.englishschool.englishschool.repository.UserHometaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HometaskServiceImpl implements HometaskService {

    private static final String BASE_URL = "localhost:8080/api/v1/hometask/";

    private final HometaskRepository hometaskRepository;
    private final UserHometaskRepository userHometaskRepository;
    private final GroupService groupService;
    private final UserService userService;

    @Override
    public void saveHometask(HometaskEntity hometask, long userId, long groupId) {
        UserEntity user = userService.getUser(userId);
        HometaskEntity hometaskEntity = hometaskRepository.save(hometask);
        List<UserHometaskEntity> userHometaskEntities = new LinkedList<>();
        GroupEntity groupEntity = groupService.getGroupById(groupId);
        Set<Long> studentIds = groupEntity.getParticipants().stream()
                .map(UserEntity::getId).collect(Collectors.toSet());
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
    public List<Hometask> getHometasks(long userId) {
        List<HometaskEntity> hometasks = hometaskRepository.findByGroupId(groupService.getGroupForUser(userId).getId());
        return hometasks.stream().map(x -> new Hometask(x.getId(), x.getName(), BASE_URL + x.getId())).collect(Collectors.toList());
    }

    @Override
    public void rate(long hometaskId, int mark, long userId) {
        if (mark < 0 || mark > 10) {
            return;
        }
        UserHometaskEntity userHometaskEntity = userHometaskRepository.findByHometaskIdAndUserId(hometaskId, userId)
                .orElseThrow(RuntimeException::new);
        userHometaskEntity.setMark(mark);
        userHometaskEntity.setDone(true);
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

    @Override
    public List<HometaskMark> getMarks(long userId) {
        UserEntity user = userService.getUser(userId);
        List<UserHometaskEntity> userHometaskEntities = userHometaskRepository.findByUserId(userId);
        List<HometaskEntity> hometaskEntities = hometaskRepository.findByIdIn(
                userHometaskEntities.stream().map(UserHometaskEntity::getHometaskId).collect(Collectors.toList())
        );
        Map<Long, HometaskEntity> map = userHometaskEntities.stream().collect(Collectors.toMap(UserHometaskEntity::getId,
                x -> hometaskEntities.get(((int)x.getHometaskId()))));
        return userHometaskRepository.findByUserId(userId).stream().filter(x -> x.isDone() && x.getMark() != null)
                .map(x -> new HometaskMark(
                        map.get(x.getHometaskId()).getName(),
                        x.getMark(),
                        BASE_URL + x.getHometaskId(),
                        map.get(x.getHometaskId()).getDate()
                )).collect(Collectors.toList());
    }
}
