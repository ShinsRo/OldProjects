package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.siotman.wos.yourpaper.domain.converter.JsonMapConverter;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Map;

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

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "uid")
    @JsonIgnoreProperties("users")
    private Paper paper;

    @Enumerated(EnumType.STRING)
    @Column(length = 16)
    private AuthorType authorType;

    @Lob
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Map<String, Integer>> selfTcDataJson;

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
