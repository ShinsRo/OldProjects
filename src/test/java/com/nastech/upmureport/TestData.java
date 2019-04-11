package com.nastech.upmureport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nastech.upmureport.domain.entity.AuthInfo;
import com.nastech.upmureport.domain.entity.Career;
import com.nastech.upmureport.domain.entity.Member;
import com.nastech.upmureport.domain.entity.MemberProject;
import com.nastech.upmureport.domain.entity.MemberSystem;
import com.nastech.upmureport.domain.entity.Pdir;
import com.nastech.upmureport.domain.entity.Pfile;
import com.nastech.upmureport.domain.entity.PfileLog;
import com.nastech.upmureport.domain.entity.PfileLog.LogStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.Role;
import com.nastech.upmureport.domain.entity.support.Prole;
import com.nastech.upmureport.domain.entity.support.Pstat;
import com.nastech.upmureport.domain.repository.AttachmentLogRepository;
import com.nastech.upmureport.domain.repository.AttachmentRepository;
import com.nastech.upmureport.domain.repository.AuthInfoRepository;
import com.nastech.upmureport.domain.repository.CareerRepository;
import com.nastech.upmureport.domain.repository.MemberProjectRepository;
import com.nastech.upmureport.domain.repository.MemberRepository;
import com.nastech.upmureport.domain.repository.MemberSystemRepository;
import com.nastech.upmureport.domain.repository.PdirRepository;
import com.nastech.upmureport.domain.repository.PfileLogRepository;
import com.nastech.upmureport.domain.repository.PfileRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;

@Service 
public class TestData {
		//Member repositories
		MemberRepository memberRepository;
		MemberSystemRepository memberSystemRepository;
		AuthInfoRepository authinfoRepository;
		CareerRepository careerRepository;
		
		//Project and pdir repositories
		MemberProjectRepository memberProjectRepository;
		ProjectRepository projectRepository;
		PdirRepository pdirRepository;
		
		//pfile, pfile log, attachment, attachment log
		PfileRepository pfileRepository;
		PfileLogRepository pfileLogRepository;
		AttachmentRepository attachmentRepository;
		AttachmentLogRepository attachmentLogRepository;
	
	
	public TestData(MemberRepository memberRepository, MemberSystemRepository memberSystemRepository,
			AuthInfoRepository authinfoRepository, CareerRepository careerRepository, PfileRepository pfileRepository, PfileLogRepository pfileLogRepository,
			AttachmentRepository attachmentRepository, AttachmentLogRepository attachmentLogRepository,
			MemberProjectRepository memberProjectRepository, ProjectRepository projectRepository, PdirRepository pdirRepository) {

		this.memberRepository = memberRepository;
		this.memberSystemRepository = memberSystemRepository;
		this.authinfoRepository = authinfoRepository;
		this.careerRepository = careerRepository;
		
		this.memberProjectRepository = memberProjectRepository;
		this.projectRepository = projectRepository;
		this.pdirRepository = pdirRepository;
		
		this.pfileRepository = pfileRepository;
		this.pfileLogRepository = pfileLogRepository;
		this.attachmentRepository = attachmentRepository;
		this.attachmentLogRepository = attachmentLogRepository;
		
	}

