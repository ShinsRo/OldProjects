package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.MemberProject;

public interface MemberProjectRepository extends JpaRepository<MemberProject, BigInteger> {
	
//	@Query(value = "select * from memberproject where deleteFlag = false and userId = :userId", nativeQuery = true)
//	public List<MemberProject> findAllByMember(@Param("mid") BigInteger mid);
//	public List<MemberProject> findAllByProject(Project project);
//	
////	@Query(value = "select up from userproject up where up.userId = :userId", nativeQuery = true)
////	public List<UserProject> findAllByUserId(@Param("userId") String userId);
//	
//	@Query(value = "select * from memberproject where pid = :pid and mid = :mid", nativeQuery = true)
//	public MemberProject findOneByUserIdAndPid(@Param("pid") BigInteger pid, @Param("mid") BigInteger mid);
}
