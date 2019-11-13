package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siotman.wos.yourpaper.domain.converter.JsonListConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(length = 32)
    private String email;

    @Lob
    @Convert(converter = JsonListConverter.class)
    private List<String> authorNameList;

    @Lob
    @Convert(converter = JsonListConverter.class)
    private List<String> organizationList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "memberInfo")
    @JsonIgnore
    private Member member;

    @Builder
    public MemberInfo(String name, String email,
                      List<String> authorNameList, List<String> organizationList, Member member) {
        this.name = name;
        this.email = email;
        this.authorNameList = authorNameList;
        this.organizationList = organizationList;
        this.member = member;
    }
}
