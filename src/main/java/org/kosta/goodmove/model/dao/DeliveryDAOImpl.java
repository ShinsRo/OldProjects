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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(DeliveryVO dvo) {
		// TODO Auto-generated method stub

	}

	@Override
	public int idcheck(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalDelivery() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int passwordCheck(String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteDelivery(String id, String password) {
		// TODO Auto-generated method stub

	}

}
