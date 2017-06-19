package org.kosta.goodmove.model.dao;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.Authority;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.PagingBean;
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
	/*
	 * Security에서 인증시 사용
	 */
	@Override					     
	public List<Authority> selectAuthorityById(String id) {
		return template.selectList("member.selectAuthorityById", id);
	}
	
	/*
	 * Security를 위해 권한부여를 위해  사용
	 */
	@Override	
	public void registerRole(Authority authority){
		template.insert("member.registerRole",authority);
	}
	
	/** 
	 * 관리자 회원관리
	 */
	@Override
	public int getTotalMemberCount(){
		return template.selectOne("member.getTotalMemberCount");
	}
	@Override
	public List<MemberVO> getMemberList_admin(PagingBean pagingBean){
		return template.selectList("member.getMemberList_admin",pagingBean);
	}
	@Override
	public void deleteMember_admin(String id){
		template.update("member.deleteMember_admin",id);
	}
	@Override
	public void restoreMember_admin(String id){
		template.update("member.restoreMember_admin",id);
	}
}
