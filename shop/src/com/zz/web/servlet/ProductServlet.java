package com.zz.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.zz.entity.Cart;
import com.zz.entity.CartItem;
import com.zz.entity.Category;
import com.zz.entity.PageBean;
import com.zz.entity.Product;
import com.zz.service.CategoryService;
import com.zz.service.ProductService;
import com.zz.utils.JedisPoolUtil;

import redis.clients.jedis.Jedis;

/**
 * 商品模块
 * @author zzCoding
 *
 * 2019年8月4日
 */
@SuppressWarnings("serial")
public class ProductServlet extends BaseServlet {
	
	//导航栏所有商品分类
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//先从缓存中查询，如果缓存没有数据，再去数据库查询，然后把得到的数据放到缓存中
		Jedis jedis = JedisPoolUtil.getJedis();
		String categoryListJson = jedis.get("categoryListJson");
		if(categoryListJson==null) {
			CategoryService categoryService = new CategoryService();
			List<Category> categoryList = categoryService.findAllCategory();
			categoryListJson = JSON.toJSONString(categoryList);
			jedis.set("categoryListJson",categoryListJson);
		}
		jedis.close();
		response.getWriter().write(categoryListJson);
	}
	
	//首页热门商品和最新商品
	public void indexProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ProductService productService = new ProductService();
		//查询热门商品(9个)
		List<Product> hotProductList = productService.getHotProductList();
		request.setAttribute("hotProductList",hotProductList);
		//查询最新商品(9个)
		List<Product> newProductList = productService.getNewProductList();
		request.setAttribute("newProductList",newProductList);
		//请求转发（携带数据）
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
	
	//根据pid查询商品详细信息
	public void productInfoByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService productService = new ProductService();
		CategoryService categoryService = new CategoryService();
		//得到参数（商品pid、分类cid、当前页数（用于返回））
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String page = request.getParameter("page");
		String keywords = request.getParameter("keywords");
		String pids = null;
		//传递数据，查询商品信息
		Product product = productService.getProductInfoByPid(pid);
		//存储数据
		request.setAttribute("product", product);
		if(cid!=null && !cid.equals("")) {
			request.setAttribute("cid", cid);
			String cname = categoryService.getCnameByCid(cid);
			Category category = new Category(cid,cname);
			product.setCategory(category);
		}
		if(page!=null && !page.equals("")) {
			request.setAttribute("page", page);
		}
		if(keywords!=null && !keywords.equals("")) {
			request.setAttribute("keywords", keywords);
		}
		//将浏览的商品添加到浏览记录中（cookie）
		//得到存储浏览记录信息的cookie
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
				}
			}
			if(pids!=null) {
				//得到pid的数组，转化为链表，方便操作
				String[] split = pids.split("-");
				List<String> asList = Arrays.asList(split);
				LinkedList<String> pidList = new LinkedList<String>(asList);
				//如果已经有了，删除，并添加到第一个，没有的话直接添加到第一个
				if(pidList.contains(pid)) {
					pidList.remove(pid);
				}
				pidList.addFirst(pid);
				//将pidList拼接成字符串(最多显示7个浏览商品)
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<pidList.size() && i<7;i++) {
					sb.append(pidList.get(i));
					sb.append("-");
				}
				//删除多余的-
				pids = sb.substring(0, sb.length()-1);
			}else {
				pids = pid + "";
			}
		}else {
			pids = pid + "";
		}
		Cookie cookie = new Cookie("pids",pids);
		cookie.setMaxAge(60*60*24*3);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		//请求转发
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}
	
	//根据cid查询商品，并进行分页显示
	//***因为后来添加了搜索功能，发现一部分代码重复，所以对该方法取参，携参等做了变化，比较混乱。***
	//***同样做了改变的有商品详细信息模块***
	public void productListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProductService productService = new ProductService();
		//准备数据：cid商品类别编号、currentPage当前第几页、count每页最多有12条数据
		String cid = request.getParameter("cid");
		String keywords = request.getParameter("keywords");
		//如果没有cid则说明从首页商品列表跳转过来
		if((cid==null&&keywords==null)||("".equals(cid)&&"".equals(keywords))) {
			response.getWriter().write("<script>history.go(-2)</script>");
			return;
		}
		if(keywords!=null && !"".equals(keywords)) {
			searchAllProductByKeywords(request,response);
			return;
		}
		String page = request.getParameter("page");
		int currentPage = 1;
		int count = 12;
		if(page!=null && !page.equals("")) {
			currentPage = Integer.parseInt(page);
		}
		//得到封装的pageBean信息,并存储到request域中
		PageBean<Product> pageBean = productService.getPageBeanByCid(currentPage,count,cid);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		//获得浏览记录商品信息，并携带到product_list.jsp
		//获得cookie中的商品浏览记录pid
		Cookie[] cookies = request.getCookies();
		List<Product> historyProductList = new ArrayList<Product>();
		String pids = null;
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
				}
			}
			if(pids!=null) {
				String[] split = pids.split("-");
				for(String historyPid :split) {
					Product historyProduct = productService.getProductInfoByPid(historyPid);
					historyProductList.add(historyProduct);
				}
			}
		}
		request.setAttribute("historyProductList", historyProductList);
		//请求转发（携带数据）
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
	
	//将商品加入购物车
	public void productToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String num = request.getParameter("num");
		CartItem cartItem = new CartItem();
		int totalNum = 0;
		double subTotalPrice = 0;
		//1.准备数据product、数量
		ProductService productService = new ProductService();
		Product product = productService.getProductInfoByPid(pid);
		totalNum = Integer.parseInt(num);
		//2.获取购物车数据，如果没有，创建
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart==null) {
			cart = new Cart();
		}
		//3.准备购物项数据
		//3.1判断购物车里有没有该商品，如果有，数量相加
		if(cart.getCartItems().containsKey(pid)) {
			totalNum += cart.getCartItems().get(pid).getNum();
		}
		//3.2计算购物项总价
		subTotalPrice = totalNum * product.getShop_price();
		//3.3将数据放入购物项
		cartItem.setProduct(product);
		cartItem.setNum(totalNum);
		cartItem.setSubTotalPrice(subTotalPrice);
		//4.将购物项放入购物车，并加上总价
		cart.getCartItems().put(pid, cartItem);
		cart.setTotalPrice(cart.getTotalPrice()+Integer.parseInt(num)*product.getShop_price());
		//5.将购物车放回session域中
		session.setAttribute("cart", cart);
		//6.重定向到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
	
	//根据pid从购物车中删除某件商品
	public void removeProductFromCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//1.获取前台给的参数pid
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("num"));
		CartItem cartItem = null;
		double decrTotalPrice = 0;
		int stayNum = 0;
		//2.获取购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//3.删除该商品，计算总价格
		cartItem = cart.getCartItems().get(pid);
		stayNum = cartItem.getNum() - num;
		//3.1如果删除数量过大或全部删除，直接删除该购物项，减去购物项总价
		if(stayNum<=0) {
			decrTotalPrice = cartItem.getSubTotalPrice();
			cart.getCartItems().remove(pid);
			cart.setTotalPrice(cart.getTotalPrice()-decrTotalPrice);
		}else {
			//3.2如果还有剩余，重新设置数量，子总价，总价
			decrTotalPrice = num * cartItem.getProduct().getShop_price();
			cart.getCartItems().get(pid).setNum(stayNum);
			cart.getCartItems().get(pid).setSubTotalPrice(cartItem.getSubTotalPrice()-decrTotalPrice);;
			cart.setTotalPrice(cart.getTotalPrice()-decrTotalPrice);
		}
		//4.将购物车放回session
		session.setAttribute("cart", cart);
		//5.跳转页面
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	//清空购物车
	public void emptyCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	
	//根据商品名称关键字查询相关商品信息，最多8个
	public void searchProductByKeywords(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//1.获取前台参数keywords
		String keywords = request.getParameter("keywords");
		//2.调用service层得到商品列表
		ProductService productService = new ProductService();
		List<Product> productList = productService.getProductInfoByKeywords(keywords);
		String productListJson = JSON.toJSONString(productList);
		response.getWriter().write(productListJson);
	}
	
	//根据商品名称关键字查询所有商品信息，并进行分页显示
	public void searchAllProductByKeywords(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//1.获取参数
		String keywords = request.getParameter("keywords");
		//2.准备数据(当前第几页，每页最多多少条)
		String pageStr = request.getParameter("page");
		int page=1;
		if(pageStr!=null) {
			page=Integer.parseInt(pageStr);
		}
		int count = 12;
		//3.传递数据获得封装好的pageBean
		ProductService productService = new ProductService();
		PageBean<Product> pageBean = productService.getProductPageBeanBykeywords(page,count,keywords);
		//4.将pageBean放到request域中，请求转发
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("keywords", keywords);
		//5.获得浏览记录商品信息，并携带到product_list.jsp
		//获得cookie中的商品浏览记录pid
		Cookie[] cookies = request.getCookies();
		List<Product> historyProductList = new ArrayList<Product>();
		String pids = null;
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
				}
			}
			if(pids!=null) {
				String[] split = pids.split("-");
				for(String historyPid :split) {
					Product historyProduct = productService.getProductInfoByPid(historyPid);
					historyProductList.add(historyProduct);
				}
			}
		}
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
}