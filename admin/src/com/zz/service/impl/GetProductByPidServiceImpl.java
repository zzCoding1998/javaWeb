package com.zz.service.impl;

import java.sql.SQLException;

import com.zz.dao.IGetProductByPidDao;
import com.zz.dao.impl.GetProductByPidDaoImpl;
import com.zz.entity.Product;
import com.zz.service.IGetProductByPidService;

public class GetProductByPidServiceImpl implements IGetProductByPidService {

	IGetProductByPidDao getProductByPidDao = new GetProductByPidDaoImpl();
	
	@Override
	public Product getProductByPid(String pid) {
		try {
			return getProductByPidDao.getProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
