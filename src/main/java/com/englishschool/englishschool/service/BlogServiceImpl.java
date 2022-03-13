package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.entity.BlogEntity;
import com.englishschool.englishschool.repository.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public Blog getBlogById(long id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow(RuntimeException::new);
        return new Blog(blogEntity);
    }
}
