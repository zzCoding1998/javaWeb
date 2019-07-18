package com.zz.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.zz.dao.ITransferDao;

public class TransferDaoImpl implements ITransferDao{

	QueryRunner runner = new QueryRunner();
	
	@Override
	//账户转出金额
	public int outMoney(Connection con, String out, double count) throws SQLException {
		String sql = "update account set money = money-? where account=?";
		return runner.update(con, sql, count,out);
	}

	@Override
	public int inMoney(Connection con, String in, double count) throws SQLException {
		String sql = "update account set money = money+? where account=?";
		return runner.update(con, sql, count,in);
	}
	
}
