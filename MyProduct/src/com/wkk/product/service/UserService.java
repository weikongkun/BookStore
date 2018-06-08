package com.wkk.product.service;

import java.sql.SQLException;
import com.wkk.product.dao.UserDao;
import com.wkk.product.domain.User;
import com.wkk.product.exception.UserException;
import com.wkk.product.util.SendJMail;

public class UserService {
	UserDao ud = new UserDao();
	//注册用户并发送激活码
	public void regist(User user) throws UserException {
		try {
			ud.addUser(user);//用户注册
			String email = "注册成功，请<a href='http://www.product.com/active?activeCode="
					+ user.getActiveCode()+ "'>激活</a>后登陆";
			SendJMail.sendMail(user.getEmail(), email);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("注册失败！");
		}
	}
	
	public void activeUser(String activeCode) throws UserException {
		//根据激活码查找用户
		try {	
			User user = ud.findUserByActiveCode(activeCode);
			//如果查到用户，则进行激活
			if (user != null) {
				ud.activeCode(activeCode);
				return;
			}
			throw new UserException("激活失败！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new UserException("激活失败！");
		}
	}
	//注册
	public User login(String username, String password) throws UserException {
		User user = null;
		try {
			user =  ud.findUserByUserNameAndPassword(username, password);
			if (user == null) {
				throw new UserException("用户名或密码错误！");
			}
			if (user.getState() == 0)
				throw new UserException("用户未激活！");
		} catch (SQLException e) {
			throw new UserException("用户名或密码错误！");
		}
		return user;
	}
	//根据id查找用户
	public User findUserById(String id) throws UserException {
		User user = null;
		try {
			user = ud.findUserById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("用户查找失败！");
		}
		return user;
	}
	//修改用户信息
	public void modifyUser(User user) throws UserException {
		try {
			ud.modifyUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserException("修改失败！");
		}
	}
}
