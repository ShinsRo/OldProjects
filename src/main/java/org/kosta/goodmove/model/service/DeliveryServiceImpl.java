package org.kosta.goodmove.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.DeliveryDAO;
import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryMatchVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	@Resource
	private DeliveryDAO dao;

	@Override
	public int idcheck(String id) {
		return dao.idcheck(id);
	}

	@Override
	public int getTotalDelivery() {
		return 0;
	}

	@Override
	public int passwordCheck(String password) {
		return dao.passwordCheck(password);
	}

	@Override
	public void convertDoneState(String id, String bno, String currState) {
		String state = "";
		if(currState.equals("YES")) state = "NO";
		else state = "YES";
		dao.convertDoneState(id, bno, state);
	}
	@Override
	public List<ApplicationVO> getAllDeliveryList() {
		return dao.getAllDeliveryList();
	}
	@Override
	public Map<String, MemberVO> getDeliveryDetail(String id,String bno){
		Map<String, MemberVO> map = new HashMap<>();
		map.put("giver", dao.selectGiverByBno(bno));
		map.put("reciever", dao.selectRecieverById(id));
		return map;
	}
	@Override
	public List<MemberVO> getNotConfirmedDeliveryList(){
		return dao.getNotConfirmedDeliveryList();
	}
	@Override
	public List<MemberVO> getDeliveryListAndNotConfirmed(){
		return dao.getDeliveryListAndNotConfirmed();
	}
	
	@Override
	public void confirmDelivery(String id){
		dao.confirmDelivery(id);
	}
	@Override
	public void revocationContract(String id) {
		dao.revocationContract(id);
		
	}

	@Override
	public void registerDeliveryMatch(String bno, String aid, String did) {
		dao.registerDeliveryMatch(bno, aid, did);
	}

	@Override
	public int countDelivery_admin() {
		return dao.getTotalDelivery();
	}
	@Override
	public List<DeliveryMatchVO> findDeliveryMatchByDid(String did){
		return dao.findDeliveryMatchByDid(did);
	}

	@Override
	public void coalition(MemberVO mvo) {
		dao.coalition(mvo);
		
	}



}
