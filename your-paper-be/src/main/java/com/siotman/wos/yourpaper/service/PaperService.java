package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.PaperSearchParameter;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import com.siotman.wos.yourpaper.repo.PaperSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PaperService {
    private PaperRepository paperRepository;

    public Page<Paper> search(PaperSearchParameter parameter) {
        Sort sortBy = Sort.by(parameter.getSortBy());
        Page<Paper> searchResults = paperRepository.findAll(
                PaperSpecification.searchPaperLike(parameter.getFieldName(), parameter.getValue()),
                PageRequest.of(
                        parameter.getPage(), parameter.getCount(),
                        (parameter.getIsAsc()) ? sortBy : sortBy.descending()
                ));

        return searchResults;
    }

    @Autowired
    public PaperService(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }
}
