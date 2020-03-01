package com.siotman.wos.yourpaper.service;

import com.siotman.wos.jaxws2rest.domain.dto.LamrResultsDto;
import com.siotman.wos.jaxws2rest.domain.dto.LiteRecordDto;
import com.siotman.wos.jaxws2rest.domain.dto.SearchResultsDto;
import com.siotman.wos.yourpaper.domain.criteria.MemberPaperCriteria;
import com.siotman.wos.yourpaper.domain.criteria.MemberPaperSpecification;
import com.siotman.wos.yourpaper.domain.dto.*;
import com.siotman.wos.yourpaper.domain.entity.*;
import com.siotman.wos.yourpaper.exception.NoSuchMemberException;
import com.siotman.wos.yourpaper.repo.MemberPaperRepository;
import com.siotman.wos.yourpaper.repo.MemberRepository;
import com.siotman.wos.yourpaper.repo.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MemberPaperService {
    private MemberRepository        memberRepository;
    private PaperRepository         paperRepository;
    private MemberPaperRepository   memberPaperRepository;

    private WokSearchService            searchService;
    private SearchedCacheService        searchedCacheService;
    private AsyncParsingTriggerService  asyncParsingTriggeringService;

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
    public Integer addOrUpdate(UidsDto uidsDto) throws NoSuchMemberException {
        String username = uidsDto.getUsername();

        Optional<Member> memberOptional = memberRepository.findById(username);
        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        List<MemberPaper> addingList = new LinkedList<>();
        List<PaperDto> parsingList   = new ArrayList<>();
        for (UidDto uidDto : uidsDto.getUids()) {
            String      uid;
            AuthorType  authorType;
            MemberPaper memberPaperEntity;
            Paper       paperEntity;

            uid = uidDto.getUid();
            Optional<Paper> targetPaperOptional = paperRepository.findById(uid);

            paperEntity = targetPaperOptional
                    .orElseGet(() -> searchedCacheService.newPaperEntityFromCacheByUid(uid));

            if (uidDto.getAuthorType() == null) authorType = AuthorType.REFFERING;
            else                                authorType = uidDto.getAuthorType();

            Optional<MemberPaper> memberPaperOptional
                    = memberPaperRepository.findOneByMemberAndPaper(member, paperEntity);

            Long memberPaperId = null;
            if (memberPaperOptional.isPresent()) {
                memberPaperId = memberPaperOptional.get().getId();
            }
            memberPaperEntity = MemberPaper.builder()
                    .id(memberPaperId)
                    .member(member)
                    .paper(paperEntity)
                    .authorType(authorType)
                    .build();

            if (!targetPaperOptional.isPresent()) {
                paperRepository.save(paperEntity);
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

            Optional<MemberPaper> oneByMemberAndPaper = memberPaperRepository.findOneByMemberAndPaper(member, paper);
            if (oneByMemberAndPaper.isPresent()) deleteList.add(oneByMemberAndPaper.get());
        }

        memberPaperRepository.deleteAll(deleteList);
        return true;
    }

    public Page<MemberPaper> listByPage(MemberPaperQueryParameters params) throws NoSuchMemberException {
        Optional<Member> memberOptional = memberRepository.findById(params.getUsername());

        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        SortOption sortOption = params.getSortOption();
        PageOption pageOption = params.getPageOption();

        Pageable pageable = PageRequest.of(
                pageOption.getPage(), pageOption.getCount(),
                (sortOption.getIsAsc())?
                        Sort.by(sortOption.getSortBy()).ascending() :
                        Sort.by(sortOption.getSortBy()).descending()
        );

        Specification<MemberPaper> where = Specification.where(new MemberPaperSpecification(
                new MemberPaperCriteria("member", MemberPaperCriteria.OPERATION.MATCH, member)
        ));

        for (MemberPaperCriteria criteria: params.getCriteria()) {
            where = where.and(new MemberPaperSpecification(criteria));
        }

//        Page<MemberPaper> memberPapers = memberPaperRepository.findAllByMember(member, pageable);
        Page<MemberPaper> memberPapers = memberPaperRepository.findAll(where, pageable);
        return memberPapers;
    }

    public Integer count(MemberDto dto) throws NoSuchMemberException {
        Optional<Member> memberOptional = memberRepository.findById(dto.getUsername());

        Member member;
        if (!memberOptional.isPresent())    throw new NoSuchMemberException();
        else                                member = memberOptional.get();

        return memberPaperRepository.countByMember(member);
    }

    /**
     * 최근 5년 데이터 중,
     * 사용자의 정보 (연관 기관, 영문 명 리스트) 를 이용해 검색하여,
     * 반환된 결과에 대해 최대 50개까지 내 논문으로 등록한다.
     *
     * @param dto
     * @return
     * @throws IOException
     */
    public List<MemberPaper> searchAndAddByMember(MemberDto dto) throws IOException {
        String username = dto.getUsername();
        MemberInfoDto memberInfoDto = dto.getMemberInfoDto();

        List<String> organizations  = memberInfoDto.getOrganizationList();      // 입력받은 소속 기관 목록
        List<String> authors        = memberInfoDto.getAuthorNameList();        // 입력받은 논문 상 영문명 목록

        StringBuilder query = new StringBuilder();
        if (memberInfoDto.getOrganizationList().size() > 0) {
            query
                    .append("AD=(")
                    .append(String.join(" OR ", organizations))
                    .append(")");
        }
        if (authors.size() > 0) {
            if (query.length() > 0) query.append(" AND ");
            query
                    .append("AU=(")
                    .append(String.join(" OR ", authors))
                    .append(")");
        }

        // WOS API 서버를 통해 5년 내 관련 데이터 검색 요청
        SearchResultsDto searchResults = searchService.search(query.toString(),
                "5year", 1, 50);

        List<MemberPaper>   addingList      = new ArrayList<>();
        List<PaperDto>      parsingList     = new ArrayList<>();
        List<LamrResultsDto> lamrData   = searchService.getLamrData(searchResults);
        List<LiteRecordDto> records     = searchResults.getRecords();
        for (int idx = 0; idx < lamrData.size() && idx < records.size(); idx++) {
            LiteRecordDto record    = records.get(idx);
            LamrResultsDto lamr     = lamrData.get(idx);
            Optional<Paper> paperOptional = paperRepository.findById(record.getUid());
            Paper paper;
            if (paperOptional.isPresent()) {
                paper = paperOptional.get();
            } else {
                paper = Paper.buildWithWokResponse(record, lamr);
                parsingList.add(
                        PaperDto.buildWithMemberPaper(
                                MemberPaper.builder()
                                    .paper(paper)
                                    .build())
                );
            }

            addingList.add(
                    MemberPaper.builder()
                        .member(Member.builder().username(username).build())
                        .paper(paper)
                        .authorType(AuthorType.GENERAL)
                        .build()
            );
        }
        if (parsingList.size() > 0) asyncParsingTriggeringService.triggerAll(parsingList);
        return memberPaperRepository.saveAll(addingList);
    }

    @Autowired
    public MemberPaperService(WokSearchService searchService, SearchedCacheService searchedCacheService,
                              AsyncParsingTriggerService asyncParsingTriggeringService, AsyncRunner asyncRunner,
                              MemberRepository memberRepository, PaperRepository paperRepository,
                              MemberPaperRepository memberPaperRepository, RedisTemplate redisTemplate) {
        this.searchService = searchService;
        this.searchedCacheService = searchedCacheService;
        this.asyncParsingTriggeringService = asyncParsingTriggeringService;
        this.memberRepository = memberRepository;
        this.paperRepository = paperRepository;
        this.memberPaperRepository = memberPaperRepository;
    }
}
