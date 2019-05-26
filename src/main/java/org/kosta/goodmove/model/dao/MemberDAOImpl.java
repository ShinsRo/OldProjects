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
	
	/*
	 * 로그인 관련
	 * @see org.kosta.goodmove.model.dao.MemberDAO#login(org.kosta.goodmove.model.vo.MemberVO)
	 */
	@Override
	public MemberVO login(MemberVO memberVO) {
		return template.selectOne("member.login", memberVO);
	}
	/*
	 * 회원가입관련
	 * @see org.kosta.goodmove.model.dao.MemberDAO#register(org.kosta.goodmove.model.vo.MemberVO)
	 */
	@Override
	public void register(MemberVO vo) {
		template.insert("member.register", vo);
	}
	/*
	 * 아이디 중복확인관련 부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#idcheck(java.lang.String)
	 */
	@Override
	public int idcheck(String id) {
		return template.selectOne("member.idcheck", id);
	}
	/*
	 * 아이디를 통해 멤버값을 가져오는 부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#findMemberById(java.lang.String)
	 */
	@Override
	public MemberVO findMemberById(String id) {
		return template.selectOne("member.findMemberById", id);
	}
	/*
	 * 멤버의 총 인원수를 가져오기위한 메서드
	 * @see org.kosta.goodmove.model.dao.MemberDAO#getTotalMember()
	 */
	@Override
	public int getTotalMember(){
		return template.selectOne("member.getMemberCount");
	}
	/*
	 * 회원수정에 관련 부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#updateMember(org.kosta.goodmove.model.vo.MemberVO)
	 */
	@Override
	public void updateMember(MemberVO memberVO) {
		template.update("member.updateMember", memberVO);
	}
	/*
	 * 회원탈퇴 관련 부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#deleteMember(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteMember(String id, String password) {
		MemberVO vo=new MemberVO(id,password);
		template.update("member.deleteMember", vo);
	}
	/*
	 * 아이디 찾기 관련부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#forgotId(java.lang.String, java.lang.String)
	 */
	@Override
	public MemberVO forgotId(String name, String tel) {
		MemberVO vo=new MemberVO();
		vo.setName(name);
		vo.setTel(tel);
		return template.selectOne("member.forgotId", vo);
	}
	/*
	 * 비밀번호 찾기에 관련 부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#forgotPass(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public MemberVO forgotPass(String id,String name, String tel) {
		MemberVO vo=new MemberVO();
		vo.setId(id);
		vo.setName(name);
		vo.setTel(tel);
		return template.selectOne("member.forgotPass", vo);
	}
	/*
	 * 비밀번호 찾기를통해 비밀번호 변경에 관한 부분
	 * @see org.kosta.goodmove.model.dao.MemberDAO#changePass(java.lang.String, java.lang.String)
	 */
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
