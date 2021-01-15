package com.nj.secretary.services.monologue.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nj.secretary.services.monologue.repository.MonologueDAO;

@Repository("MonologueDAOImpl")
public class MonologueDAOImpl implements MonologueDAO{
	
	@Autowired
	SqlSession sqlsession;
	

	@Override
	public void addQuestionText(String questionText) throws Exception {
		// TODO Auto-generated method stub
		sqlsession.insert("MonologueMapper.addQuestionText", questionText);
	}

	@Override
	public void addMonologueText(String monologueText) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
