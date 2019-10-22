package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ParsedDataUpdateService {
    @Autowired
    private PaperRepository paperRepository;

    public void updatePaperData(ParsedDataDto parsedDataDto) {
        Optional<Paper> paperOptional = paperRepository.findById(parsedDataDto.getUid());

        if (!paperOptional.isPresent()) {
            throw new NoSuchElementException();
        }

        Paper targetPaper = paperOptional.get();
        targetPaper.updatePaperData(parsedDataDto);

        paperRepository.save(targetPaper);
    }

    public void updateTcData(ParsedDataDto parsedDataDto) {
        Optional<Paper> paperOptional = paperRepository.findById(parsedDataDto.getUid());

        if (!paperOptional.isPresent()) {
            throw new NoSuchElementException();
        }

        Paper targetPaper = paperOptional.get();
        targetPaper.updateTcData(parsedDataDto);

        paperRepository.save(targetPaper);
    }
}
