package com.siotman.wos.yourpaper.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserPaper {
    @Id
    @GeneratedValue
    private Long upid;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "uid")
    private Paper paper;

    @Column(length = 16)
    private String type;
}
