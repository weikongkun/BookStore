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
		//处理验证码
		String ckcode = request.getParameter("ckcode");
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
		//如果验证码不一致，调回注册页面
		if (ckcode.isEmpty() || !ckcode.equals(checkcode_session)) {
			request.setAttribute("ckcode_msg", "验证码错误!");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return ;
		}
		//获取表达数据
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			user.setActiveCode(UUID.randomUUID().toString());
		//调用业务逻辑
			UserService us = new UserService();
			us.regist(user);
		//分发转向
			//request.getSession().setAttribute("user", user);//把用户信息封装搭配session中
			//要求用户激活后才能登陆，所以不能把用户信息保存session中,在service中实现
			request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
		} catch (UserException e) {
		//分发转向
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
