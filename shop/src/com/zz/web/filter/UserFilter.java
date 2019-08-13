package com.zz.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz.entity.User;
import com.zz.service.UserService;

public class UserFilter implements Filter {


	public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		Cookie[] cookies = req.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				//记住了用户名，将用户名放到session去
				if(cookie.getName().equals("username")) {
					//放入session
					String username = cookie.getValue();
					req.getSession().setAttribute("username", URLDecoder.decode(username, "UTF-8"));
					//更新cookie
					Cookie newCookie = new Cookie("username",username);
					newCookie.setMaxAge(60*60*24*3);
					newCookie.setPath(req.getContextPath()+"/login.jsp");
					resp.addCookie(newCookie);
				}
				if(cookie.getName().equals("userInfo")) {
					//获取userInfo，拆分成用户名和密码,封装user对象
					String userInfo = URLDecoder.decode(cookie.getValue(), "UTF-8");
					String username = userInfo.split("@")[0];
					String password = userInfo.split("@")[1];
					User user = new User();
					user.setUsername(username);
					user.setPassword(password);
					//验证用户名和密码的正确性
					UserService userService = new UserService();
					User loginUser = userService.login(user);
					if(loginUser!=null) {
						//放入session
						req.getSession().setAttribute("user", loginUser);
						//更新cookie
						Cookie newCookie = new Cookie("userInfo",URLEncoder.encode(userInfo, "UTF-8"));
						newCookie.setMaxAge(60*60*24*3);
						newCookie.setPath(req.getContextPath());
						resp.addCookie(newCookie);
					}
				}
			}
		}
		
		chain.doFilter(req, resp);
	}

}
