package com.nj.secretary.services.monologue.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nj.secretary.services.monologue.repository.MonologueDAO;
import com.nj.secretary.services.monologue.service.MonologueService;

@Service
public class MonologueServiceImpl implements MonologueService {
	
	@Autowired
	@Qualifier("monologueDAOImpl")
	MonologueDAO monologueDAO;

	@Override
	public void addQuestionText(String questionText) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("monologueServiceImpl   addQuestionText@@");
		monologueDAO.addQuestionText(questionText);
		
	}

	@Override
	public void addMonologueText(String monologueText) throws Exception{
		// TODO Auto-generated method stub
		monologueDAO.addMonologueText(monologueText);
		
	}

	@Override
	public int getQuestionId(int questionId) throws Exception {
		System.out.println("monologueServiceImpl   getQuestionId@@");
		// TODO Auto-generated method stub
		return monologueDAO.getQeustionId(questionId);
	}
	
	

}
