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
	public MemberVO login(MemberVO memberVO) {
		return template.selectOne("member.login", memberVO);
	}

	@Override
	public void register(MemberVO vo) {
		System.out.println(vo);
		template.insert("member.register", vo);
	}

	@Override
	public int idcheck(String id) {
		return template.selectOne("member.idcheck", id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return template.selectOne("member.findMemberById", id);
	}
	
	@Override
	public int getTotalMember(){
		return template.selectOne("member.getMemberCount");
	}
	@Override
	public void updateMember(MemberVO memberVO) {
		template.update("member.updateMember", memberVO);
	}

	@Override
	public int passwordCheck(String password) {
		return template.selectOne("member.passwordCheck", password);
	}
 
	@Override
	public void deleteMember(String id, String password) {
		MemberVO vo=new MemberVO(id,password);
		template.update("member.deleteMember", vo);
	}

	@Override
	public MemberVO forgotId(String name, String tel) {
		MemberVO vo=new MemberVO();
		vo.setName(name);
		vo.setTel(tel);
		return template.selectOne("member.forgotId", vo);
	}

	@Override
	public MemberVO forgotPass(String id,String name, String tel) {
		MemberVO vo=new MemberVO();
		vo.setId(id);
		vo.setName(name);
		vo.setTel(tel);
		return template.selectOne("member.forgotPass", vo);
	}

	@Override
	public void changePass(String id, String password) {
		MemberVO vo=new MemberVO();
		vo.setId(id);
		vo.setPassword(password);
		template.update("member.changePass", vo);
	}	
}
