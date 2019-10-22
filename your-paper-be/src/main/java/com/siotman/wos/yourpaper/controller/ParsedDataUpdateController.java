package com.siotman.wos.yourpaper.controller;

import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import com.siotman.wos.yourpaper.service.ParsedDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parsedData")
public class ParsedDataUpdateController {
    @Autowired
    ParsedDataUpdateService parsedDataUpdateService;

    @PostMapping(value = "/updatePaperData")
    public void updatePaperData(@RequestBody ParsedDataDto parsedDataDto) {
        parsedDataUpdateService.updatePaperData(parsedDataDto);
        return;
    }

    @PostMapping(value = "/updateTcData")
    public void updateTcData(@RequestBody ParsedDataDto parsedDataDto) {
        parsedDataUpdateService.updateTcData(parsedDataDto);
        return;
    }
}
