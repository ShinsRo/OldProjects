package org.sejonghacker.botum;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractDAO<E> implements RawDAO<E> {
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String daoID = "Abstract";
	
	@Override
	public Integer insert(E params) {
		return sqlSession.insert(daoID + "_insert" , params);
	}

	@Override
	public E select(E params) {
		return sqlSession.selectOne(daoID + "_selectOne", params);
	}

	@Override
	public Integer delete(E params) {
		return sqlSession.delete(daoID + "_delete", params);
	}

	@Override
	public E update(E params) {
		return null;
	}

	@Override
	public List<E> getAllList(E params) {

		return null;
	}

	@Override
	public Integer getCount() {

		return null;
	}
	
	
}
