package com.wkk.product.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wkk.product.domain.Product;
import com.wkk.product.service.ProductService;

public class ChangeNumServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取更改购物车后商品id和数量
		String id = request.getParameter("id");
		String num = request.getParameter("num");
		
		//获取session和购物车
		HttpSession session = request.getSession();
		Map<Product, String> cart = (Map<Product, String>)session.getAttribute("cart");
		
		//根据商品id查找Product
		ProductService ps = new ProductService();
		Product product = ps.findProductById(id);
		
		//判断num是否为0
		if (Integer.parseInt(num) == 0) {
			//如果为0,则将该商品从购物车中删去
			cart.remove(product);
		}else {
			//如果不为0，则改变数量
			cart.put(product, num);
		}
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
