package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private String username;
    private String password;
    private String name;

    @Builder
    private MemberDto(final Member member) {
        this.username   = member.getUsername();
        this.password   = member.getPassword();
        this.name       = member.getMemberInfo().getName();
    }

    public void updateMemberDto(MemberDto dto) {
        String newPassword  = dto.getPassword();
        String newName      = dto.getName();

        if (newPassword != null)    this.password = newPassword;
        if (newName != null)        this.name = newName;
    }
}
