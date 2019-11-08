package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.domain.entity.RecordState;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ParsedDataUpdateService {
    @Autowired
    private PaperRepository paperRepository;

    public void updateParsedData(ParsedDataDto parsedDataDto) {
        Optional<Paper> paperOptional = paperRepository.findById(parsedDataDto.getUid());

        if (!paperOptional.isPresent()) {
            throw new NoSuchElementException();
        }

        Paper targetPaper = paperOptional.get();
        try {
            targetPaper.updatePaperData(parsedDataDto);

            if (targetPaper.getTimesCited().equals("0")) targetPaper.setRecordState(RecordState.COMPLETED);
            else targetPaper.setRecordState(RecordState.PARSING);

        } catch (Exception e) {
            targetPaper.setRecordState(RecordState.ERROR);
        }
        paperRepository.save(targetPaper);
    }

    public void updateTcData(ParsedDataDto parsedDataDto) {
        Optional<Paper> paperOptional = paperRepository.findById(parsedDataDto.getUid());

        if (!paperOptional.isPresent()) {
            throw new NoSuchElementException();
        }

        Paper targetPaper = paperOptional.get();
        try {
            targetPaper.updateTcData(parsedDataDto);
            targetPaper.setRecordState(RecordState.COMPLETED);
        } catch (Exception e) {
            targetPaper.setRecordState(RecordState.ERROR);
        }
        paperRepository.save(targetPaper);
    }
}
