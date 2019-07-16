package com.zz.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取下载文件名
		String filename = request.getParameter("filename");
		//解决中文参数的乱码问题
		filename = new String(filename.getBytes("ISO8859-1"),"UTF-8");
		
		//获得请求头中的User-Agent
		String agent = request.getHeader("User-Agent");
		//根据不同浏览器进行不同的编码
		String filenameEncoder = "";
		if (agent.contains("MSIE")) {
			// IE浏览器
			filenameEncoder = URLEncoder.encode(filename, "utf-8");
			filenameEncoder = filenameEncoder.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐浏览器
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filenameEncoder = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
		} else {
			// 其它浏览器
			filenameEncoder = URLEncoder.encode(filename, "utf-8");				
		}
		
		//获取输入流
		InputStream in = new FileInputStream(this.getServletContext().getRealPath("/WEB-INF/"+filename));
		//告知浏览器要下载这个文件
		response.setHeader("Content-Disposition","attachment;filename="+filenameEncoder);
		//告知浏览器这个文件的类型（MIME）
		response.setContentType(this.getServletContext().getMimeType(filename));
		//获取输出流
		OutputStream out = response.getOutputStream();
		//通过流将文件写到网页上(response缓冲区只有8k)
		int lenth = 0;
		byte[] buffer =new byte[1024];
		while((lenth=in.read(buffer))>0) {
			out.write(buffer,0,lenth);
		}
		in.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}