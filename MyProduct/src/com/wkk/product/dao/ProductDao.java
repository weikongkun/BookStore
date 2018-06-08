package com.wkk.product.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.wkk.product.domain.Order;
import com.wkk.product.domain.OrderItem;
import com.wkk.product.domain.Product;
import com.wkk.product.util.C3P0Util;
import com.wkk.product.util.ManagerThreadLocal;

public class ProductDao {
	//�޸���Ʒ����
	public void updateProductNum(Order order) throws SQLException {
		//������
		List<OrderItem> orderItems = order.getOrderItems();
		QueryRunner qr = new QueryRunner();
		Object[][] params = new Object[orderItems.size()][];
		for (int i = 0; i < params.length; i++) {
			params[i] = new Object[]{orderItems.get(i).getBuynum(), orderItems.get(i).getP().getId()};
		}
		qr.batch(ManagerThreadLocal.getConnection(),"update products set pnum=pnum-? where id=?", params);
	}
	//��������
	public int findCountByCategory(String category) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select count(*) from products";
		//���category���ǿգ��ͼ�������
		if(!"".equals(category)){
			sql += " where category='" + category + "'";
		}
		long l = (long) qr.query(sql, new ScalarHandler(1));
		return (int)l;
	}
	//�����Ʒ
	public void addProduct(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "INSERT INTO products (id, NAME, price, category, pnum, description) VALUES (?,?,?,?,?,?)";
		qr.update(sql, product.getId(),product.getName(),product.getPrice(),product.getCategory(),
				product.getPnum(),product.getDescription());
	}
	//����������Ʒ��Ϣ
	public List<Product> listAll() throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from products", new BeanListHandler<Product>(Product.class));
	}
	//��������ѯ
	public List<Product> findProductByManyCondition(String id, String category, String name, String minprice,
			String maxprice) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from products where 1=1";
		if (id != null && !"".equals(id)) sql += " and id='" + id +"'";
		if (category != null && !"".equals(category)) sql += " and category='" + category + "'";
		if (name != null && !"".equals(name)) sql += " and name='" + name +"'";
		if (minprice != null && !"".equals(minprice)) sql += " and price>" + minprice;
		if (maxprice != null && !"".equals(maxprice)) sql += " and price<" + maxprice;
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}
	//����idɾ����Ʒ
	public void delProductById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("delete from products where id=?", id);
	}
	//����id������Ʒ
	public Product findProductById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from products where id=?", new BeanHandler<Product>(Product.class), id);
	}
	//�޸���Ʒ��Ϣ
	public void editProduct(Product product) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "update products set name=?,price=?,pnum=?,category=? where id=?";
		qr.update(sql, product.getName(), product.getPrice(),
				product.getPnum(),product.getCategory(),product.getId());
	}
	//��ҳ��ѯ
	public List<Product> findByPage(int pageSize, int currentPage, String category) throws SQLException {
		int m = pageSize * (currentPage - 1);
		int n = pageSize;
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from products";
		if (category != null && !"".equals(category))
			sql += " where category='" + category + "'";
		sql += " limit " + m + "," + n;
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}
	//����ɾ�������ݶ����Ų��ҵĶ������Ʒ��������Ӧ����
	public void updateProductNum(List<OrderItem> orderItems) throws SQLException {
		// ������
		QueryRunner qr = new QueryRunner();
		Object[][] params = new Object[orderItems.size()][];
		for (int i = 0; i < params.length; i++) {
			params[i] = new Object[] { orderItems.get(i).getBuynum(), orderItems.get(i).getP().getId() };
		}
		qr.batch(ManagerThreadLocal.getConnection(), "update products set pnum=pnum+? where id=?", params);
	}

}
