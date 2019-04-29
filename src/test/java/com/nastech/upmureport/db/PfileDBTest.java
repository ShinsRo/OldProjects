package com.nastech.upmureport.db;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nastech.upmureport.TestData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PfileDBTest {
	
	
	@Autowired
	TestData td;
	
	@Before
	public void setup() {
		
		td.setMemberTestData();
		
		td.setProjectTestData();

		td.setPdirTestData();
		
		td.setPfileTestData();
		
	}
	
	@Test
	public void pfileTest() {
		
		
	}

}
