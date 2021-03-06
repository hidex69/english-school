package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.*;
import com.englishschool.englishschool.entity.*;
import com.englishschool.englishschool.enums.UserRole;
import com.englishschool.englishschool.exception.BadRequsetException;
import com.englishschool.englishschool.exception.EntityNotFoundException;
import com.englishschool.englishschool.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final CourseRatingRepository courseRatingRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoRepository userInfoRepository;

    @Override
    public Group getGroup(long id) {
        GroupEntity groupEntity = groupService.getGroupEntityById(id);
        UserEntity teacher = null;
        if (groupEntity.getTeacherId() != null) {
            teacher = getUser(groupEntity.getTeacherId());
        }
        return new Group(groupEntity.getId(), groupEntity.getName(), groupEntity.getParticipants(), teacher);
    }

    @Override
    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserEntity> getFreeTeachers() {
        List<Long> ids = groupRepository.findAll().stream().map(GroupEntity::getTeacherId).collect(Collectors.toList());
        return getUsersByUserRole(UserRole.TEACHER).stream().filter(x -> !ids.contains(x.getId())).collect(Collectors.toList());
    }

    @Override
    public List<UserEntity> getUsersByUserRole(UserRole role) {
        return userRepository.findByUserRole(role);
    }

    @Override
    public Long saveUser(UserRequest user) {
        if (user == null) {
            throw new RuntimeException();
        }
        UserEntity newUser = new UserEntity();
        UserInfoEntity newUserInfo = new UserInfoEntity();
        if (user.getId() != null) {
            newUser = userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);
        }
        Optional<UserEntity> checkEmail = userRepository.findByEmail(user.getEmail());
        if (checkEmail.isPresent() && user.getUserRole() == UserRole.NEW) {
            throw new BadRequsetException("This email already exists");
        }
        newUser.setEmail(user.getEmail());
        newUser.setSurname(user.getSurname());
        newUser.setName(user.getName());
        newUser.setUserRole(user.getUserRole());
        newUser.setDeleted(Boolean.FALSE);
        long userId = userRepository.save(newUser).getId();
        if (user.getId() == null) {
            newUserInfo.setUserId(userId);
            newUserInfo.setPassword(passwordEncoder.encode(user.getPassword()));
            userInfoRepository.save(newUserInfo);
        }
        return userId;
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<UserEntity> getUser(Collection<Long> ids) {
        return userRepository.findByIdIn(ids);
    }

    public UserEntity getUser(String username) {
        return userRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setDeleted(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public void restoreUser(long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setDeleted(Boolean.FALSE);
        userRepository.save(user);
    }

    private static final String BASE_URL = "localhost:8080/api/v1/hometask/";
    private final UserHometaskRepository userHometaskRepository;
    private final HometaskRepository hometaskRepository;

    private List<HometaskMark> getMarks(long userId) {
        UserEntity user = getUser(userId);
        List<UserHometaskEntity> userHometaskEntities = userHometaskRepository.findByUserId(userId);
        List<HometaskEntity> hometaskEntities = hometaskRepository.findByIdIn(
                userHometaskEntities.stream().map(UserHometaskEntity::getHometaskId).collect(Collectors.toList())
        );
        Map<Long, HometaskEntity> map = userHometaskEntities.stream().collect(Collectors.toMap(UserHometaskEntity::getHometaskId,
                x -> hometaskEntities.stream().filter(i -> i.getId() == x.getHometaskId()).findFirst().get()));
        return userHometaskRepository.findByUserId(userId).stream()
                .map(x -> new HometaskMark(
                        map.get(x.getHometaskId()).getName(),
                        x.getMark() == null ? -1 : x.getMark(),
                        BASE_URL + x.getHometaskId(),
                        map.get(x.getHometaskId()).getDate()
                )).collect(Collectors.toList());
    }

    @Override
    public GroupWithStudents getGroupForUser(long userId) {
        UserEntity userEntity = getUser(userId);
        GroupEntity result = new GroupEntity();
        if (userEntity.getUserRole() == UserRole.STUDENT) {
            result = groupService.getGroupForUser(userId);
        } else if (userEntity.getUserRole() == UserRole.TEACHER) {
            result = groupService.getGroupForTeacher(userId);
        }
        UserEntity teacher = getUser(result.getTeacherId());
        List<StudentWithMark> studentWithMarks = result.getParticipants()
                .stream().map(x -> new StudentWithMark(x.getId(), x.getName() + " " + x.getSurname(), getMarks(x.getId())))
                .collect(Collectors.toList());
        return new GroupWithStudents(result.getId(), result.getName(), teacher, studentWithMarks);
    }

    @Override
    @Transactional
    public void assignToGroup(GroupRequest request) {
        if (request == null) {
            return;
        }
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(EntityNotFoundException::new);
        GroupEntity group = groupRepository.findById(request.getGroupId()).orElseThrow(EntityNotFoundException::new);
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
        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(EntityNotFoundException::new);
        GroupEntity group = groupRepository.findById(request.getGroupId()).orElseThrow(EntityNotFoundException::new);
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
            newRating = courseRatingRepository.findById(ratingEntity.getId()).orElseThrow(EntityNotFoundException::new);
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
        Map<Long, UserEntity> mapUserIdAndFullName = userRepository.findByIdIn(courseRatingEntities.stream()
        .map(CourseRatingEntity::getUserId).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(UserEntity::getId, x -> x));
        return courseRatingEntities.stream().map(x -> new CourseRating(x, mapUserIdAndFullName.get(x.getUserId())))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUser(username);
        UserInfoEntity userInfoEntity = userInfoRepository.findByUserId(userEntity.getId()).orElseThrow(EntityNotFoundException::new);
        return new User(userEntity.getEmail(), userInfoEntity.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(
                userEntity.getUserRole().toString()
        )));
    }
}
