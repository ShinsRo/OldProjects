package com.siotman.wos.yourpaper.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "member")
@ToString(exclude = {"papers"})
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"username"})
public class Member {
    @Id
    @Column(length = 64)
    private String username;
    @Column(length = 64)
    private String password;
    private Boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<MemberPaper> papers;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRole role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return grantedAuthorities;
    }

    @Builder
    public Member(String username, String password, MemberInfo memberInfo, Set<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.memberInfo = memberInfo;
        this.roles = roles;

        this.papers = new HashSet<>();
        this.enabled = false;
    }
}
