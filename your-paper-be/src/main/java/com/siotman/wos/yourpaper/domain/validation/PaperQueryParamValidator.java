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

                if(!memberPaperQueryParameters.getSortBy().equals("title"))
                    return false;

                if(!memberPaperQueryParameters.getSortBy().equals("timesCited"))
                    return false;

                if(memberPaperQueryParameters.getFirstRecord() < 0)
                    return false;

                if(memberPaperQueryParameters.getCount() < 0
                || memberPaperQueryParameters.getCount() > 50)
                    return false;

                break;

        } // switch

        return true;
    }
}
