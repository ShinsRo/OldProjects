package com.nastech.upmureport.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.common.support.Utils;
import com.nastech.upmureport.feature.project.domain.dto.PdirDto;
import com.nastech.upmureport.feature.project.domain.dto.ProjectDto;
import com.nastech.upmureport.feature.project.domain.entity.Pdir;
import com.nastech.upmureport.feature.project.domain.entity.Project;
import com.nastech.upmureport.feature.project.repo.MemberProjectRepository;
import com.nastech.upmureport.feature.project.repo.PdirRepository;
import com.nastech.upmureport.feature.project.repo.ProjectRepository;
import com.nastech.upmureport.feature.project.service.PdirService;
import com.nastech.upmureport.feature.project.service.ProjectService;
import com.nastech.upmureport.feature.user.domain.entity.Member;
import com.nastech.upmureport.feature.user.repo.AuthInfoRepository;
import com.nastech.upmureport.feature.user.repo.CareerRepository;
import com.nastech.upmureport.feature.user.repo.MemberRepository;
import com.nastech.upmureport.feature.user.repo.MemberSystemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdirServiceTest {
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
	
	@Autowired
	PdirService ds;
	
	@Before
	public void setUp() {
		td.setMemberTestData();
		td.setProjectTestData();
		td.setPdirTestData();
	}
	
	@Test
	public void 디렉토리목록_가져오기_테스트 () {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		List<ProjectDto> pDtos = ps.listByMid(kssMid);
		List<PdirDto> dDtos = ds.listByPid(pDtos.get(0).getPid());
		assertTrue(dDtos.size() == 4);
	}
	
	@Test
	public void 디렉토리_추가하기_테스트 () {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		ProjectDto pDto = ps.listByMid(kssMid).get(0);
		
		Project project = Project.builder()
				.pid(Utils.StrToBigInt(pDto.getPid()))
				.build();
		
		Pdir root = pdirRepository.findAllByProjectAndDflagFalse(project).get(0);
		
		PdirDto dto = PdirDto.builder()
				.dname("추가한 디렉토리")
				.parentDid(root.getDid().toString())
				.mid(pDto.getMid())
				.mname("김승신")
				.pid(pDto.getPid())
				.build();
		
		PdirDto dir = ds.register(dto);
		assertTrue(dir != null);
		
		Pdir regied = pdirRepository.findById(Utils.StrToBigInt(dir.getDid())).get();
		assertTrue(regied.getMember().getName() != null);
	}
	
	@Test
	public void 디렉토리_수정하기_테스트 () {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		ProjectDto pDto = ps.listByMid(kssMid).get(0);
		List<PdirDto> dirDtos = ds.listByPid(pDto.getPid());
		PdirDto target = dirDtos.get(0);
		String originDname = target.getDname();
		
		target.setDname(originDname + "_수정본");
		
		ds.correct(target, "수정");
		
		Pdir after = pdirRepository.findById(Utils.StrToBigInt(target.getDid())).get();
		assertTrue(after.getDname().equals(originDname + "_수정본"));
	}
	
	@Test
	public void 디렉토리_연결해제_테스트 () {
		Member kss = memberRepository.findOneByEidAndDflagFalse("1111");
		String kssMid = kss.getMid().toString();
		
		ProjectDto pDto = ps.listByMid(kssMid).get(0);
		
		List<PdirDto> dirDtos = ds.listByPid(pDto.getPid());
		Integer size = dirDtos.size();
		
		ds.disable(dirDtos.get(0));
		
		dirDtos = ds.listByPid(pDto.getPid());
		assertTrue(dirDtos.size() == size - 1);
	}
	
	@After
	public void clearAll() {
		td.deleteAllPdirData();
		td.deleteAllProjectData();
		td.deleteAllMemberData();
	}
}
