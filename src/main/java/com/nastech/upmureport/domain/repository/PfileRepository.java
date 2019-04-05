//package com.nastech.upmureport.domain.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.nastech.upmureport.domain.entity.Pdir;
//import com.nastech.upmureport.domain.entity.Pfile;
//
//import java.math.BigInteger;
//import java.util.List;
//
//@Transactional
//public interface PfileRepository extends JpaRepository<Pfile, BigInteger>{
//	
//	@Query("select p from Pfile p where p.dId = :dId and p.deleteFlag = 0")
//	List<Pfile> findByDirId(@Param(value = "dId") Pdir dId);
//}