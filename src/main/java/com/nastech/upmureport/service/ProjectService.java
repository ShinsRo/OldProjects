/** 
 * 프로젝트의 기본 CRUD를 수행하는 ProjectService 정의.
 * 
 * 2019.05.22.
 * @author 김승신
 */
package com.nastech.upmureport.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.dto.CollaboratorDto;
import com.nastech.upmureport.domain.dto.PdirDto;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.enums.Prole;
import com.nastech.upmureport.domain.enums.Pstat;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.service.ProjectNotificationService.NOTIFICATION_TYPE;
import com.nastech.upmureport.support.Utils;

@Service
public class ProjectService {
	@Autowired
	private MemberRepository 		mr;
	@Autowired
	private MemberProjectRepository mpr;
	@Autowired
	private PdirRepository 			dr;
	@Autowired
	private PdirService 			ds;
	@Autowired
	private ProjectNotificationService pns;
	
	
	/**
	 * 사원 아이디 (!= 사원 번호) 를 기준으로 모든 프로젝트들을 조회하고 DTO 리스트로 변환하여 반환한다.
	 * 
	 * 각 DTO는 기본 프로젝트 정보, 소유한 디렉토리 정보 그리고 같은 프로젝트에 소속한 팀원 정보를 갖는다.
	 * 
 	 * @author 김승신 2019.05.16.
	 * @param 	mid	사원의 고유 식별 아이디
	 * @return		프로젝트의 DTO 리스트 
	 */
	@Transactional
	public List<ProjectDto> listByMid(String mid) {
		
		/* 변수 초기화 */
		Long longMid 	= null;	// mid :String -> Long
		Member member 	= null;	// 타겟 사원 객체
		
		List<ProjectDto> 	projectDtos 		= null;	// 반환할 프로젝트 DTO 리스트 
		List<MemberProject> projectsByMember 	= null;	// 타겟 사원으로 검색한 사원-프로젝트 연결 엔티티 리스트		
		
		longMid 	= Long.valueOf(mid);
		member 		= Member.builder().mid(longMid).build();		
		projectDtos = new ArrayList<ProjectDto>();
		/* 변수 초기화 끝 */
		
		
		// 대상 사원으로 사원-프로젝트 연결 엔티티 조회
		projectsByMember = mpr.findAllByMemberAndDflagFalse(member);
		
		/* 조회한 각 사원-프로젝트 엔티티로 추가 정보 조회 및 DTO 변환 */
		for (MemberProject mp : projectsByMember) {

			/* 변수 초기화 */
			ProjectDto pDto = null;	// 검색한 Member-Project에서 빼낸 프로젝트의 DTO
			
			List<PdirDto> dirs 							= null;	// 해당 Project의 디렉토리 DTO 리스트
			List<MemberProject> collaboratorsByProject 	= null;	// 해당 Project로 검색한 사원(공동 작업[소유]자)-프로젝트 연결 엔티티 리스트
			List<CollaboratorDto> collaborators 		= null;	// 사원(공동 작업[소유]자) DTO 리스트
			
			pDto 			= new ProjectDto(mp);				// 기본 정보 변환
			collaborators 	= new ArrayList<CollaboratorDto>();
			/* 변수 초기화 끝 */
			
			
			// 각 프로젝트 아이디로 소유한 디렉토리 조회
			dirs = ds.listByPid(pDto.getPid());
			pDto.setDirs(dirs);

			// 대상 프로젝트로 사원(공동 작업[소유]자)-프로젝트 연결 엔티티 조회
			collaboratorsByProject = mpr.findAllByProjectAndDflagFalse(mp.getProject());
			
			/* 조회한 각 공동작업자 엔티티를 DTO로 변환 */
			for (MemberProject cp : collaboratorsByProject) {

				String collabMid 	= cp.getMember().getMid().toString();
				String collabName 	= cp.getMember().getName();
				String collabProle 	= cp.getProle().toString();
				
				collaborators.add(
					CollaboratorDto
						.builder()
						.mid(collabMid)
						.name(collabName)
						.prole(collabProle)
						.build()
				);
			}
			pDto.setCollaborators(collaborators);
			/* 조회한 각 공동작업자 엔티티를 DTO로 변환 끝 */
			
			projectDtos.add(pDto);
		}
		/* 조회한 각 사원-프로젝트 엔티티로 추가 정보 조회 및 DTO 변환 끝 */
		
		return projectDtos;
	}
	
