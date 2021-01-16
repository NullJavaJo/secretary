package com.nj.secretary.services.monologue.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nj.secretary.services.monologue.repository.MonologueDAO;

@Repository("monologueDAOImpl")
public class MonologueDAOImpl implements MonologueDAO{
	
	@Autowired
	SqlSession sqlsession;
	
	public MonologueDAOImpl() {
              System.out.println("default constructor ºÎ¸£±â~~~"+getClass());
 	}
	
	
	@Override
	public void addQuestionText(String questionText) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("monologueDAOImpi  addQuestionText~~");
		sqlsession.insert("MonologueMapper.addQuestionText", questionText);
	}
	
	

	@Override
	public void addMonologueText(String monologueText) throws Exception {
		System.out.println("monologueDAOImpl  addMonologueText~~~");
		sqlsession.insert("MonologueMapper.addMonologueText", monologueText);
		// TODO Auto-generated method stub
		
	}



	@Override
	public int getQeustionId(int questionId) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("monologueDAOImpi  getQuestionId~~");
		return sqlsession.selectOne("MonologueMapper.addQuestionText", questionId);
		
	}


	@Override
	public String getQuestionText(String questionText) throws Exception {
		
		return sqlsession.selectOne("MonologueMapper.getQuestionText", questionText);
		// TODO Auto-generated method stub
		
	}
	
	
}
