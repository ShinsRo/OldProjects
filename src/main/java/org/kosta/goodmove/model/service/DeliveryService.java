package org.kosta.goodmove.model.service;

import java.util.List;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryVO;

public interface DeliveryService {

	public DeliveryVO login(DeliveryVO dvo);

	void register(DeliveryVO dvo);

	int idcheck(String id);

	int getTotalDelivery();

	int passwordCheck(String password);

	void deleteDelivery(DeliveryVO dvo);

	public void convertDoneState(String id, String bno, String currState);

	List<ApplicationVO> getAllDeliveryList();
}
