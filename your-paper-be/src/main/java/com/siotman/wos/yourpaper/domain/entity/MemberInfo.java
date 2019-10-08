package com.siotman.wos.yourpaper.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member_info")
@ToString(exclude = {"member"})
@Getter
@NoArgsConstructor
public class MemberInfo {
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "memberInfo")
    private Member member;

    @Builder
    public MemberInfo(String name, String dept, String pos, String email) {
        this.name = name;
        this.dept = dept;
        this.pos = pos;
        this.email = email;
        this.member = null;
    }
}
