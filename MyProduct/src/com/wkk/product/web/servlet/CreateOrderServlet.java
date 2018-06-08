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
//提交信息到第三方
public class CreateOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.封装order对象
		Order order = new Order();
		try {
			BeanUtils.populate(order, request.getParameterMap());
			order.setId(UUID.randomUUID().toString());
			//将用户加入到订单项中
			order.setUser((User)request.getSession().getAttribute("user"));
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		if (order.getReceiverAddress()==null || order.getReceiverName() ==null
				|| order.getReceiverPhone()==null || "".equals(order.getReceiverAddress()) ||
				"".equals(order.getReceiverName())
				|| "".equals(order.getReceiverPhone())) {
			request.setAttribute("order_msg", "订单信息不完整！");
			request.getRequestDispatcher("/order.jsp").forward(request, response);
			return;
		}
		//2.获得session对象中的购物车信息
		Map<Product, String> cart = (Map<Product, String>)request.getSession().getAttribute("cart");
		//3.遍历购物车中的商品，添加到orderItem对象中，同时把多个Item对象添加到list集合中
		List<OrderItem> list = new ArrayList<OrderItem>();
		for (Product p : cart.keySet()) {
			OrderItem oi = new OrderItem();
			oi.setP(p);//购物车的商品
			oi.setOrder(order);//订单
			oi.setBuynum(Integer.parseInt(cart.get(p)));//每种商品的数量
			list.add(oi);//把每个订单项加到集合中
		}
		//4.把集合放入到order对象中
		order.setOrderItems(list);
		//5.调用业务逻辑
		OrderService os = new OrderService();
		try {
			os.addOrder(order);
		} catch (OrderException e) {
			//如果生成订单失败，返回到订单提交页
			request.setAttribute("order_msg", e.getMessage());
			request.getRequestDispatcher("/order.jsp").forward(request, response);
			return;
		}
		//如果生成订单成功，删除购物车
		request.getSession().setAttribute("cart", null);
		//把order编号和订单价格
		request.setAttribute("orderid", order.getId());
		request.setAttribute("money", order.getMoney());
		//6.跳转分发转向
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
