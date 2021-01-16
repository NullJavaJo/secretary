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
	 //@Qualifier("monologueServiceImpl")
	MonologueService monologueService;
	

	@GetMapping("addQuestionText")
	public String addQuestionText() throws Exception {
		System.out.println("monologueController addQuestionText 시작");
		return "home/home";
	}

	@PostMapping("addQuestionText")
	public String addQuestionText(String questionText) throws Exception {
		System.out.println("monologueController addQuestionText  " + questionText);
		monologueService.addQuestionText(questionText);

		return "monologue/monologue";

	}
	
	
	@GetMapping("addMonologueText")
	public String addMonologueText() throws Exception{
		System.out.println("monologueController addMonologueText 시작");
		return "monologue/monologue";
	}
	
	@PostMapping("addMonologueText")
	public String addMonologueText(String monologueText) throws Exception{
		System.out.println("monologueController   addMonologueText" +monologueText);
		monologueService.addMonologueText(monologueText);
		
		return "monologue/monologue";
		
	}
	
	
    
	
	
	
	
}
