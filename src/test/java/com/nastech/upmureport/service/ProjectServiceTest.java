package com.nastech.upmureport.service;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {
	// Member repositories
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberSystemRepository memberSystemRepository;
	@Autowired
	AuthInfoRepository authinfoRepository;
	@Autowired
	CareerRepository careerRepository;
	
	//Project and pdir repositories
	@Autowired
	MemberProjectRepository memberProjectRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	PdirRepository pdirRepository;
	
	@Autowired
	TestData td;
	
	//Project Services
	@Autowired
	ProjectService ps;
	
	@Before
	public void setUp() {
		td.setMemberTestData();
		td.setProjectTestData();
		td.setPdirTestData();
	}
	
	@Test
	@Transactional
	public void 멤버의_프로젝트목록_가져오기_테스트() {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		Member mks = memberRepository.findOneByEidAndDflagFalse("1112");
		Member kys = memberRepository.findOneByEidAndDflagFalse("1113");
		
		String kssMid = kss.getMid().toString();
		String mksMid = mks.getMid().toString();
		String kysMid = kys.getMid().toString();
		
		List<ProjectDto> pDTOList;
		
		pDTOList = ps.listByMid(kssMid);
		assertTrue(pDTOList.size() == 3);
		pDTOList = ps.listByMid(mksMid);
		assertTrue(pDTOList.size() == 2);
		pDTOList = ps.listByMid(kysMid);
		assertTrue(pDTOList.size() == 2);
	}
	
	@Test
	@Transactional
	public void 프로젝트_생성하기_테스트() throws Exception {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		List<ProjectDto> pDTOList = ps.listByMid(kssMid);
		Integer size = pDTOList.size();
		
		ProjectDto pDTO = ProjectDto.builder()
				.mid(kssMid)
				.pname("코딩 알파고 프로젝트")
				.description("코딩계의 알파고를 만들어 코딩 시키는 프로젝트")
				.stDate(LocalDateTime.now())
				.edDate(LocalDateTime.now())
				.pstat("대기")
				.prole("책임자")
				.progress(0)
				.build();
		
		ps.register(pDTO);
		
		pDTOList = ps.listByMid(kssMid);
		assertTrue(pDTOList.size() == size + 1);
	}
	
	@Test
	@Transactional
	public void 프로젝트_수정하기_테스트() throws Exception {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		LinkedList<ProjectDto> pDTOList = new LinkedList<ProjectDto>(ps.listByMid(kssMid)) ;
		ProjectDto pDTO1 = pDTOList.poll();
		
		String pname = pDTO1.getPname();
		pDTO1.setPname(pname + "_수정본");
		
		ProjectDto pDTOr = ps.correct(pDTO1);
		assertTrue(pDTOr.getPname().equals(pname + "_수정본"));
	}
	
	@Test
	@Transactional
	public void 프로젝트_연결해제_테스트() {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		List<ProjectDto> pDTOList = ps.listByMid(kssMid);
		Integer size = pDTOList.size();
		ProjectDto pDTO = pDTOList.get(0);
		
		ps.disable(pDTO);
		assertTrue(ps.listByMid(kssMid).size() == size - 1);
	}
	
	@After
	public void clearAll() {
		td.deleteAllPdirData();
		td.deleteAllProjectData();
		td.deleteAllMemberData();
	}
}
