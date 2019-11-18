package com.siotman.wos.yourpaper.domain.validation;

import java.util.Set;

abstract class ParamValidator<DTO>{

    abstract Boolean validate(String method, DTO dto);

}