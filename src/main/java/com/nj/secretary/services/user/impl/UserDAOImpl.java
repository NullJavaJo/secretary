package com.nj.secretary.services.user.impl;

import com.nj.secretary.services.user.domain.User;
import com.nj.secretary.services.user.repository.UserDAO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("UserDAOImpl")
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void addUser(User user) throws Exception {
        System.out.println("addUser UserDAOImpl start");
        sqlSession.insert("UserMapper.addUser",user);
    }

    @Override
    public User getUser(String userId) throws Exception {

        return sqlSession.selectOne("UserMapper.getUser",userId);
    }

    @Override
    public User findUserId(User user) throws Exception {
        return sqlSession.selectOne("UserMapper.findUserId", user);
    }

    @Override
    public int userInfoCheck(User user) throws Exception {
        return sqlSession.selectOne("UserMapper.userInfoCheck", user);
    }

    @Override
    public int findUserPwd(String userId) throws Exception {
    	return sqlSession.selectOne("UserMapper.findPwd", userId);
    }

    @Override
    public int loginCheck(User user) {
        int count = sqlSession.selectOne("UserMapper.loginCheck", user);

        return count;
    }

    @Override
    public int kakaoLogin(String userId) throws Exception {
        return sqlSession.selectOne("UserMapper.kakaoLogin", userId);
    }

    @Override
    public int idCheck(String userId) throws Exception {
        return sqlSession.selectOne("UserMapper.idCheck",userId);
    }

    @Override
    public int emailCheck(String email) throws Exception {
        return sqlSession.selectOne("UserMapper.emailCheck",email);
    }

    @Override
    public void settingUser() throws Exception {

    }

    @Override
    public List<User> getUserList() throws Exception {
        return null;
    }

    @Override
    public void updateUser(User user) throws Exception {
    	sqlSession.update("UserMapper.updateUser", user);
    }

    @Override
    public int deleteUser() throws Exception {
        return 0;
    }

    @Override
    public List<User> getBlindedUserList() {

        return sqlSession.selectList("UserMapper.getBlindedUserList");
    }


    @Override
    public List<User> getLimitedUserList() {
        return sqlSession.selectList("UserMapper.getLimitedUserList");
    }

    @Override
    public List<User> getLimitDateOverUser() {
        return sqlSession.selectList("UserMapper.getLimitDateOverUser");
    }


    @Override
    public int setLimit(Map map) {

        sqlSession.update("UserMapper.setLimit", map);
        return 1;
    }

    @Override
    public int releaseShareLimit(String userId) {

        sqlSession.update("UserMapper.releaseShareLimit", userId);
        return 1;
    }


    @Override
    public List<User> getWithdrawalReasonList() {
        return sqlSession.selectList("UserMapper.getWithdrawalReasonList");
    }

    @Override
    public void addKakaoUser(User user) throws Exception {
        sqlSession.insert("UserMapper.addKakaoUser",user);
    }

    @Override
    public List<User> getAllUser() throws Exception {
        return sqlSession.selectList("UserMapper.getAllUser");
    }

    @Override
	public void withdrawal(User user) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("UserMapper.withdrawal", user);
	}

	@Override
	public void withdrawalReason(User user) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert("UserMapper.withdrawalReason", user);
		
	}

	@Override
	public void changePassword(User user) throws Exception {
		sqlSession.update("UserMapper.changePassword", user);
		
	}

	@Override
	public User pwdCheck(User user) throws Exception {
		return sqlSession.selectOne("UserMapper.pwdCheck", user);
	}
}
