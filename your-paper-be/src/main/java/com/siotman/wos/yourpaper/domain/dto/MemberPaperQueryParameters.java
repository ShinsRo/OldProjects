package com.siotman.wos.yourpaper.domain.dto;

import com.siotman.wos.yourpaper.domain.criteria.MemberPaperCriteria;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberPaperQueryParameters {
    private String username;
    private List<MemberPaperCriteria> criteria;

    private SortOption sortOption;
    private PageOption pageOption;
}
