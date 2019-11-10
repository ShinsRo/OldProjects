package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.ParsedDataDto;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.domain.entity.RecordState;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ParsedDataUpdateService {
    private Logger logger = LoggerFactory.getLogger(ParsedDataUpdateService.class);

    @Autowired
    private PaperRepository paperRepository;

    public void updateParsedData(ParsedDataDto parsedDataDto) {
        Optional<Paper> paperOptional = paperRepository.findById(parsedDataDto.getUid());

        if (!paperOptional.isPresent()) {
            logger.error("데이터 베이스에 업데이트할 대상이 존재하지 않음.");
            logger.error(parsedDataDto.getUid());
            throw new NoSuchElementException();
        }

        Paper targetPaper = paperOptional.get();

        try {
            targetPaper.updatePaperData(parsedDataDto);
            RecordState recordState = targetPaper.getRecordState();

            if (recordState.equals(RecordState.PARSING_CITATION_DONE)
                && parsedDataDto.getRecordState().equals(RecordState.ERROR)) {
                targetPaper.setRecordState(RecordState.COMPLETED);
            } else {
                targetPaper.setRecordState(parsedDataDto.getRecordState());
            }
        } catch (Exception e) {
            targetPaper.setRecordState(RecordState.ERROR);
        }
        paperRepository.save(targetPaper);
    }

    public void updateTcData(ParsedDataDto parsedDataDto) {
        Optional<Paper> paperOptional = paperRepository.findById(parsedDataDto.getUid());

        if (!paperOptional.isPresent()) {
            logger.error("데이터 베이스에 업데이트할 대상이 존재하지 않음.");
            logger.error(parsedDataDto.getUid());
            throw new NoSuchElementException();
        }

        Paper targetPaper = paperOptional.get();
        try {
            targetPaper.updateTcData(parsedDataDto);
            RecordState recordState = targetPaper.getRecordState();

            if (recordState.equals(RecordState.PARSING_DETAIL_DONE)
                && parsedDataDto.getRecordState().equals(RecordState.ERROR)) {
                targetPaper.setRecordState(RecordState.COMPLETED);
            } else {
                targetPaper.setRecordState(parsedDataDto.getRecordState());
            }
        } catch (Exception e) {
            targetPaper.setRecordState(RecordState.ERROR);
        }
        paperRepository.save(targetPaper);
    }
}
