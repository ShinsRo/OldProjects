package com.nastech.upmureport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.MemCareerDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.service.CareerService;

@RestController
@RequestMapping(value = "/api/career")
public class CareerController {
	
	@Autowired
	CareerService careerService;
	
	@PostMapping(value ="/modify")
	public void careerModifyAPI(@RequestBody MemCareerDto memCareerDto) {
//		System.out.println(mem); 
		System.out.println(memCareerDto.getMem());
		System.out.println(memCareerDto.getNewCar().getDept()+" "+memCareerDto.getNewCar().getPosi());
		
		Member mem = memCareerDto.getMem();
		//System.out.println("컨트롤러변경요청 "+mem+" and \n");
		careerService.careerModify(mem, memCareerDto.getNewCar());
	}
}