	/**
	 * 멤버 테스트 데이터 생성 메서드
	 * 관리자, (관리자) ROLE_ADMIN
	 * 김승신, 김윤상, 마규석 (인턴) ROLE_USER
	 */
	public void setMemberTestData() {
		List<Member> mList = new ArrayList<Member>();
		
		Member admin = Member.builder()
				.eid("0000")
				.name("관리자")
				.birth("00000000")
				.phoneNum("01011111111")
				.joinDate(LocalDate.now())
				.build();
		mList.add(admin);
		
		Member m1 = Member.builder()
				.eid("1111")
				.name("김승신")
				.birth("19940728")
				.phoneNum("01011111111")
				.joinDate(LocalDate.now())
				.build();
		mList.add(m1);
		
		Member m2 = Member.builder()
				.eid("1112")
				.name("마규석")
				.birth("19951226")
				.phoneNum("01012345555")
				.joinDate(LocalDate.now())
				.build();
		mList.add(m2);
		
		Member m3 = Member.builder()
				.eid("1113")
				.name("김윤상")
				.birth("19940729")
				.phoneNum("01011131111")
				.joinDate(LocalDate.now())
				.build();
		mList.add(m3);
		
		List<Career> cList = new ArrayList<Career>();
		
		Career c0 = Career.builder()
				.dept("관리").posi("관리").active(true).startDate(LocalDate.now()).member(admin)
				.build();
		Career c1 = Career.builder()
				.dept("인턴부").posi("인턴").active(true).startDate(LocalDate.now()).member(m1)
				.build();
		Career c2 = Career.builder()
				.dept("인턴부").posi("인턴").active(true).startDate(LocalDate.now()).member(m2)
				.build();
		Career c3 = Career.builder()
				.dept("인턴부").posi("인턴").active(true).startDate(LocalDate.now()).member(m3)
				.build();
		
		cList.add(c0);
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		
		List<AuthInfo> aList = new ArrayList<AuthInfo>();
		
		AuthInfo a0 = AuthInfo.builder().member(admin).username("admin").password("1111").role(Role.ROLE_ADMIN).build();
		AuthInfo a1 = AuthInfo.builder().member(m1).username("m1111").password("1111").role(Role.ROLE_USER).build();
		AuthInfo a2 = AuthInfo.builder().member(m1).username("m1112").password("1111").role(Role.ROLE_USER).build();
		AuthInfo a3 = AuthInfo.builder().member(m1).username("m1113").password("1111").role(Role.ROLE_USER).build();
		
		aList.add(a0);
		aList.add(a1);
		aList.add(a2);
		aList.add(a3);
		
		List<MemberSystem> msList = new ArrayList<MemberSystem>();
		MemberSystem ms1 = MemberSystem.builder().senior(admin).junior(m1).build();
		MemberSystem ms2 = MemberSystem.builder().senior(admin).junior(m2).build();
		MemberSystem ms3 = MemberSystem.builder().senior(admin).junior(m3).build();
		
		msList.add(ms3);
		msList.add(ms2);
		msList.add(ms1);

		memberRepository.saveAll(mList);
		authinfoRepository.saveAll(aList);
		careerRepository.saveAll(cList);
		memberSystemRepository.saveAll(msList);
	}
	
	public void deleteAllMemberData() {
		memberSystemRepository.deleteAll();
		careerRepository.deleteAll();
		authinfoRepository.deleteAll();
		memberRepository.deleteAll();
	}
	
	/**
	 * 프로젝트 테스트 데이터, (멤버 테스트 데이터 생성이 선행될 것)
	 * 프로젝트에 대한 조작은 삭제를 제외하고, MemberProject에서 접근함을 원칙으로 한다.
	 */
	public void setProjectTestData() {
		List<Member> mList = memberRepository.findAll();
		
		List<Project> pList = new ArrayList<Project>();
		
		Project p1 = Project.builder()
				.pname("일일 업무 보고")
				.description("일일 간 업무를 보고한다.")
				.stDate(LocalDateTime.now())
				.edDate(LocalDateTime.now())
				.build();
		
		Project p2 = Project.builder()
				.pname("업무리포트2웹 프로젝트")
				.description("C# 업무리포트 프로그램을 웹으로 포팅한다.")
				.stDate(LocalDateTime.now())
				.edDate(LocalDateTime.now())
				.build();
		
		Project p3 = Project.builder()
				.pname("아무개 프로젝트")
				.description("시크릿 프로젝트")
				.stDate(LocalDateTime.now())
				.edDate(LocalDateTime.now())
				.build();
		
		pList.add(p1); 
		pList.add(p2); 
		pList.add(p3);
		
		List<MemberProject> mpList = new ArrayList<MemberProject>();
		
		for (Member m: mList) {
			Prole prole = Prole.관리자;
			
			if(m.getEid().equals("0000")) {
				prole = Prole.책임자;
			}

			
			MemberProject mp1 = MemberProject.builder()
					.member(m)
					.project(p1)
					.pstat(Pstat.대기)
					.prole(prole)
					.progress(0)
					.build();
			
			MemberProject mp2 = MemberProject.builder()
					.member(m)
					.project(p2)
					.pstat(Pstat.진행)
					.prole(prole)
					.progress(60)
					.build();
			
			mpList.add(mp1);
			mpList.add(mp2);
		}

		MemberProject mp3 = MemberProject.builder()
				.member(mList.get(1))
				.project(p3)
				.pstat(Pstat.완료)
				.prole(Prole.책임자)
				.progress(100)
				.build();
		
		mpList.add(mp3);
		
		mpList = memberProjectRepository.saveAll(mpList);
	}
	public void deleteAllProjectData() {
		pdirRepository.deleteAll();
		memberProjectRepository.deleteAll();
		projectRepository.deleteAll();
	}
	
