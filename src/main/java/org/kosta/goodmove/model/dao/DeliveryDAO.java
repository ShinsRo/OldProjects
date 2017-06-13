package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryMatchVO;
import org.kosta.goodmove.model.vo.DeliveryVO;
import org.kosta.goodmove.model.vo.MemberVO;

public interface DeliveryDAO {

	public DeliveryVO login(DeliveryVO dvo);

	void register(DeliveryVO dvo);

	int idcheck(String id);

	int getTotalDelivery();

	int passwordCheck(String password);

	void deleteDelivery(DeliveryVO dvo);

	public void convertDoneState(String id, String bno, String state);
	
	List<ApplicationVO> getAllDeliveryList();
	
	MemberVO selectRecieverById(String id);
	
	MemberVO selectGiverByBno(String bno);

	List<DeliveryVO> getNotConfirmedDeliveryList();

	void confirmDelivery(String id);
	
	void registerDeliveryMatch(String bno,String aid,String did);

	List<DeliveryMatchVO> findDeliveryMatchByDid(String did);
}
