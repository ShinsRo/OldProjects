package com.nastech.upmureport.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nastech.upmureport.domain.dto.DeptPosiDto;
import com.nastech.upmureport.domain.dto.MemCareerDto;
import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Dept;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.Posi;
import com.nastech.upmureport.domain.repository.DeptRepository;
import com.nastech.upmureport.domain.repository.PosiRepository;
import com.nastech.upmureport.service.CareerService;

@RestController
@RequestMapping(value = "/api/career")
public class CareerController {
	
	@Autowired
	CareerService careerService;
	@Autowired
	DeptRepository deptRepository;
	@Autowired 
	PosiRepository posiRepository;
	
	@PostMapping(value ="/modify")
	public Career careerModifyAPI(@RequestBody MemCareerDto memCareerDto) {
//		System.out.println(mem); 
		System.out.println(memCareerDto.getMem());
		System.out.println(memCareerDto.getNewCar().getDept()+" "+memCareerDto.getNewCar().getPosi());
		
		Member mem = memCareerDto.getMem();
		//System.out.println("컨트롤러변경요청 "+mem+" and \n");
		Career newCar=careerService.careerModify(mem, memCareerDto.getNewCar());
		return newCar;
	}
	@PostMapping(value="/getdeptposi")
	public DeptPosiDto getDeptPosiAPI() {
		List<Dept> deptList= deptRepository.findAll();
		List<Posi> posiList= posiRepository.findAll();
		DeptPosiDto deptPosiDto = DeptPosiDto.builder()
				.deptList(deptList).posiList(posiList)
				.build();
		System.out.println("dept posi 리스트들");
		System.out.println(deptPosiDto);
		
		return deptPosiDto;
	}
	@PostMapping(value="/adddept")
	public Dept addDeptAPI(@RequestBody Dept dept) {
		System.out.println("adddept"+dept);
		Dept addDept=deptRepository.findByDeptName(dept.getDeptName());
		if(addDept != null) {
			return null;
		}
		deptRepository.save(dept);
		return dept;
	}
	@PostMapping(value="/deldept")
	public Dept delDeptAPI(@RequestBody Dept dept) {
		Dept delDept=deptRepository.findByDeptName(dept.getDeptName());
		if(delDept != null) {
			deptRepository.delete(delDept);
			System.out.println("부서삭제:"+delDept);
			return delDept;
		}
		else {
			System.out.println("그런 부서 없다.");
			return null;
		}
	}
	
	@PostMapping(value="/addposi")
	public Posi addPosiAPI(@RequestBody Posi posi) {
		Posi addPosi = posiRepository.findByPosiName(posi.getPosiName());
		if(addPosi != null) {
			return null;
		}
		System.out.println("adddept"+posi);
		posiRepository.save(posi);
		return posi;
	}
	
	@PostMapping(value="/delposi")
	public Posi delPosiAPI(@RequestBody Posi posi) {
		Posi delPosi=posiRepository.findByPosiName(posi.getPosiName());
		if(delPosi != null) {
			posiRepository.delete(delPosi);
			System.out.println("직책 삭제:"+delPosi);
			return delPosi;
		}
		else {
			System.out.println("그런 직책 이미 없다.");
			return null;
		}
	}
}
