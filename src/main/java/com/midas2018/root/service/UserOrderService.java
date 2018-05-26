package com.midas2018.root.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.midas2018.root.model.UserOrderVO;
import com.midas2018.root.repository.UserOrderRepository;

@Service
public class UserOrderService {

    @Autowired
    private UserOrderRepository userOrderRepository;

    public List<UserOrderVO> getUserOrderList() {
        return userOrderRepository.selectUserOrderList();
    }

    public void insertUserOder(UserOrderVO userOrderVO) throws IOException {
        userOrderRepository.insertUserOder(userOrderVO);

//        ObjectMapper objectMapper = new ObjectMapper();
//
//        userOrderRepository.insertUserOrder(UserOrderVO.builder().content(userOrderRequest.getContent())
//                                                      .status(OrderStatus.WAITING)
//                                                      .totalPrice(userOrderRequest.getTotalPrice())
//                                                      .cafeMenuList(objectMapper.readValue(
//                                                              userOrderRequest.getCafeMenuList(),
//                                                              CafeMenuAndOptionsList.class))
//                                                      .build());
    }
}
