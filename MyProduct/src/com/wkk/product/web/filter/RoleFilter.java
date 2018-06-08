package com.wkk.product.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkk.product.domain.User;
//Ȩ�޹�����
public class RoleFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest rst, ServletResponse rsp, FilterChain chain)
			throws IOException, ServletException {
		//ǿת
		HttpServletRequest request = (HttpServletRequest) rst;
		HttpServletResponse response = (HttpServletResponse) rsp;
		//����ҵ��
			//��session�а��û�����õ�
		User user = (User)request.getSession().getAttribute("user");
			//�жϵ�ǰ�û�Ȩ��
		if (user != null) {
			if (!"admin".equals(user.getRole())) {
				response.getWriter().write("Ȩ�޲��㣬���޷����ʣ�");
				response.setHeader("refresh", "2;url=" + request.getContextPath() + "/index.jsp");
				return;
			}
			chain.doFilter(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
