package com.zz.service;

import java.util.List;

import com.zz.entity.Product;
import com.zz.vo.PageInfo;

public interface IProductListService {

	List<Product> getProductList();

	PageInfo<Product> getPageInfo(int currentPage,int countPerPage) throws Exception;

}
