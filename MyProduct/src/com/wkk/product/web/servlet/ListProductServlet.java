package com.wkk.product.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.Product;
import com.wkk.product.service.ProductService;

public class ListProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取查找的集合
		ProductService ps = new ProductService();
		List<Product> list = ps.listAll();
		request.setAttribute("productList", list);
		request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
