package com.wkk.product.service;

import java.sql.SQLException;
import java.util.List;

import com.wkk.product.dao.ProductDao;
import com.wkk.product.domain.Order;
import com.wkk.product.domain.PageBean;
import com.wkk.product.domain.Product;
import com.wkk.product.exception.ProductException;

public class ProductService {
	private ProductDao productDao = new ProductDao();
	
	//�����Ʒ
	public void addProduct(Product product) throws ProductException {
		try {
			productDao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("�����Ʒʧ�ܣ�");
		}
	}
	//����������Ʒ
	public List<Product> listAll() {
		try {
			return productDao.listAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//��������ѯ��Ʒ
	public List<Product> findProductByManyCondition(String id, String category, String name, String minprice, String maxprice) throws ProductException {
		try {
			return productDao.findProductByManyCondition(id,category,name,minprice,maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("����ʧ�ܣ�");
		}
	}
	//������Ʒidɾ����Ʒ
	public void delProduct(String id) throws ProductException {
		try {
			productDao.delProductById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("ɾ��ʧ�ܣ�");
		}
	}
	//������Ʒid����
	public Product findProductById(String id) {
		try {
			return productDao.findProductById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//�޸���Ʒ��Ϣ
	public void editProduct(Product product) throws ProductException {
		try {
			productDao.editProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("�޸���Ϣʧ�ܣ�");
		}
	}
	public PageBean findProductByPage(int pageSize, int currentPage, String category) {
		
		try {
			int count = productDao.findCountByCategory(category);//���������
			int totalPage = (int)Math.ceil(count * 1.0 / pageSize);//�����ҳ��
			List<Product> products = productDao.findByPage(pageSize, currentPage, category);
			//����Ϣ����װ��PageBean��
			PageBean pb = new PageBean();
			pb.setCategory(category);
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setProducts(products);
			pb.setTotalPage(totalPage);
			pb.setCount(count);
			return pb;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
