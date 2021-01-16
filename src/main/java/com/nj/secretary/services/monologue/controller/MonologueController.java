package com.nj.secretary.services.monologue.controller;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nj.secretary.services.monologue.service.MonologueService;

@Controller
@RequestMapping("/monologue/*")
public class MonologueController {
	
	@Autowired
	// @Qualifier("monologueServiceImpl")
	MonologueService monologueService;
	

	@GetMapping("addQuestionText")
	public String addQuestionText() throws Exception {
		System.out.println("monologueController addQuestionText Ω√¿€");
		return "home";
	}

	@PostMapping("getQuestionId")
	public int getQuestionId(int questionId) throws Exception {
		System.out.println("monologueController" + questionId);
		monologueService.getQuestionId(questionId);

		return questionId;

	}

	@PostMapping("addQuestionText")
	public String addQuestionText(String questionText) throws Exception {
		System.out.println("monologueController" + questionText);
		monologueService.addQuestionText(questionText);

		return "home";

	}
    
	
	
	
	
}
