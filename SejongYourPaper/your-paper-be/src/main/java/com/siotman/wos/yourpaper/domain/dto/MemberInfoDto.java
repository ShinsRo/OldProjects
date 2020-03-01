package com.siotman.wos.yourpaper.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberInfoDto {
    private String name;
    private String email;
    private List<String> authorNameList;
    private List<String> organizationList;

    @Builder
    public MemberInfoDto(String name, String email, List<String> authorNameList, List<String> organizationList) {
        this.name = name;
        this.email = email;
        this.authorNameList = authorNameList;
        this.organizationList = organizationList;
    }

    public MemberInfoDto update(MemberInfoDto newInfo) {
        if (newInfo == null) return this;

        String newName = newInfo.getName();
        String newEmail = newInfo.getEmail();
        List<String> newNameList = newInfo.getAuthorNameList();
        List<String> newOrgnList = newInfo.getOrganizationList();

        if (newName != null)            this.name = newName;
        if (newEmail != null)           this.email = newEmail;
        if (newNameList != null)        this.authorNameList = newNameList;
        if (organizationList != null)   this.organizationList = newOrgnList;

        return this;
    }
}
