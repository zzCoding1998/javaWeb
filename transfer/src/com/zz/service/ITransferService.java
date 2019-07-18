package com.zz.service;

public interface ITransferService {

	//转账
	boolean transfer(String accountIn, String accountOut, double count);
	
}
