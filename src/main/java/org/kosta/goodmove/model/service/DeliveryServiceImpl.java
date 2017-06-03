package org.kosta.goodmove.model.service;

import javax.annotation.Resource;

import org.kosta.goodmove.model.dao.DeliveryDAO;
import org.kosta.goodmove.model.vo.DeliveryVO;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	@Resource
	private DeliveryDAO dao;

	@Override
	public DeliveryVO login(DeliveryVO dvo) {
		return null;
	}

	@Override
	public void register(DeliveryVO dvo) {

	}

	@Override
	public int idcheck(String id) {
		return 0;
	}

	@Override
	public int getTotalDelivery() {
		return 0;
	}

	@Override
	public int passwordCheck(String password) {
		return 0;
	}

	@Override
	public void deleteDelivery(String id, String password) {
	}

}
