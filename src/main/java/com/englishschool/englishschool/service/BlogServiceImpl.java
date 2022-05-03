package com.englishschool.englishschool.service;

import com.englishschool.englishschool.domain.Blog;
import com.englishschool.englishschool.domain.BlogComment;
import com.englishschool.englishschool.entity.BlogCommentEntity;
import com.englishschool.englishschool.entity.BlogEntity;
import com.englishschool.englishschool.entity.UserEntity;
import com.englishschool.englishschool.exception.BadRequsetException;
import com.englishschool.englishschool.exception.EntityNotFoundException;
import com.englishschool.englishschool.repository.BlogCommentRepository;
import com.englishschool.englishschool.repository.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.Date;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final UserService userService;

    @Override
    public List<BlogEntity> getAll() {
        return blogRepository.findAll();
    }

    @Override
    @Transactional
    public Long saveBlog(BlogEntity blogRequest) {
        if (blogRequest == null) {
            return null;
        }
        BlogEntity blogEntity = new BlogEntity();
        if (blogRequest.getId() != null) {
                blogEntity = blogRepository.findById(blogRequest.getId()).orElseThrow(EntityNotFoundException::new);
        }
        blogEntity.setText(blogRequest.getText());
        blogEntity.setPostingDate(new Date());
        blogEntity.setTitle(blogRequest.getTitle());
        return blogRepository.save(blogEntity).getId();
    }

    @Override
    public Blog getBlogById(long id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new Blog(blogEntity);
    }

    @Override
    public List<BlogComment> getBlogComments(Long blogId) {
        if (blogId == null) {
            throw new BadRequsetException();
        }
        List<BlogCommentEntity> comments = blogCommentRepository.findByBlogId(blogId);
        Map<Long, UserEntity> userMap = userService.getUser(comments.stream().map(BlogCommentEntity::getUserId).collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, x -> x));
        return comments.stream().map(x -> new BlogComment(userMap.get(x.getUserId()), x.getText(), x.getPostingDate())).collect(Collectors.toList());
    }

    @Override
    public void saveBlogComment(BlogCommentEntity comment, Long userId) {
        if (comment == null) {
            return;
        }
        UserEntity user = userService.getUser(userId);
        BlogCommentEntity newComment = new BlogCommentEntity();
        if (comment.getId() != null) {
            newComment = blogCommentRepository.findById(comment.getId()).orElseThrow(EntityNotFoundException::new);
        }
        blogRepository.findById(comment.getBlogId()).orElseThrow(EntityNotFoundException::new);
        newComment.setBlogId(comment.getBlogId());
        newComment.setText(comment.getText());
        newComment.setPostingDate(new Date());
        newComment.setUserId(userId);
        blogCommentRepository.save(newComment);
    }
}
