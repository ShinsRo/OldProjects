package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.config.MessageSessionContainer;
import com.nastech.upmureport.domain.dto.CollaboratorDto;
import com.nastech.upmureport.domain.dto.PdirDto;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.support.Prole;
import com.nastech.upmureport.domain.entity.support.Pstat;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.support.Utils;

@Service
public class ProjectService {
	private static final Log Logger = LogFactory.getLog(ProjectService.class);
	@Autowired
	private MemberRepository mr;
	@Autowired
	private MemberProjectRepository mpr;
	@Autowired
	private PdirRepository dr;
	@Autowired
	private PdirService ds;
	@Autowired
	private ProjectNotificationService pns;
	
	/**
	 * 멤버 아이디에 따라 ProjectDto 리스트를 반환합니다.
	 * @param mid
	 * @return pDTOs
	 */
	public List<ProjectDto> listByMid(String mid) {
		Member m = Member.builder()
				.mid(Long.valueOf(mid))
				.build();
		
		List<MemberProject> mpList = mpr.findAllByMemberAndDflagFalse(m);
		
		List<ProjectDto> pDTOs = new ArrayList<ProjectDto>();
		for (MemberProject mp : mpList) {
			ProjectDto pDTO = new ProjectDto(mp);
			List<PdirDto> dirs = ds.listByPid(pDTO.getPid());
			List<MemberProject> mpListByProject = mpr.findAllByProjectAndDflagFalse(mp.getProject());
			
			List<CollaboratorDto> collaborators = new ArrayList<>();
			
			mpListByProject.forEach((mpByProject -> {
				String collabMid = mpByProject.getMember().getMid().toString();
				String collabName = mpByProject.getMember().getName();
				String collabProle = mpByProject.getProle().toString();
				
				collaborators.add(CollaboratorDto.builder()
						.mid(collabMid)
						.name(collabName)
						.prole(collabProle)
						.build()
				);
				
			}));
			pDTO.setDirs(dirs);
			pDTO.setCollaborators(collaborators);
			pDTOs.add(pDTO);
		}
		return pDTOs;
	}
	
	/**
	 * 넘겨받은 프로젝트 정보에 의거해 프로젝트를 등록합니다. 이 때 요청한 유저는 프로젝트에 책임자로 소속합니다.
	 * @param pDto 프로젝트 등록할 정보
	 * @return 등록자와 프로젝트 연결 객체, 해당 유저가 존재하지 않을 경우 익셉션
	 */
	@Transactional
	public ProjectDto register(ProjectDto pDto) {
		Member member = null;

		Project project = Project.builder()
				.pname(pDto.getPname())
				.description(pDto.getDescription())
				.stDate(pDto.getStDate())
				.edDate(pDto.getEdDate())
				.build();

		BigInteger BigIntegerPid = null;
		String pid = pDto.getPid();
		if (pid != null && pid.equals("")) {
			BigIntegerPid = Utils.StrToBigInt(pid);
			project.setPid(BigIntegerPid);
		}
		
		Long mid = Long.valueOf(pDto.getMid());
		try {
			member = mr.findById(mid).get();
		} catch (NoSuchElementException nsee) {
			throw new NoSuchElementException();
		}
		
		Pstat ps = Pstat.대기;
		Prole prole = Prole.관리자;
		try {
			ps = Pstat.valueOf(pDto.getPstat());
			prole = Prole.valueOf(pDto.getProle());
		} catch (IllegalArgumentException iae) {}
		
		
		MemberProject mp = MemberProject.builder()
				.member(member)
				.project(project)
				.pstat(ps)
				.prole(prole)
				.progress(pDto.getProgress())
				.build();
		MemberProject savedMp = mpr.save(mp);
		
		ProjectDto returnDto = new ProjectDto(savedMp);
		
		List<CollaboratorDto> collaborators = pDto.getCollaborators();
		List<CollaboratorDto> returnCollabs = new ArrayList<>();
		
		returnCollabs.add(CollaboratorDto.builder()
				.mid(savedMp.getMember().getMid().toString())
				.name(savedMp.getMember().getName())
				.prole(savedMp.getProle().toString())
				.build());
		
		for(int idx = 0; idx < collaborators.size(); idx++ ) {
			Long collabMid = Long.valueOf(collaborators.get(idx).getMid());
			String collabName = collaborators.get(idx).getName();
			if (collabMid.equals(mid)) continue;
			
			Member collab = Member.builder().mid(collabMid).name(collabName).build();
			Prole collabProle = Prole.valueOf(collaborators.get(idx).getProle());
			
			MemberProject collabMp = MemberProject.builder()
					.member(collab)
					.project(mp.getProject())
					.prole(collabProle)
					.pstat(Pstat.대기)
					.progress(0)
					.build();
			
			MemberProject savedCollabMp = mpr.save(collabMp);
			returnCollabs.add(CollaboratorDto.builder()
					.mid(savedCollabMp.getMember().getMid().toString())
					.name(savedCollabMp.getMember().getName())
					.prole(savedCollabMp.getProle().toString())
					.build());
		}
		
		Pdir rootDir = Pdir.builder()
				.dname("/")
				.parentDir(null)
				.member(member)
				.project(project)
				.build();
		
		Pdir savedDir = dr.save(rootDir);
		PdirDto rootDirDto = new PdirDto(savedDir);
		List<PdirDto> dirs= new ArrayList<PdirDto>();

		dirs.add(rootDirDto);
		returnDto.setDirs(dirs);
		returnDto.setCollaborators(returnCollabs);
		
		pns.notifyTo(pDto.getMid(), returnDto, "NEW");
		
		return returnDto;
	}
	

