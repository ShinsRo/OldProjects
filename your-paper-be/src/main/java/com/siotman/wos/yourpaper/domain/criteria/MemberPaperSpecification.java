package com.siotman.wos.yourpaper.domain.criteria;

import com.siotman.wos.yourpaper.domain.entity.Member;
import com.siotman.wos.yourpaper.domain.entity.MemberPaper;
import com.siotman.wos.yourpaper.domain.entity.SourceInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

public class MemberPaperSpecification implements Specification<MemberPaper> {
    private MemberPaperCriteria criteria;

    public MemberPaperSpecification(MemberPaperCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<MemberPaper> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {

        Path<String> expression;
        if (criteria.getField().contains(".")) {
            String[] joins = criteria.getField().split("\\.");
            expression = root.get(joins[0]);
            for (int i = 1; i < joins.length; i++) {
                expression = expression.get(joins[i]);
            }

        } else {
            expression = root.get(criteria.getField());
        }


        switch (criteria.getOperation()) {
            case LIKE:

                String likeValue = '%' + (String)criteria.getValue() + '%';

                return criteriaBuilder.like(expression.as(String.class), likeValue);

            case IGNORE:
                return criteriaBuilder.notEqual(expression, criteria.getValue());
            case MATCH:
                return criteriaBuilder.equal(expression, criteria.getValue());
            default: break;
        }

        return null;
    }
}
