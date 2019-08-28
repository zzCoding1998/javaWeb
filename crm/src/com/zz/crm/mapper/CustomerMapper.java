package com.zz.crm.mapper;

import java.util.List;

import com.zz.crm.entity.Base_dict;
import com.zz.crm.entity.Condition;
import com.zz.crm.entity.Customer;

/**
 * 客户管理Dao层
 * @author zzCoding
 *
 * 2019年8月27日
 */
public interface CustomerMapper {
	
	//根据dict_type_code 查询对应的base_dict列表
	//包括客户来源(002),行业分类(001),客户级别(006)
	public List<Base_dict> getBaseDictList(String dict_type_code);

	//根据condition对象查询数据总条数
	public Integer getCustomerTotalByCondition(Condition condition);

	//根据condition对象查询当前页数据
	public List<Customer> getCustomerRowsByCondition(Condition condition);

	//根据id查询客户个人信息
	public Customer getCustomerInfoById(String id);

	//更新客户信息
	public void updateCustomer(Customer customer);

	//删除客户信息
	public void deleteCustomerById(String id);
	
}
