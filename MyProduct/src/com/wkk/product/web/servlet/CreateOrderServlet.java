package com.wkk.product.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.wkk.product.domain.Order;
import com.wkk.product.domain.OrderItem;
import com.wkk.product.domain.Product;
import com.wkk.product.domain.User;
import com.wkk.product.exception.OrderException;
import com.wkk.product.service.OrderService;
//�ύ��Ϣ��������
public class CreateOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.��װorder����
		Order order = new Order();
		try {
			BeanUtils.populate(order, request.getParameterMap());
			order.setId(UUID.randomUUID().toString());
			//���û����뵽��������
			order.setUser((User)request.getSession().getAttribute("user"));
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		if (order.getReceiverAddress()==null || order.getReceiverName() ==null
				|| order.getReceiverPhone()==null || "".equals(order.getReceiverAddress()) ||
				"".equals(order.getReceiverName())
				|| "".equals(order.getReceiverPhone())) {
			request.setAttribute("order_msg", "������Ϣ��������");
			request.getRequestDispatcher("/order.jsp").forward(request, response);
			return;
		}
		//2.���session�����еĹ��ﳵ��Ϣ
		Map<Product, String> cart = (Map<Product, String>)request.getSession().getAttribute("cart");
		//3.�������ﳵ�е���Ʒ����ӵ�orderItem�����У�ͬʱ�Ѷ��Item������ӵ�list������
		List<OrderItem> list = new ArrayList<OrderItem>();
		for (Product p : cart.keySet()) {
			OrderItem oi = new OrderItem();
			oi.setP(p);//���ﳵ����Ʒ
			oi.setOrder(order);//����
			oi.setBuynum(Integer.parseInt(cart.get(p)));//ÿ����Ʒ������
			list.add(oi);//��ÿ��������ӵ�������
		}
		//4.�Ѽ��Ϸ��뵽order������
		order.setOrderItems(list);
		//5.����ҵ���߼�
		OrderService os = new OrderService();
		try {
			os.addOrder(order);
		} catch (OrderException e) {
			//������ɶ���ʧ�ܣ����ص������ύҳ
			request.setAttribute("order_msg", e.getMessage());
			request.getRequestDispatcher("/order.jsp").forward(request, response);
			return;
		}
		//������ɶ����ɹ���ɾ�����ﳵ
		request.getSession().setAttribute("cart", null);
		//��order��źͶ����۸�
		request.setAttribute("orderid", order.getId());
		request.setAttribute("money", order.getMoney());
		//6.��ת�ַ�ת��
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
