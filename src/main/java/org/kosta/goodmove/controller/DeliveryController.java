package org.kosta.goodmove.controller;

import javax.annotation.Resource;

import org.kosta.goodmove.model.service.DeliveryService;
import org.springframework.stereotype.Controller;

@Controller
public class DeliveryController {

	@Resource
	private DeliveryService service;
	
}
