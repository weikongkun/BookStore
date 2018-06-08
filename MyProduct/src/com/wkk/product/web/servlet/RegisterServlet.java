package com.wkk.product.web.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wkk.product.domain.User;
import com.wkk.product.exception.UserException;
import com.wkk.product.service.UserService;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//������֤��
		String ckcode = request.getParameter("ckcode");
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		//�����֤�벻һ�£�����ע��ҳ��
		if (ckcode.isEmpty() || !ckcode.equals(checkcode_session)) {
			request.setAttribute("ckcode_msg", "��֤�����!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return ;
		}
		//��ȡ�������
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			user.setActiveCode(UUID.randomUUID().toString());
		//����ҵ���߼�
			UserService us = new UserService();
			us.regist(user);
		//�ַ�ת��
			//request.getSession().setAttribute("user", user);//���û���Ϣ��װ����session��
			//Ҫ���û��������ܵ�½�����Բ��ܰ��û���Ϣ����session��,��service��ʵ��
			request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
		} catch (UserException e) {
		//�ַ�ת��
			request.setAttribute("user_msg", e.getMessage());
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		} catch (Exception e) {
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
