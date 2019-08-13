package com.zz.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.zz.utils.CommonsUtils;

public class InetAddressTest {

	@Test
	public void getIpTest() {
		try {
			System.out.println(InetAddress.getLocalHost().toString().split("/")[1]);
			System.out.println(InetAddress.getLocalHost().getLoopbackAddress());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void mapTest() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "zhangsan");
		System.out.println(map);
		map.put(1, "lisi");
		System.out.println(map);
	}
	
	@Test
	public void millisecondsTest() {
		System.out.println("7.jpg".split("\\.")[0]);;
	}
	
}
