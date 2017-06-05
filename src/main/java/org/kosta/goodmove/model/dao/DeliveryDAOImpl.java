package org.kosta.goodmove.model.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryVO;
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
		return 0;	
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

}
