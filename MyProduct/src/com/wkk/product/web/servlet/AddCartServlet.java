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
		//��ȡ��ӵ����ﳵ����Ʒ��id
		String id = request.getParameter("id");
		ProductService ps = new ProductService();
		//����id����product
		Product product = ps.findProductById(id);
		//��session�л�ȡ���ﳵ
		int num = 1;
		HttpSession session = request.getSession();
		Map<Product, String>cart = (Map<Product, String>)session.getAttribute("cart");
		//���û�й��ﳵ���½�һ�����ﳵ
		if (cart == null) cart = new HashMap<Product, String>(); 
		//�ж�cart���Ƿ���id����Ʒ,�������������1,û�еĻ�ֱ�Ӽ���
		if (cart.containsKey(product))
			num = Integer.parseInt(cart.get(product)) + 1;
		//��ͼ����빺�ﳵ
		cart.put(product, num + "");
		
		//��cart����Żص�session��
		session.setAttribute("cart", cart);
		response.getWriter().print("<a href='"+request.getContextPath()+"/showProductByPageServlet'>��������</a>��<a href='"+request.getContextPath()+"/cart.jsp'>�鿴���ﳵ</a>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
