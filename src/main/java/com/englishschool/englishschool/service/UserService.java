package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.CourseRating;
import com.englishschool.englishschool.domain.GroupRequest;
import com.englishschool.englishschool.entity.CourseRatingEntity;
import com.englishschool.englishschool.entity.UserEntity;

import java.util.List;

public interface UserService {
    Long saveUser(UserEntity user);
    UserEntity getUser(long id);
    void deleteUser(long id);
    void restoreUser(long id);
    void assignToGroup(GroupRequest request);
    void deleteFromGroup(GroupRequest request);
    void rateCourses(CourseRatingEntity ratingEntity, long userId);
    List<CourseRating> getRating();
}
