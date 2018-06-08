package com.wkk.product.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wkk.product.domain.User;
import com.wkk.product.exception.UserException;
import com.wkk.product.service.UserService;

public class ModifyUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表达数据
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			//调用业务逻辑
			UserService us = new UserService();
			us.modifyUser(user);
			request.getSession().invalidate();//相当于注销用户
			response.sendRedirect(request.getContextPath() + "/modifyUserInfoSuccess.jsp");
		} catch (UserException e) {
			response.getWriter().write(e.getMessage());
		}
		catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