	/**
	 * 프로젝트 테스트 데이터, (프로젝트 테스트 데이터 생성이 선행될 것)
	 * 
	 * 프로젝트 아래 디렉토리 형식
	 * \/멤버이름
	 * |----/child1
	 * |	|----/child3
	 * |
	 * |----/child2
	 */
	public void setPdirTestData() {
		List<MemberProject> mpList = memberProjectRepository.findAll();
		List<Pdir> roots = new ArrayList<Pdir>();
		List<Pdir> pdList = new ArrayList<Pdir>();
		
		for (MemberProject mp : mpList) {
			if (mp.getProle().compareTo(Prole.책임자) != 0) continue;
			
			Pdir rootDir = Pdir.builder()
					.dname("/")
					.parentDir(null)
					.member(mp.getMember())
					.project(mp.getProject())
					.build();
			
			roots.add(pdirRepository.save(rootDir));			
		}
		
		for (Pdir root : roots) {
			
			Pdir child1 = Pdir.builder()
					.dname("child1")
					.parentDir(root)
					.member(root.getMember())
					.project(root.getProject())
					.build();
			
			Pdir child2 = Pdir.builder()
					.dname("child2")
					.parentDir(root)
					.member(root.getMember())
					.project(root.getProject())
					.build();
			
			Pdir child3 = Pdir.builder()
					.dname("child3")
					.parentDir(child1)
					.member(root.getMember())
					.project(root.getProject())
					.build();
			
			pdList.add(child1);
			pdList.add(child2);
			pdList.add(child3);
		}
		
		pdirRepository.saveAll(pdList);
	}
	
	public void deleteAllPdirData() {
		pdirRepository.deleteAll();
	}
	
	public void setPfileTestData() {
		List<Pdir> pdirs = pdirRepository.findAll();
		List<Member> members = memberRepository.findAll();
		
		List<Pfile> pfiles = new ArrayList<Pfile>();
		List<PfileLog> pfileLogs = new ArrayList<PfileLog>();
		
		Pfile pfile1 = Pfile.builder().pdir(pdirs.get(0))
				.name("0408업무일지")
				.contents("오늘은 날씨가 좋았다ㅎㅎㅎㅎ")
				.build();
		
		Pfile pfile2 = Pfile.builder().pdir(pdirs.get(0))
				.name("0409업무일지")
				.contents("ㅎㅎㅎㅎ")
				.build();
		
		Pfile pfile3 = Pfile.builder().pdir(pdirs.get(0))
				.name("0410업무일지")
				.contents("오늘은 날씨가 구리다ㅎㅎㅎㅎ")
				.build();
		
		pfiles.add(pfile1);
		pfiles.add(pfile2);
		pfiles.add(pfile3);
		
		
		PfileLog pfileLog1 = PfileLog.builder().mId(members.get(0))
				.pfile(pfile1)
				.contents("로그 생성~~~~")
				.stat(LogStat.CREATE)
				.build();
		
		PfileLog pfileLog2 = PfileLog.builder().mId(members.get(0))
				.pfile(pfile2)
				.contents("로그 생성2222~~~~")
				.stat(LogStat.CREATE)
				.build();
		
		PfileLog pfileLog3 = PfileLog.builder().mId(members.get(0))
				.pfile(pfile3)
				.contents("로그 생성33333~~~~")
				.stat(LogStat.CREATE)
				.build();
		
		pfileLogs.add(pfileLog1);
		pfileLogs.add(pfileLog2);
		pfileLogs.add(pfileLog3);
		
		pfileRepository.saveAll(pfiles);
		pfileLogRepository.saveAll(pfileLogs);		
	}
}
