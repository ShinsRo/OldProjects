package com.nastech.upmureport.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.entity.UserProjectPK;

public interface UserProjectRepository extends JpaRepository<UserProject, UserProjectPK> {
	public List<UserProject> findAllByUser(User user);
	public List<UserProject> findAllByProject(Project project);
}
