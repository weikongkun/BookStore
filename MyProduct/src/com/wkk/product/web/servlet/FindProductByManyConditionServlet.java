package com.wkk.product.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wkk.product.domain.Product;
import com.wkk.product.exception.ProductException;
import com.wkk.product.service.ProductService;

public class FindProductByManyConditionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单信息
		String id = request.getParameter("id");
		String category = request.getParameter("category");
		String name = request.getParameter("name");
		String minprice = request.getParameter("minprice");
		String maxprice = request.getParameter("maxprice");
		ProductService ps = new ProductService();
		List<Product> list = null;
		try {
			list = ps.findProductByManyCondition(id, category, name, minprice, maxprice);
			request.setAttribute("productList", list);
		} catch (ProductException e) {
			e.printStackTrace();
			request.setAttribute("findProductByManyCondition_msg", e.getMessage());
			
		} finally {
			request.setAttribute("findId", id);
			request.setAttribute("findCategory", category);
			request.setAttribute("findName", name);
			request.setAttribute("findMinprice", minprice);
			request.setAttribute("findMaxprice", maxprice);
			request.getRequestDispatcher("/admin/products/list.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
