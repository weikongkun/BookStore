package com.wkk.product.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wkk.product.domain.Product;
import com.wkk.product.exception.ProductException;
import com.wkk.product.service.ProductService;

public class EditProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取修改后编辑的商品信息
		Product product = new Product();
		try {
			BeanUtils.populate(product, request.getParameterMap());
			ProductService ps = new ProductService();
			ps.editProduct(product);
			request.getRequestDispatcher("/listProductServlet").forward(request, response);
		}catch (ProductException e) {
			request.setAttribute("editProduct_msg", e.getMessage());
			request.getRequestDispatcher("/findProductById?id=" + product.getId()).forward(request, response);
		} 
		catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
