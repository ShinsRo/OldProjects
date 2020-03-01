package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.domain.entity.MemberInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private String username;
    private String password;
    private MemberInfoDto memberInfoDto;

    @Builder
    private MemberDto(String username, String password, MemberInfoDto memberInfoDto) {
        this.username       = username;
        this.password       = password;
        this.memberInfoDto  = memberInfoDto;
    }

    public void update(MemberDto dto) {
        String newPassword      = dto.getPassword();
        MemberInfoDto newInfo   = dto.getMemberInfoDto();

        if (newPassword != null) this.password = newPassword;
        this.memberInfoDto.update(newInfo);
    }

    public static MemberDto buildWithMember(final Member member) {
        MemberInfo memberInfo = member.getMemberInfo();

        return builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .memberInfoDto(
                        MemberInfoDto.builder()
                                .name(memberInfo.getName())
                                .email(memberInfo.getEmail())
                                .authorNameList(memberInfo.getAuthorNameList())
                                .organizationList(memberInfo.getOrganizationList())
                            .build())
                .build();
    }
}
