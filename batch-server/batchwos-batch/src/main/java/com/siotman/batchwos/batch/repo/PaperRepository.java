package com.siotman.batchwos.batch.repo;

import com.siotman.batchwos.batch.domain.jpa.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, String> {
}
