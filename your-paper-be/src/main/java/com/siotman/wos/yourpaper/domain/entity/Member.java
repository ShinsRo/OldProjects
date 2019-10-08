package com.siotman.wos.yourpaper.domain.entity;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private Set<MemberRole> roles;

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (MemberRole role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return grantedAuthorities;
    }

    @Builder
    public Member(final PasswordEncoder encoder, final MemberDto dto) {
        this.username = dto.getUsername();
        _setPasswordWithEncoder(encoder, dto.getPassword());

        this.memberInfo = MemberInfo.builder()
                .name(dto.getName())
                .build();

        this.roles = new HashSet<>();
        this.roles.add(MemberRole.ROLE_USER);

        this.papers = new HashSet<>();
        this.enabled = false;
    }

    public void updateMember(PasswordEncoder encoder, MemberDto dto) {
        String newName          = dto.getName();
        String newPassword      = dto.getPassword();

        if (newName != null) {
            this.memberInfo.updateName(dto.getName());
        }

        if (newPassword != null) {
            _setPasswordWithEncoder(encoder, dto.getPassword());
        }
    }

    private void _setPasswordWithEncoder(PasswordEncoder encoder, String password) {
        this.password = encoder.encode(password);
    }
}
