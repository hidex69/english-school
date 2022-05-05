package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.*;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.enums.UserRole;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Long saveUser(UserRequest user);
    UserEntity getUser(long id);
    List<UserEntity> getUser(Collection<Long> ids);
    void deleteUser(long id);
    void restoreUser(long id);
    GroupWithStudents getGroupForUser(long userId);
    void assignToGroup(GroupRequest request);
    void deleteFromGroup(GroupRequest request);
    void rateCourses(CourseRatingEntity ratingEntity, long userId);
    List<CourseRating> getRating();
    List<UserEntity> getUsersByUserRole(UserRole role);
}
