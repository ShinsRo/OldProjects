package com.siotman.wos.controller;

import com.siotman.wos.domain.dto.api.ErrorResponseDto;
import com.siotman.wos.domain.dto.api.PaperApiDto;
import com.siotman.wos.domain.dto.api.SearchQueryDto;
import com.siotman.wos.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaperCacheController {
    @Autowired
    PaperService paperService;

    @PostMapping("/searchOnCache")
    public ResponseEntity<?> searchOnCache(SearchQueryDto queryDto) {
        ErrorResponseDto error = findError(queryDto);

        if (error != null) {
            return ResponseEntity.badRequest().body(error);
        }

        List<PaperApiDto> data;
        if (queryDto.getType().equals("TI")) {
            data = paperService.searchPaperByTitle(queryDto.getQuery());
        } else if (queryDto.getType().equals("AU")) {
            data = paperService.searchPaperByAuthorName(queryDto.getQuery());
        } else {
            error = ErrorResponseDto.builder().reason(
                    "알 수 없는 쿼리 타입입니다."
            ).build();

            return ResponseEntity.badRequest().body(error);
        }

        return ResponseEntity.ok().body(data);
    }

    private ErrorResponseDto findError(SearchQueryDto queryDto) {
        String errMsg = null;
        if (queryDto.getQuery().length() < 3) {
            errMsg = "검색어 길이는 3자 이상이어야합니다.";
        }

        if (errMsg == null) {
            return null;
        } else {
            return ErrorResponseDto.builder()
                    .reason(errMsg)
                    .build();
        }
    }
}
