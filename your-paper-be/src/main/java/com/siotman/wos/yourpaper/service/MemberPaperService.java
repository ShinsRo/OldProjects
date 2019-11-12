package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.MemberPaperQueryParameters;
import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.UidDto;
import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import com.siotman.wos.yourpaper.domain.entity.*;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.repo.MemberPaperRepository;
import com.siotman.wos.yourpaper.repo.MemberRepository;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberPaperService {
    @Autowired
    SearchedCacheService searchedCacheService;
    @Autowired
    AsyncParsingTriggerService asyncParsingTriggeringService;
    @Autowired
    AsyncRunner asyncRunner;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PaperRepository paperRepository;
    @Autowired
    MemberPaperRepository memberPaperRepository;
    @Autowired
    RedisTemplate redisTemplate;

    public List<PaperDto> list(MemberDto dto) {
        List<PaperDto> paperDtos = new ArrayList<>();

        List<MemberPaper> memberPapers = memberPaperRepository
                .findAllByMember(Member.builder().username(dto.getUsername()).build());

        for (MemberPaper memberPaper : memberPapers) {
            paperDtos.add(PaperDto.buildWithMemberPaper(memberPaper));
        }
        return paperDtos;
    }

    /** [2019. 10. 17. 김승신]
     * 내 논문 목록에 추가하는 기능을 처리한다.
     *
     * 기존 DB에 존재하지 않는 논문의 경우, 논문 정보 페이지 파싱을 트리거한다.
     * 기존 DB에 존재하는 논문의 경우, 그대로 관계 테이블만을 생성한다.
     *
     * @param   uidsDto 등록 유저와 논문 아이디 리스트 DTO
     * @return
     * @throws  NoSuchMemberException
     */
    public Integer add(UidsDto uidsDto) throws NoSuchMemberException {
        String username = uidsDto.getUsername();

        Optional<Member> memberOptional = memberRepository.findById(username);
        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        List<String> uids = uidsDto.getUids()
                .stream()
                .map(UidDto::getUid)
                .collect(Collectors.toList());
//        List<Paper> alreadySaved        = paperRepository.findAllById(uids);


        List<MemberPaper> addingList = new LinkedList<>();
        List<PaperDto> parsingList   = new ArrayList<>();
        for (UidDto uidDto : uidsDto.getUids()) {
            String      uid;
            AuthorType  authorType;
            MemberPaper memberPaperEntity;
            Paper       paperEntity;

            uid = uidDto.getUid();
            Optional<Paper> targetPaperOptional = paperRepository.findById(uid);

            if (targetPaperOptional.isPresent()) {
                paperEntity = targetPaperOptional.get();
            } else {
                paperEntity = searchedCacheService.newPaperEntityFromCacheByUid(uid);
            }

            if (uidDto.getIsReprint())  authorType = AuthorType.REPRINT;
            else                        authorType = AuthorType.GENERAL;

            memberPaperEntity = MemberPaper.builder()
                    .member(member)
                    .paper(paperEntity)
                    .authorType(authorType)
                    .build();

            if (!targetPaperOptional.isPresent()) {
                parsingList.add(PaperDto.buildWithMemberPaper(memberPaperEntity));
            }
            addingList.add(memberPaperEntity);
        }

        if (parsingList.size() > 0) asyncParsingTriggeringService.triggerAll(parsingList);
        if (addingList.size() > 0)  return memberPaperRepository.saveAll(addingList).size();
        else                        return 0;
    }

    public Boolean delete(UidsDto uidsDto) throws NoSuchMemberException {
        String username = uidsDto.getUsername();

        Optional<Member> memberOptional = memberRepository.findById(username);
        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        List<MemberPaper> deleteList = new ArrayList<>();
        for (UidDto uidDto : uidsDto.getUids()) {
            String uid = uidDto.getUid();

            Paper paper = Paper.builder()
                    .uid(uid)
                    .build();

            deleteList.add(
                    MemberPaper.builder()
                        .member(member)
                        .paper(paper)
                        .build()
            );
        }

        memberPaperRepository.deleteAll(deleteList);
        return true;
    }

    public List<PaperDto> listByPage(MemberPaperQueryParameters params) throws NoSuchMemberException {
        Optional<Member> memberOptional = memberRepository.findById(params.getUsername());

        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        Pageable pageable = PageRequest.of(
                params.getFirstRecord(), params.getCount(),
                (params.getIsAsc())? Sort.by(params.getSortBy()).ascending() : Sort.by(params.getSortBy()).descending()
        );
        List<MemberPaper> memberPapers = memberPaperRepository.findAllByMember(member, pageable);
        List<PaperDto> paperDtos = new ArrayList<>();
        for (MemberPaper memberPaper : memberPapers) {
            paperDtos.add(PaperDto.buildWithMemberPaper(memberPaper));
        }
        return paperDtos;
    }

    public Integer count(MemberDto dto) throws NoSuchMemberException {
        Optional<Member> memberOptional = memberRepository.findById(dto.getUsername());

        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        return memberPaperRepository.countByMember(member);
    }
}
