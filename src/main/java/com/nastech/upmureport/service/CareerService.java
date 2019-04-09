package com.nastech.upmureport.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.dto.MemberDto;
import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.repository.CareerRepository;

@Service
public class CareerService {
	@Autowired
	CareerRepository careerRepository;
	public void careerModify(Member mem, Career newCar) {
		List<Career> cList =careerRepository.findAll();
		for (Career career : cList) {
			if(career.getActive() == true) {
				career.setActive(false);
				career.setEndDate(LocalDate.now());
				careerRepository.save(career);
				System.out.println("기존 캐리어 내용");
				System.out.println(career.toString());
				break;
			}
		}
		
		newCar.setStartDate(LocalDate.now());
		Member temp = Member.builder().mid(mem.getMid()).build();
		newCar.setMember(temp);
		newCar.setActive(true);
		System.out.println("새로운 캐리어 내용");
		System.out.println(newCar);
		careerRepository.save(newCar);
	}
}
