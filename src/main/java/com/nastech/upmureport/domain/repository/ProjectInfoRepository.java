package com.nastech.upmureport.domain.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nastech.upmureport.domain.entity.MemberProject;

public interface ProjectInfoRepository extends JpaRepository<MemberProject, BigInteger>{
	
	@Query(
		nativeQuery = true,
		
		countQuery = "SELECT COUNT(mp.mpid), p.st_date"
				+ " FROM member_project mp INNER JOIN ("
					+ "SELECT pid, st_date "
					+ "FROM project "
					+ "WHERE st_date BETWEEN (:from) AND (:to)"
				+ ") p ON p.pid=mp.pid",
				
		value = "SELECT mp.*, p.st_date, p.ed_date"
				+ " FROM member_project mp INNER JOIN ("
					+ "SELECT pid, st_date, ed_date"
					+ " FROM project"
					+ " WHERE st_date BETWEEN (:from) AND (:to)"
				+ ") p ON p.pid=mp.pid"
	)
	public Page<MemberProject> projectInfoByStDateBetween(
			@Param(value="from") LocalDateTime from, 
			@Param(value="to") LocalDateTime to, 
			Pageable pageable);
	
	@Query(
		nativeQuery = true,
		countQuery = "SELECT COUNT(mp.mpid), p.ed_date"
				+ " FROM member_project mp INNER JOIN ("
					+ "SELECT pid, ed_date "
					+ "FROM project "
					+ "WHERE ed_date BETWEEN (:from) AND (:to)"
				+ ") p ON p.pid=mp.pid",
				
		value = "SELECT mp.*, p.st_date, p.ed_date"
				+ " FROM member_project mp INNER JOIN ("
					+ "SELECT pid, st_date, ed_date"
					+ " FROM project"
					+ " WHERE ed_date BETWEEN (:from) AND (:to)"
				+ ") p ON p.pid=mp.pid"
	)
	public Page<MemberProject> projectInfoByEdDateBetween(
			@Param(value="from") LocalDateTime from, 
			@Param(value="to") LocalDateTime to, 
			Pageable pageable);
	
	@Query(
		nativeQuery = true,
		value = "SELECT p.*, mp.progress_avg, mp.mcnt"
				+ " FROM project p INNER JOIN ("
				+ "SELECT pid, AVG(progress) as progress_avg, COUNT(mid) as mcnt, dflag"
				+ " FROM member_project"
				+ " GROUP BY pid"
				+ " HAVING dflag=false"
				+ ") mp ON p.pid=mp.pid"
				+ " WHERE p.st_date BETWEEN (:from) AND (:to)"
	)
	public Page<Map<String, Object>> groupedProjectInfoPageByStDateBetween(
			@Param(value="from") LocalDateTime from, 
			@Param(value="to") LocalDateTime to, 
			Pageable pageable);
	
	@Query(
		nativeQuery = true,
		value = "SELECT p.*, mp.progress_avg, mp.mcnt"
				+ " FROM project p INNER JOIN ("
					+ "SELECT pid, AVG(progress) as progress_avg, COUNT(mid) as mcnt, dflag"
					+ " FROM member_project"
					+ " GROUP BY pid"
					+ " HAVING dflag=false"
				+ ") mp ON p.pid=mp.pid"
				+ " WHERE p.ed_date BETWEEN (:from) AND (:to)"
	)
	public Page<Map<String, Object>> groupedProjectInfoPageByEtDateBetween(
			@Param(value="from") LocalDateTime from, 
			@Param(value="to") LocalDateTime to, 
			Pageable pageable);
}
