package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.Product;
import com.wkk.product.service.ProductService;

public class FindBookInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ªÒ»°Id
		String id = request.getParameter("id");
		ProductService ps = new ProductService();
		Product product = ps.findProductById(id);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
