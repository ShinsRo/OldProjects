package com.siotman.wos.yourpaper.domain.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "member_paper")
@Getter
@NoArgsConstructor
public class MemberPaper {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username")
    private Member member;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "uid")
    private Paper paper;

    @Column(length = 16)
    private AuthorType authorType;

    @Builder
    public MemberPaper(Member member, Paper paper, AuthorType authorType) {
        this.member = member;
        this.paper = paper;
        this.authorType = authorType;
    }

    public void setPaper(Paper newPaperEntity) {
        this.paper = newPaperEntity;
    }
}
