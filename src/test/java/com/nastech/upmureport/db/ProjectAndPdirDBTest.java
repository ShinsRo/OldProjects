package com.nastech.upmureport.db;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.ProjectInfoRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectAndPdirDBTest {
	
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
	ProjectInfoRepository pir;
	
	@Autowired
	TestData td;

//	@Before
	public void setUp() {
		td.deleteAllPdirData();
		td.deleteAllProjectData();
		td.deleteAllMemberData();
		
		td.setMemberTestData();
		
		assertTrue(memberRepository.count() == 4);
		assertTrue(authinfoRepository.count() == 4);
		assertTrue(careerRepository.count() == 4);
		assertTrue(memberSystemRepository.count() == 3);
		
		td.setProjectTestData();
		
		assertTrue(projectRepository.count() == 3);
		assertTrue(memberProjectRepository.count() > 1);
		
		td.setPdirTestData();
		
		assertTrue(pdirRepository.count() > 1);
	}
	
	@Test
	public void 프로젝트_정보_관리자조회_테스트() {
		
		List<Map<String, Object>> groupedProjectInfo 
			= pir.groupedProjectInfoPageByStDateBetween(
				LocalDateTime.now().minusYears(1), 
				LocalDateTime.now());
		assertTrue(groupedProjectInfo.size() > 0);

		Set<String> fields 
			= groupedProjectInfo.get(0).keySet();
		assertTrue(fields.size() > 0);

		fields.stream().forEach(hd -> {
			System.out.printf("%s\t\t", (hd.length() > 3)? hd.substring(0, 3) + "...": hd);
		});
		System.out.printf("\n===========================================================================");
		System.out.printf("===========================================================================\n");
		groupedProjectInfo.stream().forEach(column -> {
			fields.stream().forEach(hd -> {
				String col = "null";
				if (column.get(hd) != null) {
					col = column.get(hd).toString();
					col = (col.length() > 3) ? col.substring(0, 3) + "...": col;
				}
				
				
				System.out.printf("%s\t\t", col);
			});
			System.out.println();
		});
		System.out.println(groupedProjectInfo.size());
		
		List<MemberProject> mps = pir.projectInfoByStDateBetween(
				LocalDateTime.now().minusMonths(12),
				LocalDateTime.now());
		
		System.out.println(mps);
	}
	
//	@After
	public void clearAll() {
		
		td.deleteAllPdirData();
		
		assertTrue(pdirRepository.count() == 0);
		
		td.deleteAllProjectData();
		
		assertTrue(projectRepository.count() == 0);
		assertTrue(memberProjectRepository.count() == 0);
		
		td.deleteAllMemberData();
		
		assertTrue(memberRepository.count() == 0);
		assertTrue(authinfoRepository.count() == 0);
		assertTrue(careerRepository.count() == 0);
		assertTrue(memberSystemRepository.count() == 0);
	}

}
