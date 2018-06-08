package com.wkk.product.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.PageBean;
import com.wkk.product.service.ProductService;

public class ShowProductByPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取导航的类别
		String category = request.getParameter("category");
		if (category == null) category = "";
		
		//初始化每页的商品数
		int pageSize = 4;
		int currentPage = 1;//当前页
		String currpage = request.getParameter("currentPage");//从上一页或下一页得出当前页
		//第一次访问资源时可能为null
		if (currpage != null && !"".equals(currpage)) 
			currentPage = Integer.parseInt(currpage);
		ProductService ps = new ProductService();
		PageBean pb = ps.findProductByPage(pageSize, currentPage, category);
		request.setAttribute("pb", pb);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
