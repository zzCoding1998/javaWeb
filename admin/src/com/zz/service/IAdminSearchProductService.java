package com.zz.service;

import java.sql.SQLException;

import com.zz.entity.Product;
import com.zz.vo.Condition;
import com.zz.vo.PageInfo;

public interface IAdminSearchProductService {

	PageInfo<Product> getProductByCondition(Condition condition,int currentPage,int countPerPage) throws SQLException;

}
