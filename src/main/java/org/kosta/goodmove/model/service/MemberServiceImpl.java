package org.kosta.goodmove.model.service;

import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.MemberDAO;
import org.kosta.goodmove.model.vo.Authority;
import org.kosta.goodmove.model.vo.MemberListVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.kosta.goodmove.model.vo.PagingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
	@Resource
	private MemberDAO memberDAO;
	@Resource
	private BCryptPasswordEncoder passwordEncoder; 
	
	
	@Override
	public MemberVO login(MemberVO memberVO) {
		return memberDAO.login(memberVO);
	}
	@Transactional
	@Override
	public void register(MemberVO vo) {
		// 비밀번호를 bcrypt 알고리듬으로 암호화하여 DB에 저장한다
        String encodedPwd = passwordEncoder.encode(vo.getPassword());
        vo.setPassword(encodedPwd);
		memberDAO.register(vo);
        //회원 가입시 반드시 권한이 등록되도록 트랜잭션처리를 한다  
		Authority authority=new Authority(vo.getId(),"ROLE_MEMBER");
		memberDAO.registerRole(authority);
	}

	@Override
	public int idcheck(String id) {
		return memberDAO.idcheck(id);
	}
	
	@Override
	public List<Authority> selectAuthorityById(String id) {		
		return memberDAO.selectAuthorityById(id);
	}
	

	@Override
	public MemberVO findMemberById(String id) {
		return memberDAO.findMemberById(id);
	}

	@Override
	public int getMemberCount() {
		return memberDAO.getTotalMember();
	}
	@Override
	public void updateMember(MemberVO memberVO) {
		memberDAO.updateMember(memberVO);
	}

	@Override
	public int passwordCheck(String password) {
		return memberDAO.passwordCheck(password);
	}

	@Override
	public void deleteMember(String id, String password) {
		memberDAO.deleteMember(id, password);

	}

	@Override
	public MemberVO forgotId(String name, String tel) {
		return memberDAO.forgotId(name, tel);
	}

	@Override
	public MemberVO forgotPass(String id,String name, String tel) {
		return memberDAO.forgotPass(id,name, tel);
	}

	@Override
	public void changePass(String id, String password) {
		memberDAO.changePass(id, password);
	}
	
	/**
	 * 관리자 회원관리
	 */
	@Override
	public int getTotalMemberCount(){
		return memberDAO.getTotalMemberCount();
	}
	
	@Override
	public MemberListVO getMemberList_admin(int pageNo){
		int totalCount = memberDAO.getTotalMemberCount();
		PagingBean pagingBean = new PagingBean(totalCount,pageNo);
		return new MemberListVO(memberDAO.getMemberList_admin(pagingBean),pagingBean);
	}
	
	@Override
	public void deleteMember_admin(String id){
		memberDAO.deleteMember_admin(id);
	}
	@Override
	public void restoreMember_admin(String id){
		memberDAO.restoreMember_admin(id);
	}
}
