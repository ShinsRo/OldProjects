package com.nastech.upmureport.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.entity.UserProjectDir;
import com.nastech.upmureport.domain.entity.UserProjectDirPK;
import com.nastech.upmureport.domain.entity.UserProjectPK;

public interface UserProjectDirRepository extends JpaRepository<UserProjectDir, UserProjectDirPK> {
	public List<UserProjectDir> findAllByUser(User user);
	public List<UserProjectDir> findAllByProject(Project project);
}
