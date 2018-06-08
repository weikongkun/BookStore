package com.wkk.product.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.Order;
import com.wkk.product.domain.User;
import com.wkk.product.service.OrderService;

public class FindOrderByUser extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("user");
		OrderService os = new OrderService();
		List<Order> orders = os.findOrderByUserId(user.getId());
		request.setAttribute("orders", orders);
		request.setAttribute("size", orders.size());
		request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
