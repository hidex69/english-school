package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.domain.BlogComment;
import com.englishschool.englishschool.entity.BlogCommentEntity;
import com.englishschool.englishschool.entity.BlogEntity;

import java.util.List;


public interface BlogService {
    Blog getBlogById(long id);
    void saveBlog(BlogEntity blogRequest);
    List<BlogComment> getBlogComments(Long blogId);
    void saveBlogComment(BlogCommentEntity comment, Long userId);
 }
