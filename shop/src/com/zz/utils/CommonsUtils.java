package com.zz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonsUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String getUUID64() {
		String uuid64 = UUID.randomUUID().toString() + UUID.randomUUID().toString();
		return uuid64.replace("-", "");
	}
	
	//生成订单编号
	public static String getOrderId() {
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMdd");
		String currentTimeMillis = System.currentTimeMillis() + "";
		String orderId = dataFormat.format(new Date()) + currentTimeMillis;
		return orderId;
	}
}
