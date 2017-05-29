package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.MemberDAO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	@Resource
	private MemberDAO memberDAO;

	@Override
	public MemberVO login(MemberVO memberVO) {
		return memberDAO.login(memberVO);
	}

	@Override
	public void register(MemberVO vo) {
		memberDAO.register(vo);
	}

	@Override
	public int idcheck(String id) {
		return memberDAO.idcheck(id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return memberDAO.findMemberById(id);
	}
	@Override
	public void updateMember(MemberVO memberVO) {
		memberDAO.updateMember(memberVO);
	}

	@Override
	public String passwordCheck(String password) {
		return memberDAO.passwordCheck(password);
	}

	@Override
	public void deleteMember(String id, String password) {
		memberDAO.deleteMember(id, password);
		
	}
}
