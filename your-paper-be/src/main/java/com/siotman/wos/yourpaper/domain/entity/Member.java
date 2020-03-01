package com.siotman.wos.yourpaper.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.MemberInfoDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private boolean disabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id", referencedColumnName = "id")
    private MemberInfo memberInfo;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<MemberPaper> papers;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    private Set<MemberRole> roles;

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (MemberRole role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return grantedAuthorities;
    }

    @Builder
    public Member(String username, String password, boolean enabled, MemberInfo memberInfo) {
        this.username = username;
        _setPasswordWithEncoder(password);
        this.memberInfo = memberInfo;
        this.roles = new HashSet<>();
        this.roles.add(MemberRole.ROLE_USER);
        this.papers = new HashSet<>();
        this.disabled = enabled;
    }

    public void update(MemberDto dto) {
        this.username = dto.getUsername();
        String newPassword      = dto.getPassword();
        MemberInfoDto infoDto   = dto.getMemberInfoDto();

        if (newPassword != null) {
            _setPasswordWithEncoder(newPassword);
        }
        this.memberInfo = MemberInfo.builder()
                .name(infoDto.getName())
                .email(infoDto.getEmail())
                .authorNameList(infoDto.getAuthorNameList())
                .organizationList(infoDto.getOrganizationList())
            .build();
    }

    private void _setPasswordWithEncoder(String password) {
        if (password == null) return;
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
