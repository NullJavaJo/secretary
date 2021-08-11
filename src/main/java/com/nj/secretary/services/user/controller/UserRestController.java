package com.nj.secretary.services.user.controller;

import com.nj.secretary.services.alarm.domain.Alarm;
import com.nj.secretary.services.alarm.service.AlarmService;
import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.service.MonologueService;
import com.nj.secretary.services.user.domain.User;
import com.nj.secretary.services.user.service.UserService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@RestController//반환 값이 단순 문자열과 Json이다.
@RequestMapping("/restUser/*")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private TemplateEngine templateEngine;


    @ResponseBody//서버로 보낸 json데이터를 자바 객체로 매핑
    @GetMapping("/idCheck")
    public int idCheck(@RequestParam("userId") String userId) throws Exception{
        int count = userService.idCheck(userId);
        return userService.idCheck(userId);
    }

    @ResponseBody//서버로 보낸 json데이터를 자바 객체로 매핑
    @GetMapping("/emailCheck")
    public int emailCheck(@RequestParam("email") String email,@RequestParam("userId") String userId) throws Exception{
        User user = userService.getUser(userId);
        System.out.print(user);
        if(user == null){
            return 0;
        }else{
            if(user.getEmail().equals(email)){
                return 0;
            }else{
                return 1;
            }
        }

    }

    @ResponseBody
    @GetMapping("loginCheck")
    public int loginCheck(@RequestParam("userId") String userId, @RequestParam("password") String password) throws Exception{
        System.out.println("loginCheck rest시작 id : "+userId + "password : " + password);
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);

        int result = userService.loginCheck(user);
        System.out.println("결과값 : " + result);
        if (result == 0){
            return 0;
        }else{
            return 1;
        }
    }


    //아이디 찾기 : 메일전송
    @PostMapping("findId")
    public String findId(@RequestBody Map<String, Object> paramMap, User user)
            throws Exception {
        System.out.println(paramMap+"paramMap입니다.");
        String userName = (String) paramMap.get("userName");
        String email = (String) paramMap.get("email");
        Context context = new Context();
        System.out.println(userName);
        System.out.println("email이 찍혀요?"+email);
        user.setUserName(userName);
        user.setEmail(email);
        User dbUser = userService.findUserId(user);
        System.out.println("dbUser1"+dbUser);
        if (dbUser==null){
            return "0";
        }
        if (email.equals(dbUser.getEmail())) {
            String userId = dbUser.getUserId();
            System.out.println("낰낰 userId이써여?"+userId);
            try {
                MimeMessage msg = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");

                messageHelper.setFrom("Secretary", "Secretary");
                messageHelper.setSubject("Secretary 아이디찾기 메일입니다.");//메일 제목
                messageHelper.setTo(email);
                context.setVariable("key", userId);
                String template = templateEngine.process("/main/id", context);
                messageHelper.setText(template,true);//메일 내용
//        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail));
                mailSender.send(msg);
            } catch (MessagingException e) {
                System.out.println("MessagingException");
                e.printStackTrace();
            }return "1";
        }else {
            System.out.println("일치하는 정보 없음");
        }
        System.out.println("일치하는 정보 없음");
        return "0";
        //return "user/emailSuccess";
    }
    
    @PostMapping("/findPwd")
    public int findPwd(@RequestBody String userId,  @RequestBody String email,  User user, Model model) throws Exception {
        System.out.println("userController findPwd 찾기 시작");
       System.out.println("findPwd restController"+userId);
        
        //User user01=new User();
        if(user.getUserId().equals(userId) && user.getEmail().equals(email)) {
        	userService.findUserPwd(user.getUserId());
        	//model.addAttribute("user", "0");
        	return 0;
        }else {
        	//model.addAttribute("user", "1");
        	return 1;
        }
        //진행중~~
     
//        System.out.println("findPwd restController"+email);
    	//userService.findUserPwd(userName);
        //return "user/changePwd";
    }

    //아이디 찾기 회원정보 일치여부
    @ResponseBody
    @GetMapping("userInfoCheck")
    public int userInfoCheck(@RequestParam("userName") String userName, @RequestParam("email") String email) throws Exception{
        System.out.println("userInfoCheck rest시작 name : "+userName + "email : " + email);
        User user = new User();
        user.setUserName(userName);
        user.setPassword(email);

        int result = userService.userInfoCheck(user);

        System.out.println("결과값 : " + result);
        if (result == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @PostMapping("kakaoCheck")
    public String kakaoCheck(@RequestBody User user, HttpSession session) throws Exception{
        System.out.println("UserRestController kakaoCheck 시작"+user);
        User user01 = (User)session.getAttribute("user");
        user01 = userService.getUser(user01.getUserId());
        if(user.getEmail().equals(user01.getEmail())){
            return "1"; // 성공
        }else{
            return "0"; // 실패
        }
    }


    @PostMapping("/sendMail")
    @ResponseBody //ajax이후 다시 응답을 보내는게 아니기 때문에 적어줘야함.
    public String SendMail(@RequestParam("email") String mail) throws Exception{
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        Random random = new Random();//난수 생성을 위한 랜덤 클래스
        String key=""; //인증번호
        Context context = new Context();


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);//스크립트에서 보낸 메일을 받을 사용자 이메일 주소

        for (int i=0; i<3; i++){
            int index = random.nextInt(25)+65;//A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex= random.nextInt(9999)+1000;//4자리 랜덤 정수를 생성
        key+=numIndex;
        messageHelper.setFrom("Secretary", "Secretary");
        messageHelper.setSubject("Secretary 인증번호입니다.");//메일 제목
        messageHelper.setTo(mail);
        context.setVariable("key", key);
        String template = templateEngine.process("/main/password", context);
        messageHelper.setText(template,true);//메일 내용
//        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail));
        mailSender.send(msg);
        return key;
    }

    @PostMapping("/CheckCertification")
    @ResponseBody //ajax이후 다시 응답을 보내는게 아니기 때문에 적어줘야함.
    public String SendMail01(String mail) throws Exception{
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true, "UTF-8");
        Random random = new Random();//난수 생성을 위한 랜덤 클래스
        String key=""; //인증번호
        Context context = new Context();


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);//스크립트에서 보낸 메일을 받을 사용자 이메일 주소

        for (int i=0; i<3; i++){
            int index = random.nextInt(25)+65;//A~Z까지 랜덤 알파벳 생성
            key+=(char)index;
        }
        int numIndex= random.nextInt(9999)+1000;//4자리 랜덤 정수를 생성
        key+=numIndex;
        messageHelper.setFrom("Secretary", "Secretary");
        messageHelper.setSubject("Secretary 비밀번호찾기 인증번호입니다.");//메일 제목
        messageHelper.setTo(mail);

        context.setVariable("key", key);
        String template = templateEngine.process("/main/mailTemplate", context);
        messageHelper.setText(template,true);//메일 내용
//        msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail));
        mailSender.send(msg);
        return key;
    }

    @GetMapping("limitUser")
    public List<User> limitUser(Model model){
        System.out.println("admin User start in Restcontroller");
        List<User> blindedUserList = userService.getBlindedUserList();
        System.out.println("blindedUserList : " + blindedUserList);

        model.addAttribute("blindedUserList", blindedUserList);

        return blindedUserList;
    }

    @GetMapping("getLimitedUser")
    public List<User> getLimitedUser(Model model){
        System.out.println("getLimitedUser start in Restcontroller");
        List<User> getLimitedUserList = userService.getLimitedUserList();
        System.out.println("getLimitedUserList : " + getLimitedUserList);

        model.addAttribute("getLimitedUserList", getLimitedUserList);

        return getLimitedUserList;
    }

    @GetMapping("getLimitDateOverUser")
    public List<User> getLimitDateOverUser(Model model){
        System.out.println("getLimitDateOverUser start in Restcontroller");
        List<User> getLimitDateOverUser = userService.getLimitDateOverUser();
        System.out.println("getLimitDateOverUser : " + getLimitDateOverUser);

        model.addAttribute("getLimitDateOverUser", getLimitDateOverUser);

        return getLimitDateOverUser;
    }

    @PostMapping("setShareLimit")
    public int setShareLimit(@RequestBody String id){
        System.out.println("user limit set check : " + id);
        String[] text = id.split("&");
        String userId = text[0].split("=")[1];
        int blindStatus = Integer.parseInt(text[1].split("=")[1]);

        System.out.println("userId : " + userId + "blindStatus : " + blindStatus);

        Map map = new HashMap();
        Alarm alarm = new Alarm();
        if(blindStatus == 2){
             map.put("userId", userId);
             map.put("period", 7);
            alarm.setAlarmText("공유기능이 7일간 제한됬습니다.");
        }else if(blindStatus == 3){
            map.put("userId", userId);
            map.put("period", 30);
            alarm.setAlarmText("공유기능이 30일간 제한됬습니다.");
        }else if(blindStatus == 4){
            map.put("userId", userId);
            map.put("period", 365);
            alarm.setAlarmText("공유기능이 1년간 제한됬습니다.");
        }else if(blindStatus >= 5){
            map.put("userId", userId);
            map.put("period", 3650);
            alarm.setAlarmText("공유기능이 10년간 제한됬습니다.");
        }
        userService.setLimit(map);


        alarm.setUserId(userId);
        alarm.setAlarmType("3");

        alarmService.shareLimitAlarm(alarm);
       //int i = userService.setShareLimit(userId);
        System.out.println("setShareLimit from UserRestController finish");

        return 1;
    }

    @PostMapping("releaseShareLimit")
    public int releaseShareLimit(@RequestBody String id) {
        System.out.println("user limit release check : " + id);
        String userId = id.split("=")[1];
        System.out.println(userId);

        userService.releaseShareLimit(userId);
        System.out.println("releaseShareLimit from UserRestController finish");

        return 1;
    }
    
    @PostMapping("pwdCheck")
    public int pwdCheck(@RequestBody User user, HttpSession session, Model model) throws Exception{
    	System.out.println("UserRestController pwdCheck 시작"+user);
    	User user01 = (User)session.getAttribute("user");
        user01 = userService.getUser(user01.getUserId());
    	if(user.getPassword().equals(user01.getPassword())){
    	    return 0;
        }else{
    	    return 1;
        }
    }
    

    @PostMapping("changePassword")
    public String changePassword(@RequestBody User user) throws Exception{
    	userService.changePassword(user);
    	System.out.println("changePassword 확인::"+user);
    	
    	return "변경이 완료 되었습니다.";
    }
    
    
    @PostMapping("withdrawal")
    public String withdrawal(@RequestBody User user) throws Exception{
    	
    	
    	System.out.println("withdrawal restController 시작합니다");
        System.out.println(user);
    	userService.withdrawal(user);

    	return "";
    }
    
    
    @PostMapping("withdrawalReason")
    public String withdrawalReason(@RequestBody User user,HttpSession session) throws Exception {
    	//user.setWithdrawalReasonId(withdrawalReasonId);
    	userService.withdrawalReason(user);
    	session.invalidate();
    	System.out.println("restController에서 탈퇴사유 확이이이인::::"+user);
    	return "탈퇴가 완료 되었습니다.";
    	
    }



}
