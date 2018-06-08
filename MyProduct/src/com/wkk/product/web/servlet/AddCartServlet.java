package com.wkk.product.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wkk.product.domain.Product;
import com.wkk.product.service.ProductService;

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取添加到购物车的商品的id
		String id = request.getParameter("id");
		ProductService ps = new ProductService();
		//根据id查找product
		Product product = ps.findProductById(id);
		//从session中获取购物车
		int num = 1;
		HttpSession session = request.getSession();
		Map<Product, String>cart = (Map<Product, String>)session.getAttribute("cart");
		//如果没有购物车，新建一个购物车
		if (cart == null) cart = new HashMap<Product, String>(); 
		//判断cart中是否有id的商品,如果有则数量加1,没有的话直接加入
		if (cart.containsKey(product))
			num = Integer.parseInt(cart.get(product)) + 1;
		//将图书放入购物车
		cart.put(product, num + "");
		
		//将cart对象放回到session中
		session.setAttribute("cart", cart);
		response.getWriter().print("<a href='"+request.getContextPath()+"/showProductByPageServlet'>继续购物</a>，<a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
