package org.kosta.goodmove.model.dao;

import javax.annotation.Resource;

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

}
