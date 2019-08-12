package com.siotman.batchwos.batch.controller;

import com.siotman.batchwos.batch.domain.dto.PaperDataDto;
import com.siotman.batchwos.batch.domain.jpa.Paper;
import com.siotman.batchwos.batch.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional      // 여러 호출로 인해 활성화한 DB 커넥션이 풀로 돌아가지 않고 얼어버리는 현상을 막는다.
@CrossOrigin("*")
public class RecordCrudController {
    @Autowired PaperRepository paperRepository;

    @PostMapping(value = "/savePaperData")
    public void savePaperData(@RequestBody PaperDataDto paperDataDto) {
        System.out.println(paperDataDto);
        Paper target    = paperRepository.findById(paperDataDto.getUid()).get();
        paperDataDto.updateEntityDetail(target);

        paperRepository.save(target);
    }

    @PostMapping(value = "/saveTcData")
    public void saveTcData(@RequestBody TcDataDto tcDataDto) {
        System.out.println(tcDataDto);
        Paper target    = paperRepository.findById(tcDataDto.getUid()).get();
        tcDataDto.updateEntityTcData(target);

        paperRepository.save(target);
    }
}
