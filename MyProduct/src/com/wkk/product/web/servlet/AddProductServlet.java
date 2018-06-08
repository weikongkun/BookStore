package com.wkk.product.web.servlet;

import java.awt.print.Book;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wkk.product.domain.Product;
import com.wkk.product.exception.ProductException;
import com.wkk.product.service.ProductService;

public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表达数据
		Product product = new Product();
		try {
			BeanUtils.populate(product, request.getParameterMap());
			product.setId(UUID.randomUUID().toString());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		ProductService ps = new ProductService();
		try {
			ps.addProduct(product);
			request.setAttribute("addProduct_msg", "商品添加成功！");
			request.getRequestDispatcher("/listProductServlet").forward(request, response);
		} catch (ProductException e) {
			e.printStackTrace();
			request.setAttribute("addProduct_msg", e.getMessage());
			request.getRequestDispatcher("/admin/products/add.jsp").forward(request, response);
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
