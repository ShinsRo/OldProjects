package org.kosta.goodmove;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kosta.goodmove.model.dao.BoardDAO;
import org.kosta.goodmove.model.dao.CommentDAO;
import org.kosta.goodmove.model.dao.MemberDAO;
import org.kosta.goodmove.model.service.BoardService;
import org.kosta.goodmove.model.service.CommentService;
import org.kosta.goodmove.model.service.MemberService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
	Backend Test JUnit
	
*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/kosta-*.xml"})
public class TestJUnit {
	
	@Resource
	private MemberDAO mDAO;
	@Resource
	private MemberService mServ;
	
	@Resource
	private BoardDAO bDAO;
	@Resource
	private BoardService bServ;
	
	@Resource
	private CommentDAO cDAO;
	@Resource
	private CommentService cServ;
	
	@Test
	public void test(){

	}
}




















