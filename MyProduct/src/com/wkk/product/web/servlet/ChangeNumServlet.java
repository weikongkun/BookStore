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
		//��ȡ���Ĺ��ﳵ����Ʒid������
		String id = request.getParameter("id");
		String num = request.getParameter("num");
		
		//��ȡsession�͹��ﳵ
		HttpSession session = request.getSession();
		Map<Product, String> cart = (Map<Product, String>)session.getAttribute("cart");
		
		//������Ʒid����Product
		ProductService ps = new ProductService();
		Product product = ps.findProductById(id);
		
		//�ж�num�Ƿ�Ϊ0
		if (Integer.parseInt(num) == 0) {
			//���Ϊ0,�򽫸���Ʒ�ӹ��ﳵ��ɾȥ
			cart.remove(product);
		}else {
			//�����Ϊ0����ı�����
			cart.put(product, num);
		}
		request.getRequestDispatcher("/cart.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
