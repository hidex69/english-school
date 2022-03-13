package com.englishschool.englishschool.controller;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/get-blog/{id}")
    public Blog getBlog(@PathVariable long id) {
        return blogService.getBlogById(id);
    }

}