	/**
	 * 넘겨받은 DTO에 맞게 프로젝트를 등록한다.
	 * 
	 * 등록한 프로젝트 정보를 반환하고, 실패하면 null을 반환한다.
	 * 프로젝트를 등록할 때에 입력받은 정보 외 루트 디렉토리도 추가한다.
	 * 
	 * @author 김승신		2019.05.16.
	 * @param 	pDto 	프로젝트 등록할 정보
	 * @return 			등록된 프로젝트 정보
	 * @throws Exception 
	 */
	@Transactional
	public ProjectDto register(ProjectDto pDto) throws Exception {
		/* 변수 정의 */
		Long mid 	= null;	// 프로젝트 등록자 멤버 아이디

		Pstat pstat = null;	// 등록할 프로젝트 상태
		Prole prole = null;	// 등록할 프로젝트의 등록자 권한
		
		Member member 		= null;	// 등록자 엔티티
		Project project 	= null;	// 등록할 프로젝트 엔티티

		MemberProject mp 		= null;	// 사원-프로젝트 관계 엔티티
		MemberProject savedMP 	= null;	// 저장한 사원-프로젝트 엔티티

		List<CollaboratorDto> collaborators = null;	// 공동참여자로 등록할 사원 리스트
		List<CollaboratorDto> returnCollabs = null;	// 공동참여자 등록에 성공한 사원 리스트
		
		Pdir rootDir 		= null;	// 루트 디렉토리 엔티티
		PdirDto rootDirDto 	= null;	// 루트 디렉토리 DTO
		List<PdirDto> dirs 	= null;	// 프로젝트 DTO에 포함할 디렉토리 DTO 리스트
		
		ProjectDto returnDto = null;	// 반환할 프로젝트 DTO
		/* 변수 정의 끝 */
		
		// 사원 조회
		mid 	= Long.valueOf(pDto.getMid());
		member 	= mr.findById(mid).get(); 

		/* 프로젝트 연결 엔티티 저장 */
		pstat 	= Pstat.대기;
		prole 	= Prole.관리자;
		pstat 	= Pstat.valueOf(pDto.getPstat());
		prole 	= Prole.valueOf(pDto.getProle());
				
		project = Project.builder()
					.pname(			pDto.getPname())
					.description(	pDto.getDescription())
					.stDate(		pDto.getStDate())
					.edDate(		pDto.getEdDate())
					.build();
		
		mp 		= MemberProject.builder()
					.member(		member)
					.project(		project)
					.pstat(			pstat)
					.prole(			prole)
					.progress(		pDto.getProgress())
					.build();
		
		// 관계 엔티티를 저장하며 프로젝트도 함께 DB에 저장한다.
		savedMP = mpr.save(mp);
		returnDto = new ProjectDto(savedMP);
		/* 프로젝트 연결 엔티티 저장 끝 */
		
		/* 협업자를 프로젝트에 연결 */
		collaborators = pDto.getCollaborators();
		returnCollabs = new ArrayList<>();
		returnCollabs.add(
			CollaboratorDto.builder()
				.mid(	savedMP.getMember().getMid().toString())
				.name(	savedMP.getMember().getName())
				.prole(	savedMP.getProle().toString())
				.build()
		);
		
		/* 협업자-프로젝트 엔티티 저장 */
		for(int idx = 0; idx < collaborators.size(); idx++ ) {
			Long collabMid = Long.valueOf(collaborators.get(idx).getMid());	// 협업자 아이디
			if (collabMid.equals(mid)) continue;									// 협업자가 등록자와 같을 경우 해당 프로세스를 수행하지 않는다.

			String collabName 			= collaborators.get(idx).getName();					// 협업자 이름
			MemberProject collabMp 		= null;												// 협업자-프로젝트 관계 객체
			MemberProject savedCollabMp = null;												// 협업자-프로젝트 관계 엔티티
			
			Member collab 		= Member.builder().mid(collabMid).name(collabName).build();		// 협업자 객체
			Prole collabProle 	= Prole.valueOf(collaborators.get(idx).getProle());				// 협업자 권한
			
			collabMp = MemberProject.builder()
					.member(	collab)
					.prole(		collabProle)
					.project(	mp.getProject())
					.progress(	mp.getProgress())
					.pstat(		mp.getPstat())
					.build();
			
			// 엔티티 저장
			savedCollabMp = mpr.save(collabMp);
			
			// DTO 변환
			returnCollabs.add(
				CollaboratorDto.builder()
					.mid(	savedCollabMp.getMember().getMid().toString())
					.name(	savedCollabMp.getMember().getName())
					.prole(	savedCollabMp.getProle().toString())
					.build()
			);
		}
		/* 협업자-프로젝트 엔티티 저장 끝 */

		returnDto.setCollaborators(returnCollabs);	// 협업자 DTO 리스트 반환 DTO에 셋팅
		/* 협업자를 프로젝트에 연결 끝 */
		
		/* 루트 디렉토리 생성, 저장 및 DTO 변환 */
		rootDir = Pdir.builder()
					.dname(		"/")
					.parentDir(	null)
					.member(	member)
					.project(	project)
					.build();
		
		rootDir 	= dr.save(rootDir);			// 루트 디렉토리 저장
		rootDirDto 	= new PdirDto(rootDir);		// DTO 변환
		dirs 		= new ArrayList<PdirDto>();

		dirs.add(rootDirDto);
		returnDto.setDirs(dirs);
		/* 루트 디렉토리 생성, 저장 및 DTO 변환 끝 */
		
		// 접속 중인 협업자와 통신, 알림
		pns.sendDtoByType(pDto.getMid(), returnDto, NOTIFICATION_TYPE.NEW_PROJECT);
		
		return returnDto;
	}
	

