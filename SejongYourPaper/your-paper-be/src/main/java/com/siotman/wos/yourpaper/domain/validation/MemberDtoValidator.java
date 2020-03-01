package com.siotman.wos.yourpaper.domain.validation;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;

public class MemberDtoValidator extends ParamValidator<MemberDto> {

    @Override
    public Boolean validate(String mappedBy, MemberDto memberDto) {
        if (memberDto == null) return false;

        switch (mappedBy) {

            case "/login": case "/logout":
                if(memberDto.getUsername() == null
                        || memberDto.getPassword() == null)
                    return false;

                break;

            case "/register": case "/update":
                if(memberDto.getUsername() == null
                        || memberDto.getPassword() == null)
                    return false;

                if(memberDto.getMemberInfoDto().getName() == null)
                    return false;

                if(memberDto.getMemberInfoDto().getOrganizationList() == null
                || memberDto.getMemberInfoDto().getAuthorNameList() == null)
                    return false;

                if(memberDto.getMemberInfoDto().getOrganizationList().size() < 1)
                    return false;

                break;

            case "/autoSearchAndAdd":
                if(memberDto.getUsername() == null)
                    return false;

                if(memberDto.getMemberInfoDto().getName() == null)
                    return false;

                if(memberDto.getMemberInfoDto().getOrganizationList() == null
                        ||memberDto.getMemberInfoDto().getAuthorNameList() == null)
                    return false;

                if(memberDto.getMemberInfoDto().getOrganizationList().size() < 1)
                    return false;

                break;

            case "/availableCheck": case "/count": case "/listAll":
                if(memberDto.getUsername() == null)
                    return false;

                break;

        } // switch

        return true;
    }
}
