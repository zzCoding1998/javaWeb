package com.zz.web.filter;

import java.io.IOException;
import java.sql.SQLException;

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
import com.zz.service.IUserService;
import com.zz.service.impl.UserServiceImpl;

public class AutoLoginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp =  (HttpServletResponse) response;
		String username = null;
		String password = null;
		String loginInfo = null;
		User user = new User();
		User loginUser = null;
		IUserService userService = new UserServiceImpl();
		
		//1.如果已经登录，则不自动登录
		if(req.getSession().getAttribute("user")!=null) {
			chain.doFilter(request, response);
			return;
		}
		//2.获取cookie，如果cookie中没有关于登录的信息，不自动登录	
		Cookie[] cookies = req.getCookies();
		if(cookies==null) {
			chain.doFilter(request, response);
			return;
		}else {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("userLoginInfo")) {
					loginInfo = cookie.getValue();
					break;
				}
			}
		}
		//3.如果有相关cookie信息，校验登录信息是否正确
		if(loginInfo!=null) {
			username=loginInfo.split("@")[0];
			password=loginInfo.split("@")[1];
			user.setUsername(username);
			user.setPassword(password);
			try {
				loginUser = userService.login(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//如果信息正确，自动登录
			if(loginUser!=null) {
				req.getSession().setAttribute("user", loginUser);
				Cookie cookie = new Cookie("userLoginInfo",loginUser.getUsername() +"@"+loginUser.getPassword());
				cookie.setMaxAge(60*60*24*3);
				cookie.setPath(req.getContextPath());
				resp.addCookie(cookie);
			}
			//如果信息不正确，不自动登录
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
