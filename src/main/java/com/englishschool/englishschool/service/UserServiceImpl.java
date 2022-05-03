package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.CourseRating;
import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.domain.UserRequest;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.entity.UserInfoEntity;
import com.englishschool.englishschool.enums.UserRole;
import com.englishschool.englishschool.exception.BadRequsetException;
import com.englishschool.englishschool.exception.EntityNotFoundException;
import com.englishschool.englishschool.repository.CourseRatingRepository;
import com.englishschool.englishschool.repository.GroupRepository;
import com.englishschool.englishschool.repository.UserInfoRepository;
import com.englishschool.englishschool.repository.UserRepository;
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
    public void register(String email, String password) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new BadRequsetException("Such user exists");
        }
        UserEntity userEntity = new UserEntity();
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
        if (checkEmail.isPresent()) {
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

    public UserEntity getUser(long id) {
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
