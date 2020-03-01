package com.siotman.wos.yourpaper.controller;

import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import com.siotman.wos.yourpaper.service.ParsedDataUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/parsedData")
public class ParsedDataUpdateController {
    private Logger logger = LoggerFactory.getLogger(ParsedDataUpdateController.class);
    @Autowired
    ParsedDataUpdateService parsedDataUpdateService;

    @PostMapping(value = "/updatePaperData")
    public void updateParsedData(@RequestBody ParsedDataDto parsedDataDto) {
        parsedDataUpdateService.updateParsedData(parsedDataDto);
        return;
    }

    @PostMapping(value = "/updateTcData")
    public void updateTcData(@RequestBody ParsedDataDto parsedDataDto) {
        parsedDataUpdateService.updateTcData(parsedDataDto);
        return;
    }
}
