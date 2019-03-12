package com.nastech.upmureport.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nastech.upmureport.config.PersistenceJPAConfig;
import com.nastech.upmureport.config.WebConfig;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.repository.ProjectRepository;

import lombok.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, PersistenceJPAConfig.class}, loader=AnnotationConfigWebContextLoader.class)
public class ProjectDbTest {
	
	//테스팅용 dto
	@Data
	class TestDTO {
		private Long id;
		private String name;
	}
	
	@Autowired
	ProjectRepository projectRepository;

	/**
	 * 한글 입력 테스트
	 */
	@Test
	public void 한글입력테스트() {
		Project target = Project.builder()
				.projName("한글1")
				.projStatCode(ProjStat.대기)
				.build();
		
		projectRepository.save(target);
		
		//한글 프로젝트 명 테스트
		assertThat(
				projectRepository.findAll().get(0).getProjName(), 
				is(target.getProjName())
		);
	}
	
	/**
	 * CRUD 테스트
	 */
	@Test
	public void CRUD테스트() {
		List<Project> addList = new ArrayList<Project>();
		
		for(int ii = 0; ii < 50; ii++) {
			addList.add(Project.builder()
					.projName("test" + ii)
					.projProgress(100)
					.projDesc("설명")
					.projStartDate(LocalDateTime.now())
					.build()
			);
			
		}
		//생성, 조회
		projectRepository.saveAll(addList);
//		assertThat(projectRepository.count(), is(Long.valueOf(addList.size() - 1)));
		
		//수정
		Project targetToUpdate = projectRepository.findAll().get(20);
		targetToUpdate.setProjName("changed");
		projectRepository.save(targetToUpdate);

		Project updated = projectRepository.findById(targetToUpdate.getProjId()).get();
		assertThat(targetToUpdate.getProjId(), is(updated.getProjId()));
		assertThat(updated.getProjName(), is("changed"));
		
		//삭제
		projectRepository.deleteById(updated.getProjId());
		Optional<Project> deleted = projectRepository.findById(updated.getProjId());
		assertFalse(deleted.isPresent());
	}
}
