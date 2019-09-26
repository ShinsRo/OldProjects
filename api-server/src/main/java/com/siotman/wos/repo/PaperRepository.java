package com.siotman.wos.repo;

import com.siotman.wos.domain.jpa.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaperRepository extends JpaRepository<Paper, String> {

//    @Query(
//            value="select * from paper p where p.title like %:title%",
//            nativeQuery=true
//    )
    List<Paper> findPapersByTitleContainingIgnoreCase(@Param("title") String title);
}
