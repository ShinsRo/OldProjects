package com.siotman.wos.jaxws2rest.controller;

import com.siotman.wos.jaxws2rest.JaxwsApplication;
import com.siotman.wos.jaxws2rest.component.AsyncRedisService;
import com.siotman.wos.jaxws2rest.component.AuthSidContainer;
import com.siotman.wos.jaxws2rest.component.SearchServiceWrapper;
import com.siotman.wos.jaxws2rest.domain.dto.ErrorMessageDto;
import com.siotman.wos.jaxws2rest.domain.dto.RetrieveParameterDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchParameterDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchResultsDto;
import com.siotman.wos.jaxws2rest.exception.SidContainerException;
import com.thomsonreuters.wokmws.v3.woksearchlite.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/WokSearchService")
public class WokSearchController {
    @Autowired
    AsyncRedisService asyncRedisService;
    @Autowired
    AuthSidContainer authSidContainer;
    @Autowired
    SearchServiceWrapper searchServiceWrapper;


    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchParameterDto dto) {
        ResponseEntity<?> response  = null;
        SearchResults results       = null;
        String SID                  = null;

        try {
            SID = authSidContainer.getSessionId(
                    JaxwsApplication.WS_WOS_USERNAME,
                    JaxwsApplication.WS_WOS_PASSWORD
            );

            results = searchServiceWrapper.search(SID, dto.getQueryParameters(), dto.getRetrieveParameters());
            SearchResultsDto searchResultsDto = new SearchResultsDto(SID, results);

            if (searchResultsDto.getRecordsFound() > 0)
                asyncRedisService.asyncSaveSearchResults(searchResultsDto);

            response = ResponseEntity.ok(searchResultsDto);
        } catch (Throwable e) {
            e.printStackTrace();
            response = _getErrorResponseFrom(e);
        }

        return response;
    }

    @PostMapping("/retrieve")
    public ResponseEntity<?> retrieve(@RequestBody RetrieveParameterDto dto) {
        ResponseEntity<?> response  = null;
        SearchResults results       = null;

        try {
            results = searchServiceWrapper.retrieve(dto.getSID(), dto.getQueryId(), dto.getRetrieveParameters());
            SearchResultsDto searchResultsDto = new SearchResultsDto(dto.getSID(), results);

            if (searchResultsDto.getRecordsFound() > 0)
                asyncRedisService.asyncSaveSearchResults(searchResultsDto);

            response = ResponseEntity.ok(searchResultsDto);
        } catch (Throwable e) {
            e.printStackTrace();
            response = _getErrorResponseFrom(e);
        }

        return response;
    }



    private ResponseEntity<ErrorMessageDto> _getErrorResponseFrom(Throwable e) {
        ResponseEntity<ErrorMessageDto> response    = null;
        ErrorMessageDto dto                         = null;

        // 임시 처리
        dto = new ErrorMessageDto();
        dto.setMessage(e.getMessage());
        response = ResponseEntity.badRequest().body(dto);
        // 임시 처리 끝

        if          (e instanceof SidContainerException) {

        } else if   (e instanceof QueryExceptionException) {

        } else if   (e instanceof InvalidInputExceptionException) {

        } else if   (e instanceof AuthenticationExceptionException) {

        } else if   (e instanceof InternalServerExceptionException) {

        } else if   (e instanceof ESTIWSExceptionException) {

        } else {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dto);
        }

        return response;
    }

}
