package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.entity.BlogCommentEntity;
import com.englishschool.englishschool.entity.BlogEntity;
import com.englishschool.englishschool.repository.BlogCommentRepository;
import com.englishschool.englishschool.repository.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import java.util.Date;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogCommentRepository blogCommentRepository;

    @Override
    @Transactional
    public Long saveBlog(BlogEntity blogRequest) {
        if (blogRequest == null) {
            return null;
        }
        BlogEntity blogEntity = new BlogEntity();
        if (blogRequest.getId() != null) {
                blogEntity = blogRepository.findById(blogRequest.getId()).orElseThrow(RuntimeException::new);
        }
        blogEntity.setText(blogRequest.getText());
        blogEntity.setPostingDate(new Date());
        blogEntity.setTitle(blogRequest.getTitle());
        return blogRepository.save(blogEntity).getId();
    }

    @Override
    public Blog getBlogById(long id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow(RuntimeException::new);
        return new Blog(blogEntity);
    }

    @Override
    public List<BlogCommentEntity> getBlogComments(Long blogId) {
        if (blogId == null) {
            throw new RuntimeException();
        }
        return blogCommentRepository.findByBlogId(blogId);
    }

    @Override
    public void saveBlogComment(BlogCommentEntity comment) {
        if (comment == null) {
            return;
        }
        blogRepository.findById(comment.getBlogId()).orElseThrow(RuntimeException::new);
        blogCommentRepository.save(comment);
    }
}
