package com.wkk.product.service;

import java.sql.SQLException;
import com.wkk.product.dao.UserDao;
import com.wkk.product.domain.User;
import com.wkk.product.exception.UserException;
import com.wkk.product.util.SendJMail;

public class UserService {
	UserDao ud = new UserDao();
	//ע���û������ͼ�����
	public void regist(User user) throws UserException {
		try {
			ud.addUser(user);//�û�ע��
			String email = "ע��ɹ�����<a href='http://www.product.com/active?activeCode="
					+ user.getActiveCode()+ "'>����</a>���½";
			SendJMail.sendMail(user.getEmail(), email);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("ע��ʧ�ܣ�");
		}
	}
	
	public void activeUser(String activeCode) throws UserException {
		//���ݼ���������û�
		try {	
			User user = ud.findUserByActiveCode(activeCode);
			//����鵽�û�������м���
			if (user != null) {
				ud.activeCode(activeCode);
				return;
			}
			throw new UserException("����ʧ�ܣ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new UserException("����ʧ�ܣ�");
		}
	}
	//ע��
	public User login(String username, String password) throws UserException {
		User user = null;
		try {
			user =  ud.findUserByUserNameAndPassword(username, password);
			if (user == null) {
				throw new UserException("�û������������");
			}
			if (user.getState() == 0)
				throw new UserException("�û�δ���");
		} catch (SQLException e) {
			throw new UserException("�û������������");
		}
		return user;
	}
	//����id�����û�
	public User findUserById(String id) throws UserException {
		User user = null;
		try {
			user = ud.findUserById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("�û�����ʧ�ܣ�");
		}
		return user;
	}
	//�޸��û���Ϣ
	public void modifyUser(User user) throws UserException {
		try {
			ud.modifyUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserException("�޸�ʧ�ܣ�");
		}
	}
}
