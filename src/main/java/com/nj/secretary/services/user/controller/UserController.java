package com.nj.secretary.services.user.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nj.secretary.services.alarm.service.AlarmService;
import com.nj.secretary.services.monologue.domain.Monologue;
import com.nj.secretary.services.monologue.domain.Question;
import com.nj.secretary.services.monologue.service.MonologueService;
import com.nj.secretary.services.user.impl.UserDAOImpl;
import org.json.JSONObject;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.nj.secretary.services.user.domain.User;
import com.nj.secretary.services.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private MonologueService monologueService;


    @GetMapping("/signUp")
    public String signUp() throws Exception {

        return "user/addUser";
    }


    @PostMapping("signUp")
    public String signUp01(@Valid User user, Errors errors, Model model) throws Exception {
       /* if (errors.hasErrors()){
            //회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("user",user);

            //유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()){
                model.addAttribute(key, validatorResult.get(key));
            }
        }*/
        System.out.println(user);
        userService.addUser(user);
        return "user/login";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model) throws Exception {
        if (session.getAttribute("user") != null) {
            User login = (User) session.getAttribute("user");
            Monologue monologue = new Monologue();
            monologue.setUserId(login.getUserId());
            if (login.getRoles().trim().equals("ADMIN")) {
                // 알람 시작
                int alarmCount = alarmService.alarmCount(login.getUserId());
                model.addAttribute("count", alarmCount);
                System.out.println("count check : " + alarmCount);
                // 알람 끝
                model.addAttribute("user", login);
                return "user/adminAfterLogin";
            } else {
                if (monologueService.checkMonologue(login.getUserId()) > 0) {
                    // 알람 시작
                    int alarmCount = alarmService.alarmCount(login.getUserId());
                    model.addAttribute("count", alarmCount);
                    System.out.println("count check : " + alarmCount);
                    // 알람 끝
                    System.out.println("Already wrote Monologue");
                    Question question = new Question();
                    model.addAttribute("user", login);
                    model.addAttribute("question", question);
                    return "user/afterLogin";
                } else {
                    Random random = new Random();
                    System.out.println("didn't write Monologue");
                    while (true) {
                        int ran = random.nextInt(100) + 1;
                        monologue.setQuestionId(ran);
                        if (monologueService.randomCheck(monologue) == 0) {
                            // 알람 시작
                            int alarmCount = alarmService.alarmCount(login.getUserId());
                            model.addAttribute("count", alarmCount);
                            System.out.println("count check : " + alarmCount);
                            // 알람 끝
                            model.addAttribute("question", monologueService.getQuestionText(ran));
                            model.addAttribute("user", login);
                            return "user/afterLogin";
                        }
                        if (monologueService.randomCheck(monologue) == 100) {
                            // 알람 시작
                            int alarmCount = alarmService.alarmCount(login.getUserId());
                            model.addAttribute("count", alarmCount);
                            System.out.println("count check : " + alarmCount);
                            // 알람 끝
                            Question question = new Question();
                            model.addAttribute("user", login);
                            model.addAttribute("question", question);
                            model.addAttribute("user", login);
                            return "user/afterLogin";
                        }
                    }
                }
            }
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String login01(User user, HttpSession session, Model model, @ModelAttribute Monologue monologue) throws Exception {

        User dbUser = userService.getUser(user.getUserId());
        Random random = new Random();
        System.out.println(user + " : " + monologue);
        if(userService.getUser(user.getUserId())==null){ //탈퇴아이디 로그인 x
            model.addAttribute("withdrawal","0");
            return "/user/login";
        }
        System.out.println("count : " + monologueService.checkMonologue(dbUser.getUserId()));
        if (user.getPassword().equals(dbUser.getPassword())) {
            session.setAttribute("user", dbUser);
            if (dbUser.getRoles().trim().equals("ADMIN")) {
                // 알람 시작
                int alarmCount = alarmService.alarmCount(dbUser.getUserId());
                model.addAttribute("count", alarmCount);
                System.out.println("count check : " + alarmCount);
                // 알람 끝
                model.addAttribute("user", dbUser);
                return "user/adminAfterLogin";
            } else {

                if (monologueService.checkMonologue(dbUser.getUserId()) > 0) {
                    // 알람 시작
                    int alarmCount = alarmService.alarmCount(dbUser.getUserId());
                    model.addAttribute("count", alarmCount);
                    System.out.println("count check : " + alarmCount);
                    // 알람 끝
                    System.out.println("Already wrote Monologue");
                    Question question = new Question();
                    model.addAttribute("user", dbUser);
                    model.addAttribute("question", question);
                    return "user/afterLogin";
                } else {
                    System.out.println("didn't write Monologue");
                    while (true) {
                        int ran = random.nextInt(100) + 1;
                        monologue.setQuestionId(ran);
                        if (monologueService.randomCheck(monologue) == 0) {
                            // 알람 시작
                            int alarmCount = alarmService.alarmCount(dbUser.getUserId());
                            model.addAttribute("count", alarmCount);
                            System.out.println("count check : " + alarmCount);
                            // 알람 끝
                            model.addAttribute("question", monologueService.getQuestionText(ran));
                            model.addAttribute("user", dbUser);
                            return "user/afterLogin";
                        }
                        if (monologueService.randomCheck(monologue) == 100) {
                            // 알람 시작
                            int alarmCount = alarmService.alarmCount(dbUser.getUserId());
                            model.addAttribute("count", alarmCount);
                            System.out.println("count check : " + alarmCount);
                            // 알람 끝
                            Question question = new Question();
                            model.addAttribute("user", dbUser);
                            model.addAttribute("question", question);
                            model.addAttribute("user", dbUser);
                            return "user/afterLogin";
                        }
                    }
                }
            }
        }
        return "/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception {

        if (session.getAttribute("accessToken") != null) {
            String reqURL = "https://kapi.kakao.com/v1/user/unlink";
            String access_Token = (String) session.getAttribute("accessToken");

            try {
                URL url = new URL(reqURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");


                //    요청에 필요한 Header에 포함될 내용
                conn.setRequestProperty("Authorization", "Bearer " + access_Token);

                int responseCode = conn.getResponseCode();
                System.out.println("responseCode02 : " + responseCode);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }
                System.out.println("response body : " + result);

                JSONObject jsonObject = new JSONObject(result);
                System.out.println(jsonObject);

                System.out.println(jsonObject.get("id"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        session.invalidate();
        return "user/login";
    }


    @GetMapping("/findPwd")
    public String findPwd() throws Exception {
        return "user/findPwd";
    }

//    @PostMapping("/findPwd")
//    public String findPwd01(String userId, String email,@ModelAttribute User user, Model model) throws Exception {
//        System.out.println("userController findPwd 찾기 시작");
//        //User user01=new User();
//        userService.findUserPwd(user.getUserId());
//        if(user.getUserId().equals(userId)) {
//        	model.addAttribute("user", "0");
//        }else {
//        	model.addAttribute("user", "1");
//        }
//        
//        if(user.getEmail().equals(email)) {
//        	model.addAttribute("user", "3");
//        }else {
//        	model.addAttribute("user", "4");
//        }
//    	
//    	//userService.findUserPwd(userName);
//        return "user/changePwd";
//    }


    @GetMapping("/kakaologin")
    public String kakaoGetToken(@RequestParam("code") String code, Model model, HttpSession session) throws IOException {

        String reqURL = "https://kauth.kakao.com/oauth/token";
        System.out.println("code" + code);

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=aceb8c76e94a93fae1034661828f1e34");
            sb.append("&redirect_uri=http://localhost:9090/user/kakaologin");
            sb.append("&code=" + code);//로그인 과정 중에 얻은 code
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            //    결과 코드가 401일시 에러
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            br.close();
            bw.close();

            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject);
            System.out.println(jsonObject.get("access_token"));
            System.out.println(jsonObject.get("refresh_token"));
            model.addAttribute("accessToken", jsonObject.get("access_token"));
            HashMap map = getUserInfo((String) jsonObject.get("access_token"));
            if ((int) map.get("check") == 0) {

                session.setAttribute("accessToken",jsonObject.get("access_token"));

                JSONObject abc = (JSONObject) map.get("userInfo");
                JSONObject nick = (JSONObject)abc.get("kakao_account");
                JSONObject nickname = (JSONObject) nick.get("profile");
                model.addAttribute("userInfo", map.get("userInfo"));
                model.addAttribute("userId",abc.get("id"));
                System.out.println(nick);
                model.addAttribute("nickname",nickname.get("nickname"));
                model.addAttribute("email",nick.get("email"));
                return "user/kakao";
            } else {

                session.setAttribute("accessToken",jsonObject.get("access_token"));

                JSONObject abc = (JSONObject) map.get("userInfo");
                User dbUser = userService.getUser(""+abc.get("id"));
                session.setAttribute("user", dbUser);
                if(monologueService.checkMonologue(dbUser.getUserId())>0){
                    System.out.println("Already wrote Monologue");
                    Question question = new Question();
                    model.addAttribute("user", dbUser);
                    model.addAttribute("question", question);
                    return "user/afterLogin";
                }else{
                    Random random = new Random();
                    System.out.println(dbUser);
                    Monologue monologue = new Monologue();
                    monologue.setUserId(dbUser.getUserId());
                    System.out.println("didn't write Monologue");
                    while (true) {
                        int ran = random.nextInt(100) + 1;
                        monologue.setQuestionId(ran);
                        if (monologueService.randomCheck(monologue) == 0) {
                            model.addAttribute("question", monologueService.getQuestionText(ran));
                            model.addAttribute("user", dbUser);
                            return "user/afterLogin";
                        }
                        if (monologueService.randomCheck(monologue) == 100) {
                            model.addAttribute("user", dbUser);
                            Question question = new Question();
                            model.addAttribute("user", dbUser);
                            model.addAttribute("question", question);
                            return "user/afterLogin";
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("변환에 실패");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public HashMap<String, Object> getUserInfo(String access_Token) throws Exception {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode02 : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject);

            System.out.println(jsonObject.get("id"));
            int dbUser = userService.kakaoLogin(Integer.toString((Integer) jsonObject.get("id")));
            userInfo.put("userInfo", jsonObject);
            System.out.println("userInfo 있나욤?" + jsonObject);
            userInfo.put("check", dbUser);
            System.out.println("dbUser안에 아뒤있나욤 1 나오면 있는거야?" + dbUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    @PostMapping("/kakaoSignUp")
    public String kakaoSignUp(User user,HttpSession session, Model model) throws Exception {
        userService.addKakaoUser(user);
        session.setAttribute("user",user);
        Monologue monologue = new Monologue();
        monologue.setUserId(user.getUserId());
        monologue.setQuestionId(7);
        model.addAttribute("question", monologueService.getQuestionText(7));

        return "user/afterLogin";
    }


    @GetMapping("/getUser")
    public String getUser(String userId, Model model, HttpSession session) throws Exception {
        if(session.getAttribute("user")==null){
            return "user/login";
        }

        System.out.println("getUser 내정보보기 Controller 시작");

        //session.setAttribute("userId", userId);
        User user = (User)session.getAttribute("user");
        //User user = userService.getUser((session.getAttribute("userId")).toString());

        model.addAttribute("user", user);
        if (user.getKakao()==0){
            return "user/getUser";
        }else{
            return "user/kakaoProfile";
        }
    }
    
    @PostMapping("getUser")
    public String getUser(HttpSession session,Model model) throws Exception{
        User user = (User)session.getAttribute("user");
    	User user01 = userService.getUser(user.getUserId());
        model.addAttribute("user", user01);
    	return "user/getUser";
    }

    @GetMapping("/adminUser")
    public String adminUser(Model model) throws Exception {
        System.out.println("admin User start in controller");
        List<User> blindedUserList = userService.getBlindedUserList();
        System.out.println("blindedUserList : " + blindedUserList);

        model.addAttribute("blindedUserList", blindedUserList);

        return "user/adminUser";
    }

    @PostMapping("updateUserView")
    public String updateuser(HttpSession session, Model model) throws Exception{
        System.out.println("updateUser controller 시작 합니다");
        User user = (User)session.getAttribute("user");
        User user01 = userService.getUser(user.getUserId());
        model.addAttribute("user",user01);
        if (user.getKakao()==0){
            return "user/updateUser";
        }else{
            return "user/kakaoUpdate";
        }
    }


    @PostMapping("updateUser")
    public String updateuser(@RequestParam("userId") String userId, User user, Model model,HttpSession session) throws Exception{
    	System.out.println("updateUser controller 시작 합니다");
        User user4 = (User)session.getAttribute("user");
    	if(user4.getKakao()==1){
    	    user.setEmail(user4.getEmail());
        }
    	userService.updateUser(user);
    	System.out.println("updateUser controller 시작 합니다2");
    	System.out.println("updateUser 확인::"+user);
        User user01=userService.getUser(userId);
        model.addAttribute("user",user01);
        session.setAttribute("user",user01);
        if (user01.getKakao()==0){
            return "user/getUser";
        }else{
            return "user/kakaoProfile";
        }
    	//return "user/getUser";
    }

    @GetMapping("getWithdrawalReasonList")
    public String getWithdrawalReasonList(Model model) {
        System.out.println("getWithdrawalReasonList in userController start");
        List<User> list = userService.getWithdrawalReasonList();
        System.out.println("getWithdrawalReasonList : " + list);
        model.addAttribute("list", list);

        return "user/getWithdrawalReasonList";

    }

    
    @PostMapping("withdrawal")
    public String withdrawal(User user) throws Exception{ 
    	System.out.println("withdrawal controller 시작 합니다");
    	
    	return "user/withdrawal";

    }

    @GetMapping("getAllUser")
    public String getAllUser(HttpSession session,Model model) throws Exception{
        User user = (User)session.getAttribute("user");
        User user01 = userService.getUser(user.getUserId());
        System.out.println(user +" : "+user01);
        if(user01.getRoles().equals("ADMIN")){
            List list = userService.getAllUser();
            System.out.println("ALLUSER : "+list);
            model.addAttribute("user",list);
            return "user/getAllUser";
        }else{
            return "user/login";
        }

    }
    
}
