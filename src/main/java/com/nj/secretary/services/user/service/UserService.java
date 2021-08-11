package com.nj.secretary.services.user.service;

import com.nj.secretary.services.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Service Layer단에서 세분화된 비즈니스로직을 처리하는 객체
 */

@Mapper
@Transactional(readOnly = true)
public interface UserService{

//    public Map<String, String> validateHandling(Errors errors);

    @Transactional
    public void addUser(User user) throws Exception;

    //Select One
    public User getUser(String userId) throws Exception;


    public User findUserId(User user) throws Exception;


    public int userInfoCheck(User user) throws Exception;


    public int findUserPwd(String userId) throws Exception;


    public int loginCheck(User user) throws Exception;


    public int kakaoLogin(String userId) throws Exception;


    public void settingUser() throws Exception;

    //Select List
    public List<User> getUserList() throws Exception;

    //Update
    public void updateUser(User user) throws Exception;

    //Delete
    public int deleteUser() throws Exception;

    //idCheck
    public int idCheck(String userId) throws Exception;

    public int emailCheck(String email) throws Exception;
    
    //탈퇴
    public void withdrawal(User user) throws Exception;
    
    //탈퇴사유
    public void withdrawalReason(User user) throws Exception;

    //비번 체크
    public User pwdCheck(User user) throws Exception;
    
    //비번 변경
    public void changePassword(User user) throws Exception;

    //adminUser
    public List<User> getBlindedUserList();

    public List<User> getLimitedUserList();

    public List<User> getLimitDateOverUser();

    public int setLimit(Map map);

    public int releaseShareLimit(String userId);

    public List<User> getWithdrawalReasonList();

    void addKakaoUser(User user) throws Exception;

    public List<User> getAllUser() throws Exception;
}
