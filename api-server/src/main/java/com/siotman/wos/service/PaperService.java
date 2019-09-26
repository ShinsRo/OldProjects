package com.siotman.wos.service;

import com.siotman.wos.domain.jpa.Author;
import com.siotman.wos.domain.jpa.Paper;
import com.siotman.wos.domain.dto.api.PaperApiDto;
import com.siotman.wos.repo.AuthorRepository;
import com.siotman.wos.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperService {
    @Autowired
    private PaperRepository paperRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public List<PaperApiDto> searchPaperByTitle(String title) {
        List<Paper> papers = paperRepository.findPapersByTitleContainingIgnoreCase(title);
        List<PaperApiDto> paperApiDtos = papers2paperJsons(papers);
        return paperApiDtos;
    }

    public List<PaperApiDto> searchPaperByAuthorName(String authorName) {
        List<Author> authors = authorRepository.findAuthorsByFullNameContainingIgnoreCase(authorName);
        Map<String, PaperApiDto> dtoMap = new HashMap<>();

        for (Author author: authors) {
            Paper paper = author.getPaper();

            if (!dtoMap.containsKey(paper.getUid())) {
                dtoMap.put(paper.getUid(), new PaperApiDto(paper));
            }
        }

        return dtoMap.values().stream().collect(Collectors.toList());
    }



    private List<PaperApiDto> papers2paperJsons(List<Paper> papers) {
        List<PaperApiDto> paperApiDtos = new ArrayList<>();
        for (Paper paper : papers) {
            paperApiDtos.add(new PaperApiDto(paper));
        }

        return paperApiDtos;
    }
}
