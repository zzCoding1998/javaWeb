package com.zz.crm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zz.crm.entity.Base_dict;
import com.zz.crm.entity.Condition;
import com.zz.crm.entity.Customer;
import com.zz.crm.service.CustomerService;
import com.zz.crm.utils.Page;

/**
 * 客户管理
 * @author zzCoding
 *
 * 2019年8月27日
 */

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerServiceImpl;
	//通过配置文件，注入类别编号参数
	@Value("${CUSTOMER_ORIGIN_CODE}")
	private String CUSTOMER_ORIGIN_CODE;
	@Value("${CUSTOMER_INDUSTRY_CODE}")
	private String CUSTOMER_INDUSTRY_CODE;
	@Value("${CUSTOMER_LEVEL_CODE}")
	private String CUSTOMER_LEVEL_CODE;

	//跳转客户管理页面，准备分类数据
	@RequestMapping(value = "/list.action")
	public String getCustomerList(Condition condition,Model model) {
		//1.获取客户来源列表
		List<Base_dict> customerOriginList = customerServiceImpl.getBaseDictList(CUSTOMER_ORIGIN_CODE);
		model.addAttribute("customerOriginList", customerOriginList);
		//2.获取客户行业分类列表
		List<Base_dict> customerIndustryList = customerServiceImpl.getBaseDictList(CUSTOMER_INDUSTRY_CODE);
		model.addAttribute("customerIndustryList", customerIndustryList);
		//3.获取客户等级列表
		List<Base_dict> customerLevelList = customerServiceImpl.getBaseDictList(CUSTOMER_LEVEL_CODE);
		model.addAttribute("customerLevelList", customerLevelList);
		//page分页对象
		Page<Customer> page = customerServiceImpl.getCustomerPageByCondition(condition);
		model.addAttribute("page", page);
		//数据回显
		model.addAttribute("custName", condition.getCust_name());
		model.addAttribute("custSource", condition.getCust_source());
		model.addAttribute("custIndustry", condition.getCust_industry());
		model.addAttribute("custLevel", condition.getCust_level());
		return "customer";
	}
	
	
	@RequestMapping(value = "/edit.action")
	@ResponseBody
	//根据id查询customer全部信息，返回json字符串
	public String editCustomer(String id) {
		Customer customer = customerServiceImpl.getCustomerInfoById(id);
		return JSON.toJSONString(customer);
	}
	
	@RequestMapping(value = "/update.action")
	@ResponseBody
	//更新客户信息
	public String updateCustomer(Customer customer) {
		customerServiceImpl.updateCustomer(customer);
		return "OK";
	}
	
	@RequestMapping(value = "/delete.action")
	@ResponseBody
	//删除客户
	public String deleteCustomer(String id ) {
		customerServiceImpl.deleteCustomerById(id);
		return "OK";
	}
}
