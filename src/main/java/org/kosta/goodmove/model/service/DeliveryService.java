package org.kosta.goodmove.model.service;

import java.util.List;
import java.util.Map;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryMatchVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeliveryService {

	int idcheck(String id);

	int getTotalDelivery();

	int passwordCheck(String password);

	public void convertDoneState(String id, String bno, String currState);

	List<ApplicationVO> getAllDeliveryList();

	Map<String, MemberVO> getDeliveryDetail(String id, String bno);

	List<MemberVO> getNotConfirmedDeliveryList();

	void confirmDelivery(String id);
	
	void registerDeliveryMatch(String bno, String aid, String did);

	public int countDelivery_admin();

	public void coalition(MemberVO mvo);
	List<DeliveryMatchVO> findDeliveryMatchByDid(String did);

	List<MemberVO> getDeliveryListAndNotConfirmed();

	void revocationContract(String id);
}
