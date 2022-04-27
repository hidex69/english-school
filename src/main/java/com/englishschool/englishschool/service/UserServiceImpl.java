package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.CourseRating;
import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.enums.UserRole;
import com.englishschool.englishschool.repository.CourseRatingRepository;
import com.englishschool.englishschool.repository.GroupRepository;
import com.englishschool.englishschool.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final CourseRatingRepository courseRatingRepository;

    @Override
    public Long saveUser(UserEntity user) {
        if (user == null) {
            throw new RuntimeException();
        }
        UserEntity newTeacher = new UserEntity();
        if (user.getId() != null) {
            newTeacher = userRepository.findById(user.getId()).orElseThrow(RuntimeException::new);
        }
        newTeacher.setEmail(user.getEmail());
        newTeacher.setSurname(user.getSurname());
        newTeacher.setName(user.getName());
        newTeacher.setUserRole(UserRole.TEACHER);
        newTeacher.setDeleted(Boolean.FALSE);
        return userRepository.save(newTeacher).getId();
    }

    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        user.setDeleted(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public void restoreUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        user.setDeleted(Boolean.FALSE);
        userRepository.save(user);
    }

    @Override
    public GroupEntity getGroupForUser(long userId) {
        UserEntity userEntity = getUser(userId);
        GroupEntity result = new GroupEntity();
        if (userEntity.getUserRole() == UserRole.STUDENT) {
            result = groupService.getGroupForUser(userId);
        } else if (userEntity.getUserRole() == UserRole.TEACHER) {
            result = groupService.getGroupForTeacher(userId);
        }
        return result;
    }

    @Override
    public void assignToGroup(GroupRequest request) {
        if (request == null) {
            return;
        }
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(RuntimeException::new);
        GroupEntity group = groupRepository.findById(request.getGroupId()).orElseThrow(RuntimeException::new);
        if (user.getUserRole().equals(UserRole.TEACHER)) {
            group.setTeacherId(user.getId());
        } else if (user.getUserRole().equals(UserRole.STUDENT)) {
            group.getParticipants().add(user);
        }
        groupRepository.save(group);
    }

    @Override
    public void deleteFromGroup(GroupRequest request) {
        if (request == null) {
            return;
        }
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(RuntimeException::new);
        GroupEntity group = groupRepository.findById(request.getGroupId()).orElseThrow(RuntimeException::new);
        if (user.getUserRole().equals(UserRole.TEACHER)) {
            group.setTeacherId(null);
        } else if (user.getUserRole().equals(UserRole.STUDENT)) {
            group.getParticipants().remove(user);
        }
        groupRepository.save(group);
    }

    @Override
    public void rateCourses(CourseRatingEntity ratingEntity, long userId) {
        if (ratingEntity == null) {
            return;
        }
        CourseRatingEntity newRating = new CourseRatingEntity();
        if (ratingEntity.getId() != null) {
            newRating = courseRatingRepository.findById(ratingEntity.getId()).orElseThrow(RuntimeException::new);
        }
        newRating.setRatingDate(new Date());
        newRating.setUserId(userId);
        newRating.setText(ratingEntity.getText());
        newRating.setMark(ratingEntity.getMark());
        courseRatingRepository.save(newRating);
    }

    @Override
    public List<CourseRating> getRating() {
        List<CourseRatingEntity> courseRatingEntities = courseRatingRepository.findAll();
        Map<Long, String> mapUserIdAndFullName = userRepository.findByIdIn(courseRatingEntities.stream()
        .map(CourseRatingEntity::getUserId).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(UserEntity::getId, x -> x.getSurname() + " " + x.getName()));
        return courseRatingEntities.stream().map(x -> new CourseRating(x, mapUserIdAndFullName.get(x.getUserId())))
                .collect(Collectors.toList());
    }
}
