package com.zz.crm.service;

import java.util.List;

import com.zz.crm.entity.Base_dict;
import com.zz.crm.entity.Condition;
import com.zz.crm.entity.Customer;
import com.zz.crm.utils.Page;

/**
 * 客户管理业务层接口
 * @author zzCoding
 *
 * 2019年8月27日
 */
public interface CustomerService {
	
	//根据dict_type_code查询相应的Base_dict
	public List<Base_dict> getBaseDictList(String dict_type_code);

	//条件查询分页信息
	public Page<Customer> getCustomerPageByCondition(Condition condition);

	//查询customer个人信息
	public Customer getCustomerInfoById(String id);

	//更新客户信息
	public void updateCustomer(Customer customer);

	//删除客户
	public void deleteCustomerById(String id);

}
