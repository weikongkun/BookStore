package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.exception.ProductException;
import com.wkk.product.service.ProductService;

public class DelProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取要删除商品的id
		String id = request.getParameter("id");
		ProductService ps = new ProductService();
		try {
			ps.delProduct(id);
			request.getRequestDispatcher("/listProductServlet").forward(request, response);
		} catch (ProductException e) {
			e.printStackTrace();
			request.setAttribute("delProduct_msg", e.getMessage());
			request.getRequestDispatcher("/listProductServlet").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
