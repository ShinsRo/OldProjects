package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.dto.MemberDto;
import com.siotman.wos.yourpaper.domain.dto.PaperDto;
import com.siotman.wos.yourpaper.domain.dto.UidDto;
import com.siotman.wos.yourpaper.domain.dto.UidsDto;
import com.siotman.wos.yourpaper.domain.entity.AuthorType;
import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.domain.entity.Paper;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.repo.MemberPaperRepository;
import com.siotman.wos.yourpaper.repo.MemberRepository;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperService {
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
                .findAllByMember(Member.builder().dto(dto).build());

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
    public Boolean add(UidsDto uidsDto) throws NoSuchMemberException {
        String username = uidsDto.getUsername();

        Optional<Member> memberOptional = memberRepository.findById(username);
        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        List<MemberPaper> addingList = new LinkedList<>();
        List<String> targetUidList   = new LinkedList<>();
        List<PaperDto> parsingList   = new ArrayList<>();
        for (UidDto uidDto : uidsDto.getUids()) {
            String      uid;
            AuthorType  authorType;
            Paper       targetPaperEntity;
            MemberPaper memberPaperEntity;

            uid                 = uidDto.getUid();
            targetPaperEntity   = Paper.builder().uid(uid).build();
            if (uidDto.getIsReprint())  authorType = AuthorType.REPRINT;
            else                        authorType = AuthorType.GENERAL;

            memberPaperEntity = MemberPaper.builder()
                    .member(member)
                    .paper(targetPaperEntity)
                    .authorType(authorType)
                    .build();

            targetUidList.add(uid);
            addingList.add(memberPaperEntity);
        }

        List<Paper> alreadySaved        = paperRepository.findAllById(targetUidList);
        for (int idx = 0; idx < addingList.size(); idx++) {
            MemberPaper memberPaperEntity = addingList.get(idx);
            int savedIdx = alreadySaved.indexOf(memberPaperEntity.getPaper());
            if (savedIdx < 0)   {
                Paper newPaperEntity = searchedCacheService.newPaperEntityFromCacheByUid(
                        memberPaperEntity.getPaper().getUid());
                memberPaperEntity.setPaper(newPaperEntity);
                parsingList.add(PaperDto.buildWithMemberPaper(memberPaperEntity));
                continue;
            }

            addingList.remove(idx--);
            alreadySaved.remove(savedIdx);
        }

        if (parsingList.size() > 0) asyncParsingTriggeringService.triggerAll(parsingList);
        if (addingList.size() > 0)  memberPaperRepository.saveAll(addingList);
        return addingList.size() > 0;
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
}
