package com.zz.crm.test.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zz.crm.entity.Base_dict;
import com.zz.crm.entity.Condition;
import com.zz.crm.entity.Customer;
import com.zz.crm.mapper.CustomerMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml"})
public class CustomerMapperTest {
	
	@Autowired
	private CustomerMapper customerMapper;

	@Test
	public void getBaseDictListTest() {
		List<Base_dict> dictTypeNameList = customerMapper.getBaseDictList("002");
		System.out.println(dictTypeNameList);
	}
	
	@Test
	public void getCustomerTotalByConditionTest() {
		Condition condition = new Condition();
		condition.setCust_name("马");
		Integer num = customerMapper.getCustomerTotalByCondition(condition);
		System.out.println(num);
	}
	
	@Test
	public void getCustomerRowsByConditionTest() {
		Condition condition = new Condition();
		condition.setStart(0);
		condition.setNumber(8);
		condition.setCust_name("马");
		List<Customer> rows = customerMapper.getCustomerRowsByCondition(condition);
		System.out.println(rows);
	}
}
