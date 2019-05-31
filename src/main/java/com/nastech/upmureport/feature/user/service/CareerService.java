package com.nastech.upmureport.feature.user.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nastech.upmureport.feature.user.domain.dto.DeptPosiDto;
import com.nastech.upmureport.feature.user.domain.entity.Career;
import com.nastech.upmureport.feature.user.domain.entity.Dept;
import com.nastech.upmureport.feature.user.domain.entity.Member;
import com.nastech.upmureport.feature.user.domain.entity.Posi;
import com.nastech.upmureport.feature.user.repo.CareerRepository;
import com.nastech.upmureport.feature.user.repo.DeptRepository;
import com.nastech.upmureport.feature.user.repo.MemberRepository;
import com.nastech.upmureport.feature.user.repo.PosiRepository;

@Service
public class CareerService {
	@Autowired
	CareerRepository careerRepository;
	@Autowired
	MemberRepository memberRepo;
	@Autowired
	DeptRepository deptRepository;
	@Autowired 
	PosiRepository posiRepository;
	
	
	
	/**
	 * @author 마규석 2019.05.22
	 * 
	 * @return 현재 부서와 직책들의 리스트
	 */
	public DeptPosiDto getDeptPosiList() {
		List<Dept> deptList= deptRepository.findAll();
		List<Posi> posiList= posiRepository.findAll();
		DeptPosiDto deptPosiDto = DeptPosiDto.builder()
				.deptList(deptList).posiList(posiList)
				.build();
		return deptPosiDto;
	}
	
	/**
	 * 사원의 커리어를 끝냄 
	 * @author 마규석 2019.05.22
	 * @param mem 해당 사원
	 */
	public void careerEnd(Member mem) {
		List<Career> cList =careerRepository.findAllByMember(mem);
		for (Career career : cList) {   //사원들의 모든 커리어를 순회한다
			if(career.getActive() == true) {   //현재 활성화 된 현재 커리어를
				career.setActive(false);         // 끝내고
				career.setEndDate(LocalDate.now());   ///끝난 날짜를 셋팅한다
				careerRepository.save(career);
				System.out.println("기존 캐리어 내용");
				System.out.println(career.toString());
				break;
			}
		}
	}
	
	/**
	 * @author 마규석
	 * @param mem 해당 되는 사원
	 * @return 해당 되는 사원의 현재 커리어 (부서,직책)
	 */
	public Career currentCareer(Member mem) {
		List<Career> cList =careerRepository.findAllByMember(mem);
		for (Career career : cList) {
			if(career.getActive() == true) { //현재 활성화 된 즉 지금의 커리어 반환
				return career;
			}
		}
		return null;
	}
	
	/**
	 * @author 마규석
	 * @param mem    해당 되는 사원
	 * @param newCar 변경 될 커리어 (부서,직책)
	 * @return  변경 후 커리어
	 */
	
	public Career careerModify(Member mem, Career newCar) {

		System.out.println("변경요청 "+mem+" and "+newCar);
		careerEnd(mem);
		newCar.setStartDate(LocalDate.now());
		//Member temp = Member.builder().mid(mem.getMid()).build();
		Member temp = memberRepo.findOneByMid(mem.getMid());
		newCar.setMember(temp);
		newCar.setActive(true);
		careerRepository.save(newCar);
		return newCar;
	}
	/**
	 * @author 마규석
	 * @param mem 		사원
	 * @param career	등록 될 커리어 (부서, 직책)
	 * @return			등록 된 커리어
	 */
	public Career careerRegister(Member mem,Career career) {
		/* 새로운 커리어 등록 */
    	Career c1 = Career.builder().active(true).dept(career.getDept())
    			.posi(career.getPosi())
    			.member(mem)
    			.startDate(LocalDate.now())
    			.build();
    	Career savedCareer = careerRepository.save(c1);
    	return savedCareer;
	}
	
}
