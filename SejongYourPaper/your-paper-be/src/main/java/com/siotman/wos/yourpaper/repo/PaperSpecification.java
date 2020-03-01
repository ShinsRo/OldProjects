package com.siotman.wos.yourpaper.repo;

import com.siotman.wos.yourpaper.domain.entity.Paper;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class PaperSpecification {
    public static Specification<Paper> searchPaperLike(String fieldName, String value) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            String likeValue = "%" + value + "%";
            Predicate predicate;
            predicate = criteriaBuilder.like(root.get(fieldName).as(String.class), likeValue);
            return criteriaBuilder.and(predicate);
        };
    }
}
