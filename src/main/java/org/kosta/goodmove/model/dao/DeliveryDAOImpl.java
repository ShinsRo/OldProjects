package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryMatchVO;
import org.kosta.goodmove.model.vo.DeliveryVO;
import org.kosta.goodmove.model.vo.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DeliveryDAOImpl implements DeliveryDAO {
	@Resource
	private SqlSessionTemplate template;

	@Override
	public DeliveryVO login(DeliveryVO dvo) {
		return template.selectOne("delivery.login",dvo);
	}

	@Override
	public void register(DeliveryVO dvo) {
		template.insert("delivery.register", dvo);
	}

	@Override
	public int idcheck(String id) {
		return template.selectOne("delivery.idcheck", id);
	}

	@Override
	public int getTotalDelivery() {
		return template.selectOne("delivery.countDelivery");	
	}

	@Override
	public int passwordCheck(String password) {
		return template.selectOne("delivery.passwordCheck", password);
	}

	@Override
	public void deleteDelivery(DeliveryVO dvo) {
		template.delete("delivery.deleteDelivery", dvo);
	}

	@Override
	public void convertDoneState(String id, String bno, String state) {
		HashMap<String, String> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("id", id);
		paramMap.put("state", state);
		template.update("delivery.convertDoneState", paramMap);
	}
	public List<ApplicationVO> getAllDeliveryList() {
		return template.selectList("delivery.getAllDeliveryList");
	}

	@Override
	public MemberVO selectRecieverById(String id) {
		return template.selectOne("delivery.selectRecieverById", id);
	}

	@Override
	public MemberVO selectGiverByBno(String bno) {
		return template.selectOne("delivery.selectGiverByBno", bno);
	}
	@Override
	public List<DeliveryVO> getNotConfirmedDeliveryList(){
		return template.selectList("delivery.getNotConfirmedDeliveryList");
	}
	@Override
	public void confirmDelivery(String id){
		template.update("delivery.confirmDelivery", id);
	}

	@Override
	public void registerDeliveryMatch(String bno, String aid, String did) {
		HashMap<String, String> dmMap = new HashMap<>();
		dmMap.put("bno", bno);
		dmMap.put("aid", aid);
		dmMap.put("did", did);
		template.insert("delivery.registerDeliveryMatch", dmMap);
	}
	@Override
	public List<DeliveryMatchVO> findDeliveryMatchByDid(String did){
		return template.selectList("delivery.findDeliveryMatchByDid", did);
	}
}
