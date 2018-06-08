package com.wkk.product.service;

import java.sql.SQLException;
import java.util.List;

import com.wkk.product.dao.OrderDao;
import com.wkk.product.dao.OrderItemDao;
import com.wkk.product.dao.ProductDao;
import com.wkk.product.domain.Order;
import com.wkk.product.domain.OrderItem;
import com.wkk.product.exception.OrderException;
import com.wkk.product.util.ManagerThreadLocal;

public class OrderService {
	OrderDao orderDao = new OrderDao(); 
	OrderItemDao orderItemDao = new OrderItemDao(); 
	ProductDao productDao = new ProductDao();
	//添加订单
	public void addOrder(Order order) throws OrderException {
		try {
			ManagerThreadLocal.startTransacation();
			orderDao.addOrder(order);
			orderItemDao.addOrderItem(order);
			productDao.updateProductNum(order);
			ManagerThreadLocal.commit();
			ManagerThreadLocal.close();
		} catch (SQLException e) {
			e.printStackTrace();
			ManagerThreadLocal.rollback();
			throw new OrderException("提交订单失败！");
		}
	}
	//根据用户id查找订单
	public List<Order> findOrderByUserId(int id) {
		try {
			return orderDao.findOrdersByUserId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//根据orderidc查找订单
	public Order findOrderByOrderId(String orderid) {
		try {
			return orderDao.findOrderByOrderId(orderid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//前台用户根据订单编号删除自己的订单
	public void delOrderByIdWithClient(String orderid) throws OrderException {
		try {
			ManagerThreadLocal.startTransacation();
			List<OrderItem> orderItems = orderDao.findOrderByOrderId(orderid).getOrderItems();
			productDao.updateProductNum(orderItems);
			orderItemDao.delOrderItems(orderid);
			orderDao.delOrderById(orderid);
			ManagerThreadLocal.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			ManagerThreadLocal.rollback();
			throw new OrderException("删除订单失败！");
		}
	}
	//根据订单号修改订单信息
	public void modifyOrderState(String orderid) throws OrderException {
		try {
			orderDao.modifyOrderState(orderid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new OrderException("修改订单状态失败");
		}
	}
	
}
