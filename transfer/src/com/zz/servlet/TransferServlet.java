package com.zz.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.service.ITransferService;
import com.zz.service.impl.TransferServiceImpl;

/**
 * 模拟转账
 * @author zzCoding
 *
 */
public class TransferServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("非法访问，已拒绝！");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String accountIn = request.getParameter("in");
		String accountOut = request.getParameter("out");
		double count = Double.parseDouble(request.getParameter("count"));
		
		ITransferService transferService = new TransferServiceImpl();
		boolean isTransferSuccess = transferService.transfer(accountIn,accountOut,count);
		response.setContentType("text/html;charset=UTF-8");
		if(isTransferSuccess==true) {
			response.getWriter().write("转账成功！！！");
		}else {
			response.getWriter().write("转账失败！！！");
		}
	}
}