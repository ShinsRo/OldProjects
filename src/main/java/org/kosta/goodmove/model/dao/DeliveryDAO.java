package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.MemberVO;

public interface DeliveryDAO {

	int idcheck(String id);

	int getTotalDelivery();

	int passwordCheck(String password);

	public void convertDoneState(String id, String bno, String state);
	
	List<ApplicationVO> getAllDeliveryList();
	
	MemberVO selectRecieverById(String id);
	
	MemberVO selectGiverByBno(String bno);

	List<MemberVO> getNotConfirmedDeliveryList();

	void confirmDelivery(String id);
	
	void registerDeliveryMatch(String bno,String aid,String did);

	public void coalition(MemberVO mvo);
}
