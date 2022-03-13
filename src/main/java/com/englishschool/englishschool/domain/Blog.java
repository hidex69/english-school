package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.entity.BlogEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blog {

    private Long id;
    private Long commentId;
    private String data;

    public Blog(BlogEntity blogEntity) {
        this.id = blogEntity.getId();
        this.commentId = blogEntity.getCommentId();
        this.data = blogEntity.getData();
    }
}