	/**
	 * 넘겨받은 DTO에 맞게 프로젝트를 수정한다.
	 * 
	 * 수정한 프로젝트 정보를 반환하고, 실패하면 null을 반환한다.
	 * 
 	 * @author 김승신		2019.05.16.
	 * @param 	pDto	수정할 프로젝트 정보
	 * @return 			변경한 MemberProject 오브젝트
	 * @throws Exception 
	 */
	@Transactional
	public ProjectDto correct(ProjectDto pDto) throws Exception {
		/* 변수 정의 */
		BigInteger pid 	= null;	// 대상 프로젝트 아이디
		Long mid 		= null;	// 수정하는 이 사원 아이디
		
		Project project = null;	// 대상 프로젝트 객체
		Member member 	= null;	// 수정하는 이 객체
		Project overwritedProject	= null; // 수정한 프로젝트 하이버네이트 프록시 엔티티 
		
		MemberProject mp 		= null;	// 수정하는 이-프로젝트 객체
		MemberProject savedMp 	= null;	// 저장된 수정하는 이-프로젝트 엔티티
		
		Pstat pstat = null;	// 변경할 프로젝트 상태
		Prole prole = null;	// 변경할 수정하는 이 권한

		List<CollaboratorDto> returnCollabs	= null;	// 반환할 협업자 DTO 리스트

		ProjectDto returnDto = null;	// 반환할 프로젝트 DTO 
		/* 변수 정의 끝 */
		
		/* 프로젝트를 수정하는 사원의 프로젝트 및 프로젝트-사원 정보 변경 */
		pid = Utils.StrToBigInt(pDto.getPid());
		mid = Long.valueOf(pDto.getMid());
		
		try {
			pstat = Pstat.valueOf(pDto.getPstat());
			prole = Prole.valueOf(pDto.getProle());
		} catch (IllegalArgumentException iae) {
			throw iae;
		}
		
		project = Project.builder()
				.pid(pid)
				.build();
		
		member = Member.builder()
				.mid(mid)
				.build();
		
		// 대상 수정하는 이의 멤버-프로젝트 엔티티 조회
		mp 		= mpr.findOneByMemberAndProjectAndDflagFalse(member, project);
		project = mp.getProject();  
		member 	= mp.getMember();
		
		mp.setPstat(pstat);
		mp.setProle(prole);
		
		// ProjectDto의 빈 값이 아닌 데이터를 모두 빈 프로젝트 엔티티에 덮어쓰기
		overwritedProject = Project.builder()
				.pid(	pid)
				.udate(	LocalDateTime.now())
				.build();
		Utils.overwriteEntity(overwritedProject, pDto);
		
		mp.setProject(	overwritedProject);
		mp.setProgress(	pDto.getProgress());
		
		savedMp = mpr.save(mp);
		/* 프로젝트를 수정하는 사원의 프로젝트 및 프로젝트-사원 정보 변경 끝 */
		
		/* 삭제해야할 협업자 삭제 */
		if (pDto.getDeletedCollaborators() != null) {
			List<CollaboratorDto> shouldDeleted 	= pDto.getDeletedCollaborators();	// 삭제해야할 협업자 리스트
			List<MemberProject> shouldDeletedMps 	= new ArrayList<>();				// 삭제해야할 협업자-프로젝트 연결 엔티티 리스트
			Project targetProject 					= mp.getProject();					// 대상 프로젝트
			
			// 삭제해야할 협업자-프로젝트 연결 엔티티 조회
			for (CollaboratorDto collabs : shouldDeleted) {
				Long collabsMid 	= Long.valueOf(collabs.getMid());			// 협업자 아이디
				Member targetMember = Member.builder().mid(collabsMid).build();	// 협업자 객체
				
				MemberProject shouldDeletedMp = mpr.findOneByMemberAndProjectAndDflagFalse(targetMember, targetProject);
				shouldDeletedMps.add(shouldDeletedMp);
			}
			
			// 엔티티 리스트 삭제
			mpr.deleteAll(shouldDeletedMps);
		}
		/* 삭제해야할 팀원 삭제 끝 */
		
		/* 프로젝트의 협업자 권한 변경 */
		returnCollabs = new ArrayList<>();
		
		// 수정하는 이의 저장한 프로젝트 정보를 토대로 수정하는 이를 협업자 리스트에 추가 
		returnCollabs.add(
			CollaboratorDto.builder()
				.mid(	savedMp.getMember().getMid().toString())
				.name(	savedMp.getMember().getName())
				.prole(	savedMp.getProle().toString())
				.build()
		);
		
		if (pDto.getCollaborators() != null/* && 권한 추가 필요 */) {
			List<CollaboratorDto> collaborators = pDto.getCollaborators();	// 협업자의 변경정보
			
			for(int idx = 0; idx < collaborators.size(); idx++ ) {
				Long collabMid 		= Long.valueOf(collaborators.get(idx).getMid());	// 협업자 아이디
				Prole collabProle 	= Prole.valueOf(collaborators.get(idx).getProle());	// 협업자 권한
				String collabName 	= collaborators.get(idx).getName();					// 협업자 이름
				
				Member collab 						= Member.builder().mid(collabMid).name(collabName).build();	// 협업자 객체
				MemberProject collabMp 				= null;	// 저장할 협업자 정보
				MemberProject savedCollabMp			= null;	// 저장한 협업자 정보
				
				// 수정하는 이와 협업자가 같을 경우 처리하지 않는다.
				if (collabMid.equals(member.getMid())) continue;
				
				// 협업자를 조회
				collabMp = mpr.findOneByMemberAndProjectAndDflagFalse(collab, mp.getProject());
				
				// 새로운 협업자인 경우
				if (collabMp == null) {
					collabMp = MemberProject.builder()
							.project(	mp.getProject())
							.member(	collab)
							.progress(	0)
							.build();
							
				}
				collabMp.setProle(collabProle);

				// 협업자 변경내용 저장
				savedCollabMp = mpr.save(collabMp);
				returnCollabs.add(
						CollaboratorDto.builder()
							.mid(	savedCollabMp.getMember().getMid().toString())
							.name(	savedCollabMp.getMember().getName())
							.prole(	savedCollabMp.getProle().toString())
							.build()
				);
			}
			
			// 반환 DTO 생성
			returnDto = new ProjectDto(savedMp);
			returnDto.setCollaborators(returnCollabs);
		}
		/* 프로젝트의 협업자 상태 변경 끝*/
		
		// 협업자 권한 변경을 처리하지 않은 경우, 반환 DTO 생성
		if (returnDto == null) returnDto = new ProjectDto(savedMp);
		
		/* Directory DTO 추가 */
		List<Pdir> rawDirs = dr.findAllByProjectAndDflagFalse(project);
		List<PdirDto> dirs = new ArrayList<PdirDto>();
		
		for (Pdir dir : rawDirs) {
			dirs.add(new PdirDto(dir));
		}
		returnDto.setDirs(dirs);
		/* Directory DTO 추가 끝 */
		
		// 접속 중인 협업자와 통신, 알림
		pns.sendDtoByType(pDto.getMid(), returnDto, NOTIFICATION_TYPE.CORRECT_PROJECT);
		return returnDto;
	}
	
	/**
	 * 대상 프로젝트의 삭제 플래그를 활성화한다. pDto가 pid을 반드시 지녀야한다.
	 * 모든 조회 메서드는 삭제 플래그를 비활성화한 엔티티를 조회하는 것이 기본 값이므로 이를 참고한다.
	 * 
 	 * @author 김승신		2019.05.16.
	 * @param 	pDto	삭제할 pid, prole을 지닌 ProjectDto
	 * @return			삭제한 Project의 Dto
	 * @throws 	NoSuchElementException
	 */
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