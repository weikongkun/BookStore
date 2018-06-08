package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.exception.OrderException;
import com.wkk.product.service.OrderService;

public class DelOrderByIdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderid = request.getParameter("orderid");
		OrderService os = new OrderService();
		try {
			os.delOrderByIdWithClient(orderid);
			request.getRequestDispatcher("/findOrderByUser").forward(request, response);
		} catch (OrderException e) {
			e.printStackTrace();
			request.getRequestDispatcher("/findOrderByUser").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
