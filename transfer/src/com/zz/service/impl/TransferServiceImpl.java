package com.zz.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zz.dao.ITransferDao;
import com.zz.dao.impl.TransferDaoImpl;
import com.zz.service.ITransferService;

public class TransferServiceImpl implements ITransferService {

	@Override
	//转账
	public boolean transfer(String accountIn, String accountOut, double count) {
		
		boolean isTransferSuccess = true;
		ITransferDao transferDao = new TransferDaoImpl();
		Connection con = null;
		try {
			con = new ComboPooledDataSource().getConnection();
			con.setAutoCommit(false);
			//转出
			if(transferDao.outMoney(con, accountOut, count)+transferDao.inMoney(con, accountIn, count)!=2) {
				isTransferSuccess = false;
				con.rollback();
			}
			//转入
		} catch (SQLException e) {
			try {
				isTransferSuccess=false;
				con.rollback();
			} catch (SQLException e1) {
				isTransferSuccess=false;
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				con.commit();
				con.close();
			} catch (SQLException e) {
				isTransferSuccess=false;
				e.printStackTrace();
			}
		}
		return isTransferSuccess;
	}

}
