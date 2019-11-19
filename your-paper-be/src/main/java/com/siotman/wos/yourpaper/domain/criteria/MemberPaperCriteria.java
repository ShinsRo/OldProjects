package com.siotman.wos.yourpaper.domain.criteria;

import com.siotman.wos.yourpaper.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPaperCriteria {
    private String field;
    private OPERATION operation;
    private Object value;


    public enum OPERATION {
        LIKE, IGNORE, MATCH
    }
}
