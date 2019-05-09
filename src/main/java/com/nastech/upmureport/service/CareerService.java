package com.nastech.upmureport.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;

@Service
public class CareerService {
	@Autowired
	CareerRepository careerRepository;
	@Autowired
	MemberRepository memberRepo;
	public void careerEnd(Member mem) {
		List<Career> cList =careerRepository.findAllByMember(mem);
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
	}
	public Career currentCareer(Member mem) {
		List<Career> cList =careerRepository.findAllByMember(mem);
		for (Career career : cList) {
			if(career.getActive() == true) {
				return career;
			}
		}
		return null;
	}
	
	
	
	public Career careerModify(Member mem, Career newCar) {
//		List<Career> cList =careerRepository.findAllByMember(mem);
//		for (Career career : cList) {
//			if(career.getActive() == true) {
//				career.setActive(false);
//				career.setEndDate(LocalDate.now());
//				careerRepository.save(career);
//				System.out.println("기존 캐리어 내용");
//				System.out.println(career.toString());
//				break;
//			}
//		}
		System.out.println("변경요청 "+mem+" and "+newCar);
		careerEnd(mem);
		newCar.setStartDate(LocalDate.now());
		//Member temp = Member.builder().mid(mem.getMid()).build();
		Member temp = memberRepo.findOneByMid(mem.getMid());
		newCar.setMember(temp);
		newCar.setActive(true);
		System.out.println("새로운 캐리어 내용");
		System.out.println(newCar.getDept());
		System.out.println(newCar.getPosi());
		System.out.println(newCar.getActive());
		System.out.println(newCar.getStartDate());
		careerRepository.save(newCar);
		return newCar;
	}
	
}
