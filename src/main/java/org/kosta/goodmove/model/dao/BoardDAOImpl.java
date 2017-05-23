package org.kosta.goodmove.model.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
/**
 * 메타 정보 관할 컨트롤러 : DAO
 * @author AreadyDoneTeam
 * @version 1
 */
@Repository
public class BoardDAOImpl implements BoardDAO{
 @Resource
 private SqlSessionTemplate template;
 
}
