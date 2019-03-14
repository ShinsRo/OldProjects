package com.nastech.upmureport.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.pk.UserProjectPK;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectPK> {
	public List<UserProject> findAllByUser(User user);
	public List<UserProject> findAllByProject(Project project);
	
//	@Query(value = "select up from userproject up where up.userId = :userId", nativeQuery = true)
//	public List<UserProject> findAllByUserId(@Param("userId") String userId);
	
	@Query(value = "select up from userproject up where up.projId = :projId and up.userId = :userId", nativeQuery = true)
	public UserProject findOneByUserIdAndProjId(@Param("projId") Integer projId, @Param("userId") String userId);
}
