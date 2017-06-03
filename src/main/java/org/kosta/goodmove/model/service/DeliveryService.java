package org.kosta.goodmove.model.service;

import org.kosta.goodmove.model.vo.DeliveryVO;

public interface DeliveryService {

	public DeliveryVO login(DeliveryVO dvo);

	void register(DeliveryVO dvo);

	int idcheck(String id);

	int getTotalDelivery();

	int passwordCheck(String password);

	void deleteDelivery(DeliveryVO dvo);

}
