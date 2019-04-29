package com.nastech.upmureport.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nastech.upmureport.TestData;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.service.ProjectService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectControllerTest {
	@Mock
	ProjectService ps;
	
	@InjectMocks
	ProjectController pc;
	
	@Autowired
	TestData td;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(pc).build();
	}
	
	@Test
	public void 불러오기() throws Exception {
		List<ProjectDto> pDtos = Arrays.asList(
			ProjectDto.builder().pid("9001").pname("프로젝트 1").build(),
			ProjectDto.builder().pid("9002").pname("프로젝트 2").build()
		);
		Mockito.when(ps.listByMid("1111")).thenReturn(pDtos);
		
		MockHttpServletRequestBuilder builder =
				MockMvcRequestBuilders.get("/api/project/list")
				.param("mid", "1111");
				
		
		this.mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print())
				.andReturn();
	}
}
