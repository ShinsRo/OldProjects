package com.siotman.wos.yourpaper.domain.validation;

import com.siotman.wos.yourpaper.domain.dto.UidsDto;

public class UidsDtoValidator extends ParamValidator<UidsDto> {

    @Override
    public Boolean validate(String mappedBy, UidsDto uidsDto) {

        if(uidsDto == null)
            return false;

        switch (mappedBy) {

            case "/add": case "/delete":
                if(uidsDto.getUsername() == null)
                    return false;

                if(uidsDto.getUids().size() < 1)
                    return false;

                if(uidsDto.getUids() == null)
                    return false;

                break;

        } // switch

        return true;
    }
}