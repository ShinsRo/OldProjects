package com.siotman.wos.parsingtrigger.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 16)
    private String name;

    @Column(length = 16)
    private String dept;

    @Column(length = 16)
    private String pos;

    @Column(length = 32)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userInfo")
    private User user;
}
