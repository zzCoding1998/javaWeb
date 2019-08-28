package com.zz.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zz.crm.entity.Base_dict;
import com.zz.crm.entity.Condition;
import com.zz.crm.entity.Customer;
import com.zz.crm.mapper.CustomerMapper;
import com.zz.crm.service.CustomerService;
import com.zz.crm.utils.Page;

/**
 * 客户管理业务层实现类
 * @author zzCoding
 *
 * 2019年8月27日
 */
@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;

	@Override
	//根据dict_type_code查询相应的Base_dict
	public List<Base_dict> getBaseDictList(String dict_type_code) {
		return customerMapper.getBaseDictList(dict_type_code);
	}

	@Override
	//条件查询分页信息
	public Page<Customer> getCustomerPageByCondition(Condition condition) {
		//处理数据，封装分页信息
		Page<Customer> page = new Page<Customer>();
		//1.设置每页最多有多少条信息
		page.setSize(8);
		condition.setNumber(8);
		page.setPage(1);
		condition.setStart(0);
		
		if(condition!=null) {
			//判断参数合法性
			//当前页
			if(condition.getPage()!=null && condition.getPage()>0) {
				page.setPage(condition.getPage());
				condition.setStart((condition.getPage()-1)*condition.getNumber());
			}
			//客户名称
			if(condition.getCust_name()!=null && condition.getCust_name().trim()!="") {
				condition.setCust_name(condition.getCust_name().trim());
			}
			//客户来源
			if(condition.getCust_source()!=null && condition.getCust_source().trim()!="") {
				condition.setCust_source(condition.getCust_source().trim());
			}
			//客户行业
			if(condition.getCust_industry()!=null && condition.getCust_industry().trim()!="") {
				condition.setCust_industry(condition.getCust_industry().trim());
			}
			//客户等级
			if(condition.getCust_level()!=null && condition.getCust_level().trim()!="") {
				condition.setCust_level(condition.getCust_level().trim());
			}
		}
		page.setTotal(customerMapper.getCustomerTotalByCondition(condition));
		page.setRows(customerMapper.getCustomerRowsByCondition(condition));
		return page;
	}

	@Override
	//根据id查询客户个人信息
	public Customer getCustomerInfoById(String id) {
		return customerMapper.getCustomerInfoById(id);
	}

	@Override
	//更新客户信息
	public void updateCustomer(Customer customer) {
		customerMapper.updateCustomer(customer);
	}

	@Override
	//删除客户
	public void deleteCustomerById(String id) {
		customerMapper.deleteCustomerById(id);
	}
}
