package org.sejonghacker.botum;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Resource
	private AbstractDAO<TestVO> testDAO;
	
	public TestVO getOne(int id) {
		TestVO params = new TestVO(id, "null");
		return testDAO.select(params);
	}
}
