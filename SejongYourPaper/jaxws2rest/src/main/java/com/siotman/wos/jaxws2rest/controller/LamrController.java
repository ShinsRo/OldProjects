package com.siotman.wos.jaxws2rest.controller;

import com.siotman.wos.jaxws2rest.component.AsyncRedisService;
import com.siotman.wos.jaxws2rest.component.LamrServiceWrapper;
import com.siotman.wos.jaxws2rest.domain.dto.ErrorMessageDto;
import com.siotman.wos.jaxws2rest.domain.dto.LamrParameterDto;
import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/LamrService")
public class LamrController {
    @Autowired
    AsyncRedisService asyncRedisService;
    @Autowired
    LamrServiceWrapper lamrServiceWrapper;


    @PostMapping("/lamrCorCollectionData")
    public ResponseEntity<?> lamrCorCollectionData(@RequestBody LamrParameterDto dto) {
        if (dto.getUids().size() > 50) {
            return ResponseEntity.badRequest().body(
                    new ErrorMessageDto("한 번에 조회할 수 있는 양은 50개입니다.")
            );
        }

        List<LamrResultsDto> resultsDtos;

        try {
            resultsDtos = lamrServiceWrapper.requestCoreCollectionData(dto.getUids());

            if (resultsDtos.size() > 0)
                asyncRedisService.asyncSaveLamrResults(resultsDtos);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(resultsDtos);
    }

    @PostMapping("/lamrDataCitationIndexData")
    public ResponseEntity<?> lamrDataCitationIndexData(@RequestBody LamrParameterDto dto) {
        return ResponseEntity.ok().body("lamrDataCitationIndexData");
    }

    @PostMapping("/lamrJcrData")
    public ResponseEntity<?> lamrJcrData(@RequestBody LamrParameterDto dto) {
        return ResponseEntity.ok().body("lamrJcrData");
    }

}
