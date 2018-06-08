package com.wkk.product.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wkk.product.domain.Order;
import com.wkk.product.domain.OrderItem;
import com.wkk.product.util.C3P0Util;
import com.wkk.product.util.ManagerThreadLocal;

public class OrderItemDao {
	//添加订单项
	public void addOrderItem(Order order) throws SQLException {
		List<OrderItem> orderItems = order.getOrderItems();//得到所有订单项集合
		QueryRunner qr = new QueryRunner();
		Object[][] params = new Object[order.getOrderItems().size()][];
		for (int i = 0; i < params.length; i++) {
			//数组中的第一个参数代表主单id，第二个代表商品id，第三个代表商品购买数量
			params[i] = new Object[]{order.getId(),orderItems.get(i).getP().getId(), 
					orderItems.get(i).getBuynum()};
		}
		qr.batch(ManagerThreadLocal.getConnection(),"insert into orderitem values(?,?,?)", params);
	}
	
	
	
	//根据订单号删除相应的订单项
	public void delOrderItems(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(ManagerThreadLocal.getConnection(), "delete from orderitem where order_id=?",orderid);
	}
}
