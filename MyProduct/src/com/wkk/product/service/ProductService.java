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
	
	//添加商品
	public void addProduct(Product product) throws ProductException {
		try {
			productDao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("添加商品失败！");
		}
	}
	//查找所有商品
	public List<Product> listAll() {
		try {
			return productDao.listAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//多条件查询商品
	public List<Product> findProductByManyCondition(String id, String category, String name, String minprice, String maxprice) throws ProductException {
		try {
			return productDao.findProductByManyCondition(id,category,name,minprice,maxprice);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("查找失败！");
		}
	}
	//根据商品id删除商品
	public void delProduct(String id) throws ProductException {
		try {
			productDao.delProductById(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("删除失败！");
		}
	}
	//根据商品id查找
	public Product findProductById(String id) {
		try {
			return productDao.findProductById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//修改商品信息
	public void editProduct(Product product) throws ProductException {
		try {
			productDao.editProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ProductException("修改信息失败！");
		}
	}
	public PageBean findProductByPage(int pageSize, int currentPage, String category) {
		
		try {
			int count = productDao.findCountByCategory(category);//查出总条数
			int totalPage = (int)Math.ceil(count * 1.0 / pageSize);//求出总页数
			List<Product> products = productDao.findByPage(pageSize, currentPage, category);
			//将信息都封装到PageBean中
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
