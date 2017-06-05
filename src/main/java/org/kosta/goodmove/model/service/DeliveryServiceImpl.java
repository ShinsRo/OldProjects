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
		return dao.login(dvo);
	}

	@Override
	public void register(DeliveryVO dvo) {
		dao.register(dvo);
	}

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
	public void deleteDelivery(DeliveryVO dvo) {
		dao.deleteDelivery(dvo);
	}

	@Override
	public void convertDoneState(String id, String bno, String currState) {
		String state = "";
		if(currState.equals("YES")) state = "NO";
		else state = "YES";
		dao.convertDoneState(id, bno, state);
	}

}
