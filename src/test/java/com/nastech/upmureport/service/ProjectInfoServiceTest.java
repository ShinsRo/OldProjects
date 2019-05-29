package com.nastech.upmureport.service;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.domain.dto.ProjectQueryDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectInfoServiceTest {
	
	@Autowired
	ProjectInfoService pis;
	
	@Test
	public void 프로젝트쿼리_테스트() {
		System.out.println(pis.getProjectInfosByOps(
				ProjectQueryDto.builder()
					.queryOps("01")
					.from(LocalDateTime.now().minusYears(1))
					.to(LocalDateTime.now())
				.build()));
	}
}
