package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.dto.ProjectDTO;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.support.Prole;
import com.nastech.upmureport.domain.entity.support.Pstat;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class ProjectService {
	@Autowired
	private MemberRepository mr;
	
	@Autowired
	private MemberProjectRepository mpr;
	
	@Autowired
	private PdirRepository pdr;
	
	/**
	 * @param pDTO 프로젝트 등록할 정보
	 * 넘겨받은 프로젝트 정보에 의거해 프로젝트를 등록합니다. 이 때 요청한 유저는 자동적으로 프로젝트에 소속합니다.
	 * @return 등록자와 프로젝트 연결 객체, 해당 유저가 존재하지 않을 경우 익셉션
	 */
	@Transactional
	public MemberProject register(ProjectDTO pDTO) throws NoSuchElementException {
		Member member = null;

		Project project = Project.builder()
				.pName(pDTO.getPName())
				.description(pDTO.getDescription())
				.stDate(pDTO.getStDate())
				.edDate(pDTO.getEdDate())
				.build();
		
		BigInteger BigIntegerPid = null;
		String pid = pDTO.getPid();
		if (pid != null && pid.equals("")) 
			BigIntegerPid = Utils.StrToBigInt(pid);
		
		project.setPid(BigIntegerPid);
		
		BigInteger mid = Utils.StrToBigInt(pDTO.getMid());
		try {
			member = mr.findById(mid).get();
		} catch (NoSuchElementException nsee) {
			throw new NoSuchElementException();
		}
		
		Pstat ps = null;
		try {
			ps = Pstat.valueOf(pDTO.getPStat());
		} catch (IllegalArgumentException iae) {
			ps = Pstat.대기;
		}
		
		MemberProject mp = MemberProject.builder()
				.member(member)
				.project(project)
				.pStat(ps)
				.pRole(Prole.책임자)
				.progress(pDTO.getProgess())
				.build();
		
		return mpr.save(mp);
	}
	

	/**
	 * 프로젝트 DTO와 UserProjectDTO로부터 기존 내용과 다른 사항을 수정합니다.
	 * @param pDTO
	 * @return 변경한 UserProject 오브젝트
	 */
	@Transactional
	public MemberProject update(ProjectDTO pDTO) {
		Project p = Project.builder()
				.pid(Utils.StrToBigInt(pDTO.getPid()))
				.build();
		
		Member m = Member.builder()
				.mid(Utils.StrToBigInt(pDTO.getMid()))
				.build();
		
		//원본 유저, 프로젝트
		MemberProject mp = mpr.findOneByMemberAndProject(m, p);
		
		Pstat ps;
		try {
			ps = Pstat.valueOf(pDTO.getPStat());
		} catch (IllegalArgumentException iae) {
			ps = mp.getPStat();
		}
		mp.setPStat(ps);
		
		Prole pr;
		try {
			pr = Prole.valueOf(pDTO.getPRole());
		} catch (IllegalArgumentException iae) {
			pr = mp.getPRole();
		}
		mp.setPRole(pr);
		
		mpr.save(mp);
		return mp;
	}
	
	public List<ProjectDTO> listByMid(String mid) {
		Member m = Member.builder()
				.mid(Utils.StrToBigInt(mid))
				.build();
		
		List<MemberProject> mpList = mpr.findAllByMember(m);
		
		List<ProjectDTO> pDTOs = new ArrayList<ProjectDTO>(); 
		for (MemberProject mp : mpList) {
			ProjectDTO pDTO = new ProjectDTO(mp);
			pDTOs.add(pDTO);
		}
		
		return pDTOs;
	}
	
	@Transactional
	public void disableMemberProject(ProjectDTO pDTO) throws NoSuchElementException {
		Member m = Member.builder()
				.mid(Utils.StrToBigInt(pDTO.getMid()))
				.build();
		
		Project p = Project.builder()
				.pid(Utils.StrToBigInt(pDTO.getPid()))
				.build();
		
		MemberProject mp = mpr.findOneByMemberAndProject(m, p);
		mp.setDFlag(true);
		
		mpr.disable(mp);
	}
	
//	/**
//	 * 
//	 * @param userId
//	 * @return 유저 아이디에 연관한 모든 유저-프로젝트 리스트
//	 */
//	public List<MemberProject> findAllUserProjectByUserId(String userId) {
//		List<MemberProject> userProjs = new ArrayList<MemberProject>();
//		
//		userProjs = userProjectRepository.findAllByUser(userId);
//		return userProjs;
//	}
//	
	/**
	 * 
	 * @param projId
	 * @return
	 */
//	public List<MemberProject> findAllUserProjectByProjId(Integer projId) {
//		List<MemberProject> userProjs = new ArrayList<MemberProject>();
//		
//		userProjs = userProjectRepository.findAllByProject(
//				Project.builder().projId(projId).build());
//		return userProjs;
//	}
//
//	
//	public List<DirDto> findDirsByProjId(String projId) {
//		List<Dir> dirs = pdr.findAllByParentProjId(Integer.parseInt(projId));
//		List<DirDto> dirDtos = new ArrayList<DirDto>();
//		for (Dir dir : dirs) {
//			DirDto temp = DirDto.builder()
//					.projId(projId)
//					.userId(dir.getUser().getUserId())
//					.userName(dir.getUser().getUserName())
//					.dirId(""+dir.getDirId())
//					.dirName(dir.getDirName())
//					.build();
//			
//			if (dir.getParentDir() == null) {
//				temp.setParentDirId("root");				
//			} else {
//				temp.setParentDirId(""+dir.getParentDir().getDirId());
//			}
//			dirDtos.add(temp);
//		}
//		return dirDtos;
//	}
//
//	public Dir registerDir(DirDto dirDto) {
//		String userId = dirDto.getUserId();
//		Integer projId = Integer.parseInt(dirDto.getProjId());
//		
//		User user = mr.findById(userId).get();
//		Project proj = pr.findById(projId).get();
//		
//		String parentDirId = dirDto.getParentDirId();
//		Dir parentDir = null;
//		if (parentDirId != null) parentDir = pdr.getOne(Integer.valueOf(parentDirId));
//		
//		Dir dir = Dir.builder()
//				.dirName(dirDto.getDirName())
//				.user(user)
//				.project(proj)
//				.build();
//		
//		if(parentDir != null ) dir.setParentDir(parentDir);
//		return pdr.save(dir);
//	}
//
//
//	public void disableDir(DirDto dto) {
//		Dir target = pdr.getOne(Integer.valueOf(dto.getDirId()));
//		target.setDeleteFlag(true);
//		pdr.save(target);
//	}
}