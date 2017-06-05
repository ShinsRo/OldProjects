package org.kosta.goodmove.model.dao;

import java.util.List;

import org.kosta.goodmove.model.vo.ApplicationVO;
import org.kosta.goodmove.model.vo.DeliveryVO;

public interface DeliveryDAO {

	public DeliveryVO login(DeliveryVO dvo);

	void register(DeliveryVO dvo);

	int idcheck(String id);

	int getTotalDelivery();

	int passwordCheck(String password);

	void deleteDelivery(DeliveryVO dvo);

	public void convertDoneState(String id, String bno, String state);
	
	List<ApplicationVO> getAllDeliveryList();
}
