package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.User;
import com.wkk.product.exception.UserException;
import com.wkk.product.service.UserService;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表达数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserService us = new UserService();
		try {
			String path = "/index.jsp";
			User user = us.login(username, password);
			if ("admin".equals(user.getRole()))
				path = "/admin/login/home.jsp";
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher(path).forward(request, response);
		} catch (UserException e) {
			e.printStackTrace();
			request.setAttribute("user_msg", e.getMessage());
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
