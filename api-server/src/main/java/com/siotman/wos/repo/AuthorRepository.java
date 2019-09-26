package com.siotman.wos.repo;

import com.siotman.wos.domain.jpa.Author;
import com.siotman.wos.domain.jpa.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, String> {

//    @Query(
//            value = "select * from author a " +
//                        "where a.name like %:authorName% or a.name like %:authorName%",
//            nativeQuery = true
//    )
    List<Author> findAuthorsByFullNameContainingIgnoreCase(@Param("authorName") String authorName);

}
