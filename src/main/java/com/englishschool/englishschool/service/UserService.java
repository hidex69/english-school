package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.CourseRating;
import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.domain.UserRequest;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.GroupEntity;
import com.englishschool.englishschool.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public interface UserService {
    Long saveUser(UserRequest user);
    UserEntity getUser(long id);
    List<UserEntity> getUser(Collection<Long> ids);
    void deleteUser(long id);
    void restoreUser(long id);
    GroupEntity getGroupForUser(long userId);
    void assignToGroup(GroupRequest request);
    void deleteFromGroup(GroupRequest request);
    void rateCourses(CourseRatingEntity ratingEntity, long userId);
    List<CourseRating> getRating();
    void register(String email,String password);
}
