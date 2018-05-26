package com.midas2018.root.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midas2018.root.model.ResultContainer;
import com.midas2018.root.model.UserOrderVO;
import com.midas2018.root.service.UserOrderService;
import com.midas2018.root.support.Constant;

@RestController
@RequestMapping(Constant.USER_ORDER_API_URI)
public class UserOrderControlloer {

    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("/getUserOrderList")
    public ResultContainer<List<UserOrderVO>> getUserOrderList() {
        return new ResultContainer<>(userOrderService.getUserOrderList());
    }

    @PostMapping("insertUserOrder")
    public void insertUserOder(@RequestBody UserOrderVO userOrderVO) throws IOException {
        userOrderService.insertUserOder(userOrderVO);
    }


}
