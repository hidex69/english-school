package com.englishschool.englishschool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "hometask")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HometaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @JsonIgnore
    private byte[] data;

    @Column
    private String contentType;

    @Column
    private Date endDate;
}
