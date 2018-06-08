package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.User;

public class MyAccountServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��sessionȡ��user����
		User user = (User)request.getSession().getAttribute("user");
		//�ж�user�Ƿ�Ϊnull
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}else {
			//��ͨ�û�ҳ��
			String path = "/myAccount.jsp";
			//����Աҳ��
			if ("admin".equals(user.getRole()))
				path = "/admin/login/home.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
