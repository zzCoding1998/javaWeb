package com.zz.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.zz.entity.Category;
import com.zz.entity.Condition;
import com.zz.entity.OrderItem;
import com.zz.entity.Orders;
import com.zz.entity.PageBean;
import com.zz.entity.Product;
import com.zz.service.AdminService;
import com.zz.utils.CommonsUtils;
@SuppressWarnings("serial")
public class AdminServlet extends BaseServlet {
	
	AdminService adminService = new AdminService();

	//获取商品分类，并跳转到商品添加页面
	public void getCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = adminService.getCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/product/add.jsp").forward(request, response);
	}

	//添加商品（文件上传）
	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Product product = new Product();
		try {
			//1.创建磁盘文件项工厂对象
			File tempPath = new File(this.getServletContext().getRealPath("/temp"));
			DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024, tempPath);
			//2.创建文件上传核心对象
			FileUpload upload = new FileUpload(factory);
			//3.判断表单是否为多部分表单数据
			boolean isMultipartContent = upload.isMultipartContent(request);
			if(isMultipartContent) {
				//解决文件名中文乱码问题
				upload.setHeaderEncoding("UTF-8");
				//解析request对象
				List<FileItem> itemList = upload.parseRequest(request);
				if(itemList!=null) {
					for(FileItem item : itemList) {
						//4.判断item普通表单项
						boolean isformField = item.isFormField();
						if(isformField) {
							//是普通表单项
							switch (item.getFieldName()) {
							case "pname":
								product.setPname(item.getString("UTF-8"));
								break;
							case "ishot":
								product.setIs_hot(Integer.parseInt(item.getString("UTF-8")));
								break;
							case "market_price":
								product.setMarket_price(Double.parseDouble(item.getString("UTF-8")));
								break;
							case "cid":
								Category category = new Category();
								category.setCid(item.getString("UTF-8"));
								product.setCategory(category);
								break;
							case "shop_price":
								product.setShop_price(Double.parseDouble(item.getString("UTF-8")));
								break;
							case "pdesc":
								product.setPdesc(item.getString("UTF-8"));;
								break;
							}
						}else {
							//不是普通表单项
							//文件名，文件路径，文件IO流
							String filename = CommonsUtils.getUUID64()+"."+item.getName().split("\\.")[1];
							String pathStr = this.getServletContext().getRealPath("/products/1")+"\\"+filename;
							File path = new File(pathStr);
							InputStream in = item.getInputStream();
							FileOutputStream out = new FileOutputStream(path);
							//文件copy
							IOUtils.copy(in, out);
							//关闭流
							in.close();
							out.close();
							//删除临时文件
							item.delete();
							//将文件路径存入product对象
							product.setPimage("products/1/"+filename);
						}
					}
				}
			}
			//5.将product对象补充完整
			//pid、pdate、pflag
			product.setPid(CommonsUtils.getUUID());
			product.setPdate(new Date());
			product.setPflag(0);
			//6.传递商品信息给业务层
			adminService.addProduct(product);
			//重定向到信息查询页
			response.sendRedirect(request.getContextPath()+"/admin?method=productList");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//首页商品展示
	public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//从视图得到分页参数
		String currentPageStr = request.getParameter("currentPage");
		int currentPage;
		if(currentPageStr != null) {
			currentPage= Integer.parseInt(currentPageStr);
		}else{
			currentPage=1;
		}
		int countPerPage = 12;
		//放入商品信息
		PageBean<Product> pageBean = null;
		try {
			pageBean = adminService.getpageBean(currentPage,countPerPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("pageBean", pageBean);
		//放入商品类别信息
		List<Category> categoryList = adminService.getCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("admin/product/list.jsp").forward(request, response);
	}
	
	//查找所有商品
	public void searchProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			request.setCharacterEncoding("UTF-8");
			//从视图得到分页参数
			String pageStr = request.getParameter("page");
			int page;
			if(pageStr != null) {
				page= Integer.parseInt(pageStr);
			}else{
				page=1;
			}
			int countPerPage = 12;
			Condition condition = new Condition();
			try {
				//获取查询条件
				BeanUtils.populate(condition, request.getParameterMap());
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			//将数据传递给service层，获取返回数据,放到request
			
			PageBean<Product> pageBean = null;
			try {
				pageBean = adminService.getProductByCondition(condition,page,countPerPage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("pageBean", pageBean);
			//放入商品类别信息
			List<Category> categoryList = adminService.getCategory();
			request.setAttribute("categoryList", categoryList);
			//将回显数据放到request
			request.setAttribute("condition", condition);
			//请求转发到jsp页面
			request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);;
		}
	
	//删除商品
	public void delProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		Product product = adminService.getProductByPid(pid);
		String filePath = this.getServletContext().getRealPath(product.getPimage());
		//删除商品
		if(adminService.delProduct(pid)==1) {
			//删除成功
			//删除商品照片
			File file = new File(filePath);
			file.delete();
			response.sendRedirect(request.getContextPath() + "/admin?method=productList");		
		}else {
			response.getWriter().write("<script>alert('暂时不能删除该商品！'); location.href='"+request.getContextPath()+"/admin?method=productList'</script>");
		}
	}
	
	//编辑商品界面的加载数据传输
	public void editProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pid =request.getParameter("pid");
		Product product = adminService.getProductByPid(pid);
		request.setAttribute("product", product);
		
		List<Category> categoryList = adminService.getCategory();
		request.setAttribute("categoryList", categoryList);
		
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);;
		
	}
	
	//编辑商品
	@SuppressWarnings({ "deprecation", "unchecked", "static-access" })
	public void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product product = new Product();
		
		try {
			//1.创建磁盘文件项工厂对象
			File tempPath = new File(this.getServletContext().getRealPath("/temp"));
			DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024, tempPath);
			//2.创建文件上传核心对象
			FileUpload upload = new FileUpload(factory);
			//3.判断表单是否为多部分表单数据
			boolean isMultipartContent = upload.isMultipartContent(request);
			if(isMultipartContent) {
				//解决文件名中文乱码问题
				upload.setHeaderEncoding("UTF-8");
				//解析request对象
				List<FileItem> itemList = upload.parseRequest(request);
				if(itemList!=null) {
					for(FileItem item : itemList) {
						//4.判断item普通表单项
						boolean isformField = item.isFormField();
						if(isformField) {
							//是普通表单项
							switch (item.getFieldName()) {
							case "pid":
								product.setPid(item.getString("UTF-8"));
								break;
							case "pname":
								product.setPname(item.getString("UTF-8"));
								break;
							case "ishot":
								product.setIs_hot(Integer.parseInt(item.getString("UTF-8")));
								break;
							case "market_price":
								product.setMarket_price(Double.parseDouble(item.getString("UTF-8")));
								break;
							case "cid":
								Category category = new Category();
								category.setCid(item.getString("UTF-8"));
								product.setCategory(category);
								break;
							case "shop_price":
								product.setShop_price(Double.parseDouble(item.getString("UTF-8")));
								break;
							case "pdesc":
								product.setPdesc(item.getString("UTF-8"));;
								break;
							}
						}else {
							//不是普通表单项
							//文件名，文件路径，文件IO流
							String filename = CommonsUtils.getUUID64()+"."+item.getName().split("\\.")[1];
							String pathStr = this.getServletContext().getRealPath("/products/1")+"\\"+filename;
							File path = new File(pathStr);
							InputStream in = item.getInputStream();
							FileOutputStream out = new FileOutputStream(path);
							//文件copy
							IOUtils.copy(in, out);
							//关闭流
							in.close();
							out.close();
							//删除临时文件
							item.delete();
							//删除原来的商品图片
							Product productOrigin = adminService.getProductByPid(product.getPid());
							String filePath = this.getServletContext().getRealPath(productOrigin.getPimage());
							File file = new File(filePath);
							file.delete();
							//将文件路径存入product对象
							product.setPimage("products/1/"+filename);
						}
					}
				}
			}
			//数据传输到service层
			adminService.updateProduct(product);
			response.sendRedirect(request.getContextPath()+"/admin?method=productList");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查找所有商品分类，跳转到商品分类管理页面
	public void categoryList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Category> categoryList = adminService.getCategory();
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);;
	}
	
	//跳转到商品分类编辑页面。准备数据
	public void categoryEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		request.setAttribute("cname", cname);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);;
	}
	
	//修改分类名称
	public void categoryUpdate(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		String newCname = request.getParameter("newCname");
		adminService.updateCategory(cname,newCname);
		response.sendRedirect(request.getContextPath()+"/admin?method=categoryList");
	}
	
	//添加分类
	public void categoryAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		adminService.categoryAdd(cname);
		response.sendRedirect(request.getContextPath()+"/admin?method=categoryList");
	}
	
	//删除分类
	public void delCategory(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		adminService.delCategory(cid);
		response.sendRedirect(request.getContextPath()+"/admin?method=categoryList");
	}
	
	//获取订单列表
	public void orderList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		List<Orders> orderList = adminService.getOrderList();
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}
	
	//获得订单详细信息
	public void getOrderInfoByOid(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String oid = request.getParameter("oid");
		List<OrderItem> orderItemList = adminService.getOrderInfoByOid(oid);
		String orderItemListJson = JSON.toJSONString(orderItemList);
		response.getWriter().write(orderItemListJson);
	}
}