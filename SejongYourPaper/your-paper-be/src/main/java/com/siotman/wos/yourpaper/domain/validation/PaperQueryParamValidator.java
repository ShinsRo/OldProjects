package com.siotman.wos.yourpaper.domain.validation;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.MemberPaperQueryParameters;

public class PaperQueryParamValidator extends ParamValidator<MemberPaperQueryParameters> {

    @Override
    public Boolean validate(String mappedBy, MemberPaperQueryParameters memberPaperQueryParameters) {

        if(memberPaperQueryParameters == null)
            return false;

        switch (mappedBy) {

            case "/listByPage":
                if(memberPaperQueryParameters.getUsername() == null)
                    return false;

                // 검토 요망...
//                if(!memberPaperQueryParameters.getSortOption().getSortBy().equals("title"))
//                    return false;
//
//                if(!memberPaperQueryParameters.getSortBy().equals("timesCited"))
//                    return false;

                if(memberPaperQueryParameters.getPageOption().getPage() < 0)
                    return false;

                if(memberPaperQueryParameters.getPageOption().getCount() < 0
                || memberPaperQueryParameters.getPageOption().getCount() > 50)
                    return false;

                break;

        } // switch

        return true;
    }
}
