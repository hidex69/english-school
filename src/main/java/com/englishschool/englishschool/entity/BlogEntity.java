package com.englishschool.englishschool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.Date;

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
    private String title;

    @Column
    private Date postingDate;

    @Column
    private String text;

}