	/**
	 * 프로젝트 DTO로부터 MemberProject, Project를 수정합니다.
	 * @param pDto
	 * @return 변경한 MemberProject 오브젝트
	 */
	@Transactional
	public ProjectDto correct(ProjectDto pDto) {
		Project p = Project.builder()
				.pid(Utils.StrToBigInt(pDto.getPid()))
				.build();
		
		Member m = Member.builder()
				.mid(Long.valueOf(pDto.getMid()))
				.build();
		
		//원본 유저, 프로젝트
		MemberProject mp = mpr.findOneByMemberAndProjectAndDflagFalse(m, p);
		p = mp.getProject();
		m = mp.getMember();
		
		Pstat pstat;
		try {
			pstat = Pstat.valueOf(pDto.getPstat());
		} catch (IllegalArgumentException iae) {
			pstat = Pstat.대기;
		}
		mp.setPstat(pstat);
		
		Prole prole;
		try {
			prole = Prole.valueOf(pDto.getProle());
		} catch (IllegalArgumentException iae) {
			prole = mp.getProle();
		}
		mp.setProle(prole);
		
		Project updated = Project.builder()
				.pid(p.getPid())
				.udate(LocalDateTime.now())
				.build();
		Utils.overrideEntity(updated, pDto);
		
		mp.setProject(updated);
		mp.setProgress(pDto.getProgress());
		MemberProject savedMp = mpr.save(mp);
		
		// 삭제해야할 팀원
		if (pDto.getDeletedCollaborators() != null) {
			List<CollaboratorDto> shouldDeleted = pDto.getDeletedCollaborators();
			List<MemberProject> shouldDeletedMps = new ArrayList<>();
			
			Project targetProject = mp.getProject();
			for (CollaboratorDto collabs : shouldDeleted) {
				Member targetMember = Member.builder().mid(Long.valueOf(collabs.getMid())).build();
				
				MemberProject shouldDeletedMp = mpr.findOneByMemberAndProjectAndDflagFalse(targetMember, targetProject);
				shouldDeletedMps.add(shouldDeletedMp);
			}
			
			mpr.deleteAll(shouldDeletedMps);
		}
		
		List<CollaboratorDto> returnCollabs = new ArrayList<>();
		returnCollabs.add(CollaboratorDto.builder()
				.mid(savedMp.getMember().getMid().toString())
				.name(savedMp.getMember().getName())
				.prole(savedMp.getProle().toString())
				.build());
		
		ProjectDto returnDto = null;
		if (pDto.getCollaborators() != null) {
			List<CollaboratorDto> collaborators = pDto.getCollaborators();
			
			for(int idx = 0; idx < collaborators.size(); idx++ ) {
				Long collabMid = Long.valueOf(collaborators.get(idx).getMid());
				String collabName = collaborators.get(idx).getName();
				Member collab = Member.builder().mid(collabMid).name(collabName).build();
				
				if (collabMid.equals(m.getMid())) continue;
				
				MemberProject collabMp = MemberProject.builder()
						.member(collab)
						.project(mp.getProject())
						.pstat(Pstat.대기)
						.progress(0)
						.build();
				
				MemberProject originalCollab = mpr.findOneByMemberAndProjectAndDflagFalse(collab, mp.getProject());
				if (originalCollab != null) collabMp = originalCollab;
				
				Prole collabProle = Prole.valueOf(collaborators.get(idx).getProle());
				collabMp.setProle(collabProle);
				
				MemberProject savedCollabMp = mpr.save(collabMp);
				returnCollabs.add(CollaboratorDto.builder()
						.mid(savedCollabMp.getMember().getMid().toString())
						.name(savedCollabMp.getMember().getName())
						.prole(savedCollabMp.getProle().toString())
						.build());
			}
			returnDto = new ProjectDto(savedMp);
			returnDto.setCollaborators(returnCollabs);
		}
		
		if (returnDto == null) returnDto = new ProjectDto(savedMp);
		
		
		List<Pdir> rawDirs = dr.findAllByProjectAndDflagFalse(p);
		List<PdirDto> dirs = new ArrayList<PdirDto>();
		
		for (Pdir dir : rawDirs) {
			dirs.add(new PdirDto(dir));
		}
		returnDto.setDirs(dirs);
		
		pns.notifyTo(pDto.getMid(), returnDto, "/updateProject");
		return returnDto;
	}
	
	@Transactional
	public ProjectDto disable(ProjectDto pDto) throws NoSuchElementException {
		Member m = Member.builder()
				.mid(Long.valueOf(pDto.getMid()))
				.build();
		
		Project p = Project.builder()
				.pid(Utils.StrToBigInt(pDto.getPid()))
				.build();
		
		MemberProject mp = mpr.findOneByMemberAndProjectAndDflagFalse(m, p);
		mp.setDflag(true);
		
		return new ProjectDto(mpr.save(mp));
	}
}