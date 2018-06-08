package com.wkk.product.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.wkk.product.domain.Order;
import com.wkk.product.domain.OrderItem;
import com.wkk.product.domain.Product;
import com.wkk.product.util.C3P0Util;
import com.wkk.product.util.ManagerThreadLocal;

public class OrderDao {
	//��Ӷ���
	public void addOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(ManagerThreadLocal.getConnection(),sql,order.getId(),order.getMoney(),order.getReceiverAddress(),order.getReceiverName(),
				order.getReceiverPhone(),order.getPaystate(),order.getOrdertime(),order.getUser().getId());
	}
	//����userID���Ҷ���
	public List<Order> findOrdersByUserId(int id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from orders where user_id=?", new BeanListHandler<Order>(Order.class), id);
	}
	//����orderid���Ҷ���
	public Order findOrderByOrderId(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		//�õ�����
		Order order = qr.query("select * from orders where id=?", new BeanHandler<Order>(Order.class),orderid);
//		//�õ���ǰ�����е����ж�������
//		List<OrderItem> orderItems = qr.query("select * from orderItem where order_id=?", new BeanListHandler<OrderItem>(OrderItem.class),orderid);
//		//�õ����ж������е���Ʒ��Ϣ
//		List<Product> products = qr.query("select * from products where id in (select product_id from orderitem where order_id=?)", new BeanListHandler<Product>(Product.class), orderid);
//		
		List<OrderItem> orderItems = qr.query("select * from orderItem o, products p where p.id=o.product_id and order_id=?", new ResultSetHandler<List<OrderItem>>(){

			@Override
			public List<OrderItem> handle(ResultSet rs) throws SQLException {
				List<OrderItem> orderItems = new ArrayList<OrderItem>();
				while (rs.next()) {
					//��װitem����
					OrderItem oi = new OrderItem();
					oi.setBuynum(rs.getInt("buynum"));
					//��װproduct����
					Product p = new Product();
					p.setName(rs.getString("name"));
					p.setPrice(rs.getDouble("price"));
					//��product��װ��OrderItem������
					oi.setP(p);
					orderItems.add(oi);
				}
				return orderItems;
			}},orderid);
		//�����ж�������뵽����order��
		order.setOrderItems(orderItems);
		return order;
	}
	//���ݶ�����ɾ������
	public void delOrderById(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner();
		qr.update(ManagerThreadLocal.getConnection(),"delete from orders where id=?",orderid );
	}
	//���ݶ������޸Ķ����ŵ�״̬
	public void modifyOrderState(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update orders set paystate=1 where id=?", orderid);
	}
}
