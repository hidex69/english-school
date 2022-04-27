package com.englishschool.englishschool.domain;

import com.englishschool.englishschool.entity.BlogEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blog {

    private Long id;
    private String title;
    private String text;
    private Date postingDate;

    public Blog(BlogEntity blogEntity) {
        this.id = blogEntity.getId();
        this.text = blogEntity.getText();
        this.title = blogEntity.getTitle();
        this.postingDate = blogEntity.getPostingDate();
    }
}
