package com.zz.service.impl;

import java.sql.SQLException;

import com.zz.dao.IDelProductDao;
import com.zz.dao.impl.DelProductDaoImpl;
import com.zz.service.IDelProductService;

public class DelProductServiceImpl implements IDelProductService {
	
	IDelProductDao delProductDao = new DelProductDaoImpl();

	@Override
	public void delProduct(String pid) {
		try {
			delProductDao.delProduct(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
