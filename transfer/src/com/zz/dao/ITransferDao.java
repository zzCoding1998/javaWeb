package com.zz.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ITransferDao {
	
	//账户转出金额
	public int outMoney(Connection con,String out,double count) throws SQLException; 
	//账户转入金额
	public int inMoney(Connection con,String in,double count) throws SQLException; 
	
}
