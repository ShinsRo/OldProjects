package org.kosta.goodmove.model.dao;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO{
	@Resource
	 private SqlSessionTemplate template;
	
 	@Override
 	public MemberVO findByIdMember(String id){
 		return template.selectOne("member.findMemberById", id);
 	}
}
