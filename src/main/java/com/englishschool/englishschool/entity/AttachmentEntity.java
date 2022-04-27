package com.englishschool.englishschool.entity;

import com.englishschool.englishschool.enums.ContentType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "attachment")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AttachmentEntity {
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
    private ContentType entityType;

    @Column
    private Long entityId;


}
