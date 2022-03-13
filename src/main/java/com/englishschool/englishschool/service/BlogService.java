package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.entity.BlogEntity;
import org.springframework.stereotype.Service;


public interface BlogService {
    public Blog getBlogById(long id);
}
