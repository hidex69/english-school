package com.englishschool.englishschool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_hometask")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHometaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long hometaskId;

    @Column
    private long userId;

    @Column
    private Integer mark;

    @Column
    private boolean isDone;
}
