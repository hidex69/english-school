package com.englishschool.englishschool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity(name = "blog")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogEntity {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long commentId;

    @Column
    private String data;

}
