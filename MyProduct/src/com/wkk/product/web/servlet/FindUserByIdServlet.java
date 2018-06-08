package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.User;
import com.wkk.product.exception.UserException;
import com.wkk.product.service.UserService;

public class FindUserByIdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取id
		String id = request.getParameter("id");
		//调用业务逻辑
		UserService us = new UserService();
		try {
			User user = us.findUserById(id);
			request.setAttribute("u", user);
			request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			response.getWriter().write(e.getMessage());
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
